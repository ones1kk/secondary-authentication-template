package io.github.ones1kk.authenticationtemplate.web.token.provider;

import javax.servlet.http.HttpServletRequest;

public interface HttpRequestTokenProvider<T> extends TokenProvider<T> {

    String cookieName = "X_AUTH_TOKEN";

    String resolveToken(HttpServletRequest request);
}
