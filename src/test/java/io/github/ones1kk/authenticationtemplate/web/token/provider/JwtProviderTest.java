package io.github.ones1kk.authenticationtemplate.web.token.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ones1kk.authenticationtemplate.web.token.FirstAuthenticationToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@ExtendWith(MockitoExtension.class)
class JwtProviderTest {

    private static final String SECRET_KEY = UUID.randomUUID().toString();

    @Spy
    private ObjectMapper objectMapper;

    private JwtProvider<Authentication> jwtProvider;

    @BeforeEach
    void setup() {
        jwtProvider = new JwtProvider<>(SECRET_KEY, objectMapper);
    }

    @Test
    @DisplayName("create token")
    void createToken() throws Exception {
        // given
        List<String> tokens = new ArrayList<>();

        // when
        for (long i = 0; i < 10; i++) {
            tokens.add(jwtProvider.createAccessToken(new FirstAuthenticationToken(i)));
        }
        boolean isUnique = tokens.stream()
                .allMatch(new HashSet<>()::add);

        // then
        assertThat(tokens).extracting(Object::toString)
                .allMatch(Objects::nonNull);
        assertThat(tokens).extracting(String::length)
                .allMatch(length -> length.equals(305));
        assertThat(isUnique).isTrue();
    }

    @Nested
    class isValidTest {

        final Long id = 1L;

        @Test
        @DisplayName("verify same key or not")
        void isValid_success() throws Exception {
            // given
            Authentication key = new FirstAuthenticationToken(id);
            String token = jwtProvider.createToken(key, 10000L);

            // when
            boolean valid = jwtProvider.isValid(token, key);

            // then
            assertThat(valid).isTrue();
        }

        @Test
        @DisplayName("different key value")
        void isValid_fail_1() throws Exception {
            // given
            Authentication key = new FirstAuthenticationToken(id);
            String token = jwtProvider.createToken(key, 1000L);

            // when
            boolean valid = jwtProvider.isValid(token, new FirstAuthenticationToken(2L));

            // then
            assertThat(valid).isFalse();
        }

        @Test
        @DisplayName("different key value and after expired time")
        void isValid_fail_2() throws Exception {
            // given
            Authentication key = new FirstAuthenticationToken(id);
            String token = jwtProvider.createToken(key, 500L);

            // when
            Thread.sleep(1000L);

            // then
            boolean isValid = jwtProvider.isValid(token, new FirstAuthenticationToken(2L));

            assertThat(isValid).isFalse();
        }

        @Test
        @DisplayName("ask after expired time")
        void isValid_fail_3() throws Exception {
            // given
            Authentication key = new FirstAuthenticationToken(id);
            String token = jwtProvider.createToken(key, 500L);

            // when
            Thread.sleep(1000L);
            boolean isValid = jwtProvider.isValid(token, new FirstAuthenticationToken(2L));

            // then
            assertThat(isValid).isFalse();
        }
    }

    @Nested
    @DisplayName("verify expired time of access token")
    class isExpiredTest {
        final Long id = 1L;

        @Test
        @DisplayName("ask after expired time")
        void isExpired_success() throws Exception {
            // given
            Authentication key = new FirstAuthenticationToken(id);
            String token = jwtProvider.createToken(key, 500L);

            // when
            Thread.sleep(1500L);
            boolean isExpired = jwtProvider.isExpired(token);

            // then
            assertThat(isExpired).isTrue();
        }

        @Test
        @DisplayName("ask before expired time")
        void isExpired_fail() throws Exception {
            // given
            Authentication key = new FirstAuthenticationToken(id);
            String token = jwtProvider.createToken(key, 5000L);

            // when
            Thread.sleep(200L);

            // then
            assertThatNoException().isThrownBy(() -> jwtProvider.isExpired(token));
            assertThat(jwtProvider.isExpired(token)).isFalse();
        }
    }

    @Nested
    class GetAuthenticationTest {

        @Test
        @DisplayName("get authentication")
        void getAuthentication_success() throws Exception {
            // given
            final Long id = 1L;
            Authentication key = new FirstAuthenticationToken(id);
            String token = jwtProvider.createToken(key, 5000L);

            // when
            Authentication authentication = jwtProvider.getAuthentication(token, FirstAuthenticationToken.class);
            // then
            assertThat(authentication.getCredentials()).isEqualTo(key.getCredentials());
            assertThat(authentication.getPrincipal()).isEqualTo(key.getPrincipal());
            assertThat(authentication.getAuthorities()).isEqualTo(key.getAuthorities());
        }

        @Test
        @DisplayName("fail to get authentication")
        void getAuthentication_fail() throws Exception {
            // given
            String token = UUID.randomUUID().toString();

            // when
            Authentication authentication = jwtProvider.getAuthentication(token, FirstAuthenticationToken.class);

            // then
            assertThat(authentication).isNull();
            ;
        }
    }

}