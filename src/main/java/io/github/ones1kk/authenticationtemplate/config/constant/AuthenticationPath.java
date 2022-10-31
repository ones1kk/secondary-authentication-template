package io.github.ones1kk.authenticationtemplate.config.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthenticationPath {
    FIRST_LOGIN_API_PATH("/login/first"),
    SECOND_LOGIN_API_PATH("/login/second"),

    LOGOUT_API_PATH("/logout");

    private final String path;
}
