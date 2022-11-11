package io.github.ones1kk.authenticationtemplate.web.token.provider.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenHeaderName {

    X_AUTH_TOKEN("X_AUTH_TOKEN");

    private final String name;

}
