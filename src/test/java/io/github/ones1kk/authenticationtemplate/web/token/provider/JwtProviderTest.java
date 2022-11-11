package io.github.ones1kk.authenticationtemplate.web.token.provider;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtProviderTest {
    private static final String SECRET_KEY = "c88d74ba-1554-48a4-b549-b926f5d77c9e";

    JwtProvider<Long> jwtProvider = new JwtProvider<>(SECRET_KEY);

    @Test
    void createToken() throws Exception {
        // given
        List<String> tokens = new ArrayList<>();
        // when
        for (long i = 0; i < 10; i++) {
            tokens.add(jwtProvider.createToken(i));
        }
        boolean isUnique = tokens.stream()
                .allMatch(new HashSet<>()::add);


        // then
        assertThat(tokens).extracting(String::length)
                .allMatch(length -> length.equals(135));
        assertThat(isUnique).isTrue();
    }

    @Nested
    @DisplayName("verify expired time of access token ")
    class JWTExpiredTest {
        final Long id = 1L;

        @Test
        @DisplayName("request after expired time")
        void isExpired_true() throws Exception {
            // given
            String token = jwtProvider.createToken(id, 1000L);

            // when
            Thread.sleep(1500L);

            // then
            assertThatThrownBy(() -> jwtProvider.isExpired(token))
                    .isInstanceOf(SecurityException.class);
        }

        @Test
        @DisplayName("request before expired time")
        void isExpired_false() throws Exception {
            // given
            String token = jwtProvider.createToken(id, 1000L);

            // when
            Thread.sleep(200L);

            // then
            assertThat(jwtProvider.isExpired(token)).isFalse();
        }
    }

}