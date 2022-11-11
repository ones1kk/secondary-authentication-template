package io.github.ones1kk.authenticationtemplate.web.token.provider;

public interface TokenProvider<T> {

    String createToken(String key, T value);

    String createToken(T value);

    boolean isExpired(String token);

    boolean isValid(String token, T key);

    T getKey(String token) throws Exception;
}
