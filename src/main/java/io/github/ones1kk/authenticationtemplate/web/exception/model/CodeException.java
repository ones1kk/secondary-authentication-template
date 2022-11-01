package io.github.ones1kk.authenticationtemplate.web.exception.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class CodeException extends AuthenticationException {

    private final String message;

    private final HttpStatus status;

    private final Object[] args;

    public CodeException(String message, Object... args) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR, args);
    }

    public CodeException(String message, HttpStatus status, Object... args) {
        super(message);
        this.status = status;
        this.message = message;
        this.args = args;
    }
}
