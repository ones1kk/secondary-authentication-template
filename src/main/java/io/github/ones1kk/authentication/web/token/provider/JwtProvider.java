package io.github.ones1kk.authentication.web.token.provider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Deserializer;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import static io.github.ones1kk.authentication.web.token.provider.constant.TokenHeaderName.X_AUTH_TOKEN;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.StringUtils.hasText;

public class JwtProvider<T extends Authentication> implements HttpRequestTokenProvider<T> {

    // 1 hours
    private final long accessExpiredTime = Duration.ofHours(1).toMillis();

    // 3 hours
    private final long refreshExpiredTime = Duration.ofHours(3).toMillis();

    private final String secretKey;

    private final Key signKey;

    private final ObjectMapper objectMapper;

    private static final Deserializer<Map<String, ?>> JWT_DESERIALIZER = new JacksonDeserializer<>(new ObjectMapper().enable(DeserializationFeature.USE_LONG_FOR_INTS));

    @SuppressWarnings("unchecked")
    public JwtProvider(String secretKey, ObjectMapper objectMapper) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.signKey = Keys.hmacShaKeyFor(getSecretKeyByteArray(UTF_8));
        this.objectMapper = objectMapper;
    }

    @Override
    public String createToken(T value, Long expiredTime) {
        Date now = new Date();
        return Jwts.builder()
                .claim(X_AUTH_TOKEN.getName(), value)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiredTime))
                .signWith(signKey, HS256)
                .compact();
    }

    public String createAccessToken(T value) {
        return createToken(value, accessExpiredTime);
    }

    public String createRefreshToken(T value) {
        return createToken(value, refreshExpiredTime);
    }

    @Override
    public boolean isExpired(String token) {
        Claims claims = getClaims(token);
        if (claims == null) return true;
        return claims.getExpiration()
                .before(new Date());
    }

    @Override
    public boolean isValid(String token, T key) throws Exception {
        return isEqualKey(token, key) && !isExpired(token);
    }

    @Override
    public T getAuthentication(String token, Class<? extends T> clazz) {
        Claims claims = getClaims(token);
        if (claims == null) {
            return null;
        }
        Object authentication = claims.get(X_AUTH_TOKEN.getName());
        return objectMapper.convertValue(authentication, clazz);
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(X_AUTH_TOKEN.getName());
        if (hasText(token)) return token;
        return null;
    }

    @SuppressWarnings("unchecked")
    private boolean isEqualKey(String token, T key) throws Exception {
        T authentication = getAuthentication(token, (Class<? extends T>) key.getClass());
        if (authentication == null) {
            return false;
        }
        return authentication.equals(key);
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .deserializeJsonWith(JWT_DESERIALIZER)
                    .setSigningKey(getSecretKeyByteArray())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    private byte[] getSecretKeyByteArray(Charset charset) {
        return secretKey.getBytes(charset);
    }

    private byte[] getSecretKeyByteArray() {
        return secretKey.getBytes();
    }
}
