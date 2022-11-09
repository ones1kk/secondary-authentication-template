package io.github.ones1kk.authenticationtemplate.web.token.provider;

public interface TokenProvider<T> {

    // 3 days
    long expiredTime = ((3 * 60 * 1000L) * 24) * 60;

    String createToken(String key, T value);

    boolean isExpired(String token);

    boolean isValid(String token, T key);

    T getKey(String token) throws Exception;
}
