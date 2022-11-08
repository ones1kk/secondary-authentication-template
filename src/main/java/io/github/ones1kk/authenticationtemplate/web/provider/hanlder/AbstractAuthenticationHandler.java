package io.github.ones1kk.authenticationtemplate.web.provider.hanlder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ones1kk.authenticationtemplate.web.exception.MessageSupport;
import io.github.ones1kk.authenticationtemplate.web.exception.model.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public abstract class AbstractAuthenticationHandler {

    private final ObjectMapper objectMapper;

    private final MessageSupport messageSupport;

    protected void setErrorMessage(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(), messageSupport.get(exception.getMessage()));
        response.getWriter()
                .write(writePretty(exceptionResponse));
    }

    protected void setResponseMessage(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.getWriter()
                .write(writePretty(new Object()));
    }

    private <V> String writePretty(V value) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(value);
    }

}
