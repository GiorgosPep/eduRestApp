package gr.aueb.cf.eduapp.core.exceptions;

import org.springframework.data.jpa.repository.EntityGraph;

public class EntityAlreadyExistsException extends AppGenericException {
    private static final String DEFAULT_CODE = "AlreadyExists";

    public EntityAlreadyExistsException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
