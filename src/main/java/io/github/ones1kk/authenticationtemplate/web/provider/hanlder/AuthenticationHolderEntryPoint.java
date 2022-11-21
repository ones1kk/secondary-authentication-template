package io.github.ones1kk.authenticationtemplate.web.provider.hanlder;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ones1kk.authenticationtemplate.web.exception.MessageSupport;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationHolderEntryPoint extends AbstractAuthenticationHandler implements AuthenticationEntryPoint {

    public AuthenticationHolderEntryPoint(ObjectMapper objectMapper, MessageSupport messageSupport) {
        super(objectMapper, messageSupport);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        setErrorMessage(response, exception);
    }
}
