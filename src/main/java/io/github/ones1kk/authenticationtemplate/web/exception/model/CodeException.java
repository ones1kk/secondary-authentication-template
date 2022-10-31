package io.github.ones1kk.authenticationtemplate.web.exception.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CodeException extends RuntimeException implements CodeThrowable<CodeException, String> {

    private final String code;

    private final HttpStatus status;

    private final Object[] args;

    public CodeException(String code, Object... args) {
        this(code, HttpStatus.INTERNAL_SERVER_ERROR, args);
    }

    public CodeException(String code, HttpStatus status, Object... args) {
        this.status = status;
        this.code = code;
        this.args = args;
    }
}
