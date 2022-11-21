package io.github.ones1kk.authentication.web.token.provider;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface HttpRequestTokenProvider<T extends Authentication> extends TokenProvider<T> {

    String resolveToken(HttpServletRequest request);
}
