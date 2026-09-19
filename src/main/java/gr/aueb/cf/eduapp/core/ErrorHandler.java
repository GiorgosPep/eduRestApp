package gr.aueb.cf.eduapp.core;

import gr.aueb.cf.eduapp.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.eduapp.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.eduapp.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.eduapp.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFoundException(EntityNotFoundException e){
        log.warn("Entity not found. Message = {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)   // 404 Not found
                .body(new ErrorResponseDTO(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler({EntityAlreadyExistsException.class})
    public ResponseEntity<ErrorResponseDTO> handleEntityAlreadyExists(EntityNotFoundException e){
        log.warn("Entity already exists. Message = {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)    // 409 Bad request
                .body(new ErrorResponseDTO(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler({EntityInvalidArgumentException.class})
    public ResponseEntity<ErrorResponseDTO> handleEntityInvalidArgument(EntityNotFoundException e){
        log.warn("Invalid argument. Message = {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)     //400 Bad request
                .body(new ErrorResponseDTO(e.getCode(), e.getMessage()));
    }





}
