package io.github.ones1kk.authentication.web.exception.model;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationHolderException extends AuthenticationException {

    public AuthenticationHolderException(String msg) {
        super(msg);
    }

    public AuthenticationHolderException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
