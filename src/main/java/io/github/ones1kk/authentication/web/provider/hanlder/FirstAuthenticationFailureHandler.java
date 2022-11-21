package io.github.ones1kk.authentication.web.provider.hanlder;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ones1kk.authentication.web.exception.MessageSupport;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FirstAuthenticationFailureHandler extends AbstractAuthenticationHandler implements AuthenticationFailureHandler {

    public FirstAuthenticationFailureHandler(ObjectMapper objectMapper, MessageSupport messageSupport) {
        super(objectMapper, messageSupport);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        setErrorMessage(response, exception);
    }
}
