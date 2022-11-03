package io.github.ones1kk.authenticationtemplate.web.provider.hanlder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FirstAuthenticationSuccessHandler extends AbstractAuthenticationHandler implements AuthenticationSuccessHandler {

    public FirstAuthenticationSuccessHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // TODO create token
    }
}
