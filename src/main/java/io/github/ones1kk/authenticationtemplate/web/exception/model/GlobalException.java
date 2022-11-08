package io.github.ones1kk.authenticationtemplate.web.exception.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalException extends RuntimeException {

    private final String code;

    private final HttpStatus status;

    private final Object[] args;

    public GlobalException(String code, Object... args) {
        this(code, HttpStatus.INTERNAL_SERVER_ERROR, args);
    }

    public GlobalException(String code, HttpStatus status, Object... args) {
        super(code);
        this.status = status;
        this.code = code;
        this.args = args;
    }
}
