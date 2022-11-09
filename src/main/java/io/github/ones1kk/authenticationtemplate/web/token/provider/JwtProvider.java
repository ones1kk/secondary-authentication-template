package io.github.ones1kk.authenticationtemplate.web.token.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.nio.charset.StandardCharsets.UTF_8;

public class JwtProvider<T extends Long> implements HttpRequestTokenProvider<Long> {

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
                .setExpiration(new Date(now.getTime() + expiredTime))
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
        Claims claims = getClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        Optional<Cookie> token = Stream.of(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst();
        if (token.isEmpty()) return null;

        return token.get()
                .getValue();
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
