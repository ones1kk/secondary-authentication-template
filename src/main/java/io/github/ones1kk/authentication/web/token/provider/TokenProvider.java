package io.github.ones1kk.authentication.web.token.provider;

import org.springframework.security.core.Authentication;

public interface TokenProvider<T extends Authentication> {

    String createToken(T value, Long expiredTime);

    boolean isExpired(String token);

    boolean isValid(String token, T key) throws Exception;

    T getAuthentication(String token, Class<? extends T> clazz) throws Exception;
}
