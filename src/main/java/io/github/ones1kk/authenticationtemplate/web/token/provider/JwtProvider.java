package io.github.ones1kk.authenticationtemplate.web.token.provider;

import io.github.ones1kk.authenticationtemplate.web.exception.MessageSupport;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

import static io.github.ones1kk.authenticationtemplate.web.token.provider.constant.TokenHeaderName.X_AUTH_TOKEN;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.StringUtils.hasText;

public class JwtProvider<T extends Long> implements HttpRequestTokenProvider<Long> {

    // 1 hours
    private final long accessExpiredTime = Duration.ofHours(1).toMillis();

    // 3 hours
    private final long refreshExpiredTime = Duration.ofHours(3).toMillis();

    private final String secretKey;

    private final Key signKey;

    private final MessageSupport messageSupport;

    public JwtProvider(String secretKey, MessageSupport messageSupport) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.signKey = Keys.hmacShaKeyFor(getSecretKeyByteArray(UTF_8));
        this.messageSupport = messageSupport;
    }

    @Override
    public String createToken(Long value, Long expiredTime) {
        Date now = new Date();
        return Jwts.builder()
                .claim(X_AUTH_TOKEN.getName(), value)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiredTime))
                .signWith(signKey, HS256)
                .compact();
    }

    public String createAccessToken(Long value) {
        return createToken(value, accessExpiredTime);
    }

    public String createRefreshToken(Long value) {
        return createToken(value, refreshExpiredTime);
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

    @Override
    public Long getSubject(String token) {
        return Long.parseLong(getClaims(token).get(X_AUTH_TOKEN.getName()).toString());
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(X_AUTH_TOKEN.getName());
        if (hasText(token)) return token;
        return null;
    }

    private boolean isEqualKey(String token, Long key) {
        Long subject = getSubject(token);
        return subject.equals(key);
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSecretKeyByteArray())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            throw new SecurityException(messageSupport.get("M9"));
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException(messageSupport.get("M8"));
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException(messageSupport.get("M7"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(messageSupport.get("M6"));
        } catch (ExpiredJwtException e) {
            throw new SecurityException(messageSupport.get("M5"));
        }
    }

    private byte[] getSecretKeyByteArray(Charset charset) {
        return secretKey.getBytes(charset);
    }

    private byte[] getSecretKeyByteArray() {
        return secretKey.getBytes();
    }
}
