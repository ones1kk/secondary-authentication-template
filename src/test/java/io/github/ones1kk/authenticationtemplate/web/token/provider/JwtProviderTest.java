package io.github.ones1kk.authenticationtemplate.web.token.provider;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

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

        // then
        assertThat(tokens).extracting(String::length)
                .allMatch(length -> length.equals(135));
        assertThat(new HashSet<>(tokens).size()).isEqualTo(tokens.size());
    }

    @Test
    void isExpired() throws Exception {
        // given
        final Long id = 1L;

        // when
        String token = jwtProvider.createToken(id);
        Thread.sleep(2 * 90 * 60 * 1000L);
        boolean expired = jwtProvider.isExpired(token);

        // then
        System.out.println(expired);
    }

}