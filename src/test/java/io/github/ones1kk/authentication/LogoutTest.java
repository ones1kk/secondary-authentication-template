package io.github.ones1kk.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ones1kk.authentication.web.token.SecondAuthenticationToken;
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

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class LogoutTest {

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

        JwtProvider<SecondAuthenticationToken> jwtProvider = new JwtProvider<>(secretKey, objectMapper);
        SecondAuthenticationToken authenticationToken = new SecondAuthenticationToken(1L);
        authenticationToken.setAuthenticated(true);
        token = jwtProvider.createAccessToken(authenticationToken);
    }

    @Nested
    @DisplayName("logout test")
    class IntegrationLogoutTest {

        @Test
        @DisplayName("logout success test")
        void logout_success() throws Exception {
            var action = mockMvc.perform(post("/logout")
                    .header("X_AUTH_TOKEN", token));

            action.andExpectAll(status().isOk(), unauthenticated(),
                    header().doesNotExist("X_AUTH_TOKEN"));
        }

        @Test
        @DisplayName("logout fail test, doesn't exist token")
        void logout_fail_01() throws Exception {
            var action = mockMvc.perform(post("/logout"));

            action.andExpectAll(status().is5xxServerError(),
                    header().doesNotExist("X_AUTH_TOKEN"));
        }

    }


}
