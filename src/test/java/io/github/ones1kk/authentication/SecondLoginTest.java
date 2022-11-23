package io.github.ones1kk.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ones1kk.authentication.web.dto.SecondLoginDto;
import io.github.ones1kk.authentication.web.token.FirstAuthenticationToken;
import io.github.ones1kk.authentication.web.token.provider.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class SecondLoginTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Value(value = "${token.secret-key}")
    private String secretKey;

    private MockMvc mockMvc;

    private String token;

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        JwtProvider<FirstAuthenticationToken> jwtProvider = new JwtProvider<>(secretKey, objectMapper);
        FirstAuthenticationToken authenticationToken = new FirstAuthenticationToken(1L);
        authenticationToken.setAuthenticated(true);
        token = jwtProvider.createAccessToken(authenticationToken);
    }

    @Nested
    @DisplayName("second login test")
    class SecondIntegrationLoginTest {

        @Test
        @DisplayName("second login success test")
        void login_success() throws Exception {
            // given
            var longUserDto = objectMapper.writeValueAsString(new SecondLoginDto("123456"));

            // when
            var action = mockMvc.perform(post("/login/second")
                    .content(longUserDto)
                    .contentType(APPLICATION_JSON)
                    .header("X_AUTH_TOKEN", token));

            // then
            action.andExpectAll(status().isOk(), header().exists("X_AUTH_TOKEN"));
        }

        @Test
        @DisplayName("second login fail, not token")
        void login_fail_01() throws Exception {
            // given
            var longUserDto = objectMapper.writeValueAsString(new SecondLoginDto("123456"));

            // when
            var action = mockMvc.perform(post("/login/second")
                    .content(longUserDto)
                    .contentType(APPLICATION_JSON));

            // then
            action.andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("second login fail, wrong token")
        void login_fail_02() throws Exception {
            // given
            var longUserDto = objectMapper.writeValueAsString(new SecondLoginDto("123456"));

            // when
            var action = mockMvc.perform(post("/login/second")
                    .content(longUserDto)
                    .contentType(APPLICATION_JSON)
                    .header("X_AUTH_TOKEN", UUID.randomUUID()));

            // then
            action.andExpect(status().isUnauthorized());
        }

    }
}
