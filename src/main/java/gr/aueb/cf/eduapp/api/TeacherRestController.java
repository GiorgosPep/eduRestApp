package gr.aueb.cf.eduapp.api;

import gr.aueb.cf.eduapp.core.exceptions.*;
import gr.aueb.cf.eduapp.dto.ErrorResponseDTO;
import gr.aueb.cf.eduapp.dto.TeacherInsertDTO;
import gr.aueb.cf.eduapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.eduapp.dto.ValidationErrorResponseDTO;
import gr.aueb.cf.eduapp.service.ITeacherService;
import gr.aueb.cf.eduapp.validator.TeacherInsertValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teachers")
public class TeacherRestController {

    private final ITeacherService teacherService;
    private final TeacherInsertValidator teacherInsertValidator;

    @Operation(
            summary = "Save a teacher",
            description = "Registers a new teacher in the registry."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201", description = "Teacher created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TeacherReadOnlyDTO.class))
            ),
            @ApiResponse(
                    responseCode = "409", description = "Teacher already exists",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400", description = "Validation error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            ),
    })
    @PostMapping
    public ResponseEntity<TeacherReadOnlyDTO> insertTeacher(
        @Valid @RequestBody TeacherInsertDTO teacherInsertDTO,
        BindingResult bindingResult
    ) throws EntityAlreadyExistsException, EntityInvalidArgumentException, ValidationException {
        teacherInsertValidator.validate(teacherInsertDTO, bindingResult);

        if(bindingResult.hasErrors()) {
            throw new ValidationException("Teacher", "Invalid teacher data", bindingResult);
        }

        TeacherReadOnlyDTO teacherReadOnlyDTO = teacherService.saveTeacher(teacherInsertDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{uuid")
                .buildAndExpand(teacherReadOnlyDTO.uuid())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(teacherReadOnlyDTO);
    }

    @Operation(
            summary = "Upload AMKA attachment file for a teacher",
            description = "Uploads a teacher's AMKA document file. Replaces existing file if present."
    )

    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "File uploaded successfully"
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "Teacher not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),

            @ApiResponse(
                    responseCode = "500",
                    description = "File upload failed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PostMapping("/uuid/amka-file")
    public ResponseEntity<Void> uploadAmkaFile(
            @PathVariable UUID uuid,
            @RequestParam("amkaFile") MultipartFile file
    ) throws EntityNotFoundException, FileUploadException {

        teacherService.saveAmkaFile(uuid, file);
        return ResponseEntity
                .noContent()
                .build();
    }
}
