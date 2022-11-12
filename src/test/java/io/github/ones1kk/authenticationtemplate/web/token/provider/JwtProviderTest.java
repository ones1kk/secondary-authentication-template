package io.github.ones1kk.authenticationtemplate.web.token.provider;

import io.github.ones1kk.authenticationtemplate.web.exception.MessageSupport;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtProviderTest {

    private static final String SECRET_KEY = UUID.randomUUID().toString();

    @Mock
    private MessageSupport messageSupport;

    private JwtProvider<Long> jwtProvider;

    @BeforeEach
    void setup() {
        jwtProvider = new JwtProvider<>(SECRET_KEY, messageSupport);
    }

    @Test
    @DisplayName("create token")
    void createToken() throws Exception {
        // given
        List<String> tokens = new ArrayList<>();

        // when
        for (long i = 0; i < 10; i++) {
            tokens.add(jwtProvider.createAccessToken(i));
        }
        boolean isUnique = tokens.stream()
                .allMatch(new HashSet<>()::add);

        // then
        assertThat(tokens).extracting(Object::toString)
                .allMatch(Objects::nonNull);
        assertThat(tokens).extracting(String::length)
                .allMatch(length -> length.equals(135));
        assertThat(isUnique).isTrue();
    }

    @Nested
    class isValidTest {

        final Long id = 1L;

        @Test
        @DisplayName("verify same key or not")
        void isValid_success() throws Exception {
            // given
            String token = jwtProvider.createToken(id, 10000L);

            // when
            boolean valid = jwtProvider.isValid(token, id);

            // then
            assertThat(valid).isTrue();
        }

        @Test
        @DisplayName("different key value")
        void isValid_fail_1() throws Exception {
            // given
            String token = jwtProvider.createToken(id, 1000L);

            // when
            boolean valid = jwtProvider.isValid(token, 2L);

            // then
            assertThat(valid).isFalse();
        }

        @Test
        @DisplayName("different key value and after expired time")
        void isValid_fail_2() throws Exception {
            // given
            String token = jwtProvider.createToken(id, 500L);

            // when
            Thread.sleep(1000L);

            // then
            assertThatThrownBy(() -> jwtProvider.isValid(token, 2L))
                    .isInstanceOf(SecurityException.class);
        }

        @Test
        @DisplayName("ask after expired time")
        void isValid_fail_3() throws Exception {
            // given
            String token = jwtProvider.createToken(id, 500L);

            // when
            Thread.sleep(1000L);

            // then
            assertThatThrownBy(() -> jwtProvider.isValid(token, 2L))
                    .isInstanceOf(SecurityException.class);
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
            String token = jwtProvider.createToken(id, 1000L);

            // when
            Thread.sleep(1500L);

            // then
            assertThatThrownBy(() -> jwtProvider.isExpired(token))
                    .isInstanceOf(SecurityException.class);
        }

        @Test
        @DisplayName("ask before expired time")
        void isExpired_fail() throws Exception {
            // given
            String token = jwtProvider.createToken(id, 1000L);

            // when
            Thread.sleep(200L);

            // then
            assertThatNoException().isThrownBy(() -> jwtProvider.isExpired(token));
            assertThat(jwtProvider.isExpired(token)).isFalse();
        }
    }

    @Nested
    class GetSubjectTest {

        @Test
        @DisplayName("get subject")
        void getSubject_success() throws Exception {
            // given
            final Long id = 1L;
            String token = jwtProvider.createToken(id, 2000L);

            // when
            Long subject = jwtProvider.getSubject(token);

            // then
            assertThat(subject).isEqualTo(id);
        }

        @Test
        @DisplayName("fail to get subject")
        void getSubject_fail() throws Exception {
            String token = UUID.randomUUID().toString();

            assertThatThrownBy(() -> jwtProvider.getSubject(token))
                    .isInstanceOf(MalformedJwtException.class);
        }
    }

}