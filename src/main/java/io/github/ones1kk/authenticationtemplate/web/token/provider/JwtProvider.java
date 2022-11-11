package io.github.ones1kk.authenticationtemplate.web.token.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static io.github.ones1kk.authenticationtemplate.web.token.provider.constant.TokenHeaderName.X_AUTH_TOKEN;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.StringUtils.hasText;

public class JwtProvider<T extends Long> implements HttpRequestTokenProvider<Long> {

    // 3 hours
    private final long accessExpiredTime = 2 * 90 * 60 * 1000L;

    // 6 hours
    private final long refreshExpiredTime = 6 * 60 * 60 * 1000L;

    private final String secretKey;
    private final Key signKey;

    public JwtProvider(String secretKey) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.signKey = Keys.hmacShaKeyFor(getSecretKeyByteArray(UTF_8));
    }

    @Override
    public String createToken(String key, Long value) {
        Date now = new Date();
        return Jwts.builder()
                .claim(key, value)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessExpiredTime))
                .signWith(signKey, HS256)
                .compact();
    }

    @Override
    public String createToken(Long value) {
        Date now = new Date();
        return Jwts.builder()
                .claim(X_AUTH_TOKEN.getName(), value)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessExpiredTime))
                .signWith(signKey, HS256)
                .compact();
    }

    @Override
    public boolean isExpired(String token) {
        return getClaims(token)
                .getExpiration()
                .before(new Date());
    }

    @Override
    public boolean isValid(String token, Long key) {
        return isEqualKey(token, key) && !isExpired(token);
    }

    private boolean isEqualKey(String token, Long key) {
        return getKey(token).equals(key);
    }

    @Override
    public Long getKey(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(X_AUTH_TOKEN.getName());
        if (hasText(token)) return token;
        return null;
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKeyByteArray())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private byte[] getSecretKeyByteArray(Charset charset) {
        return secretKey.getBytes(charset);
    }

    private byte[] getSecretKeyByteArray() {
        return secretKey.getBytes();
    }
}
