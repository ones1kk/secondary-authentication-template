package io.github.ones1kk.authenticationtemplate.web.token.provider;

public interface TokenProvider<T> {

    String createToken(T value, Long expiredTime);

    boolean isExpired(String token);

    boolean isValid(String token, T key) throws Exception;

    T getAuthentication(String token, Class<? extends T> clazz) throws Exception;
}
