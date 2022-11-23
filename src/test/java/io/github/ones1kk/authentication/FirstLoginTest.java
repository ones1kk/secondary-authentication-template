package io.github.ones1kk.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ones1kk.authentication.web.dto.FirstLoginUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class FirstLoginTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Nested
    @DisplayName("first login test")
    class FirstIntegrationLoginTest {

        @Test
        @DisplayName("first login success test")
        void login_success() throws Exception {
            // given
            var loginUserDto = objectMapper.writeValueAsString(new FirstLoginUserDto("ones1kk", "123123"));

            // when
            var action = mockMvc.perform(post("/login/first")
                    .content(loginUserDto)
                    .contentType(APPLICATION_JSON));

            // then
            action.andExpectAll(status().isOk(), header().exists("X_AUTH_TOKEN"));
        }

        @Test
        @DisplayName("first login fail test, wrong id")
        void login_fail_01() throws Exception {
            // given
            var loginUserDto = objectMapper.writeValueAsString(new FirstLoginUserDto("anonymous", "123123"));

            // when
            var action = mockMvc.perform(post("/login/first")
                    .content(loginUserDto)
                    .contentType(APPLICATION_JSON));

            // then
            action.andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("first login fail test, wrong password")
        void login_fail_02() throws Exception {
            // given
            var loginUserDto = objectMapper.writeValueAsString(new FirstLoginUserDto("ones1kk", "321321"));

            // when
            var action = mockMvc.perform(post("/login/first")
                    .content(loginUserDto)
                    .contentType(APPLICATION_JSON));

            // then
            action.andExpect(status().isUnauthorized());
        }
    }
}
