package io.github.ones1kk.authentication.web.provider.hanlder;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ones1kk.authentication.web.exception.MessageSupport;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static io.github.ones1kk.authentication.web.token.provider.constant.TokenHeaderName.X_AUTH_TOKEN;
import static org.springframework.util.StringUtils.hasText;

public class SecurityAccessDeniedHandler extends AbstractAuthenticationHandler implements AccessDeniedHandler {

    public SecurityAccessDeniedHandler(ObjectMapper objectMapper, MessageSupport messageSupport) {
        super(objectMapper, messageSupport);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    setMessage(response, accessDeniedException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
