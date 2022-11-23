package io.github.ones1kk.authentication.web.provider.hanlder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ones1kk.authentication.web.exception.MessageSupport;
import io.github.ones1kk.authentication.web.exception.model.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public abstract class AbstractAuthenticationHandler {

    private final ObjectMapper objectMapper;

    private final MessageSupport messageSupport;

    protected void setErrorMessage(HttpServletResponse response, AuthenticationException exception) throws IOException {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(), messageSupport.get(exception.getMessage()));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter()
                .write(writePretty(exceptionResponse));
    }

    private <V> String writePretty(V value) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(value);
    }

}
