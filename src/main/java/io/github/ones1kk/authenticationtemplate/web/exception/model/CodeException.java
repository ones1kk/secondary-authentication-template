package io.github.ones1kk.authenticationtemplate.web.exception.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CodeException extends RuntimeException {

    private final String message;

    private final HttpStatus status;

    private final Object[] args;

    public CodeException(String message, Object... args) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR, args);
    }

    public CodeException(String message, HttpStatus status, Object... args) {
        this.status = status;
        this.message = message;
        this.args = args;
    }
}
