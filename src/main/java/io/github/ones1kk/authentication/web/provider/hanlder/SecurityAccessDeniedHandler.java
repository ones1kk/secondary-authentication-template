package io.github.ones1kk.authentication.web.provider.hanlder;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ones1kk.authentication.web.exception.MessageSupport;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityAccessDeniedHandler extends AbstractResponseHandler implements AccessDeniedHandler {

    public SecurityAccessDeniedHandler(ObjectMapper objectMapper, MessageSupport messageSupport) {
        super(objectMapper, messageSupport);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        setMessage(response, accessDeniedException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
