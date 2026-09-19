package gr.aueb.cf.eduapp.core.exceptions;

import org.springframework.aop.framework.AopConfigException;

public class EntityInvalidArgumentException extends AppGenericException {
    private static final String DEFAULT_CODE = "InvalidArgument";

    public EntityInvalidArgumentException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
