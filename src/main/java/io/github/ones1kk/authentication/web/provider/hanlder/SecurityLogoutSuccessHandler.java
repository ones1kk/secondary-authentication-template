package io.github.ones1kk.authentication.web.provider.hanlder;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ones1kk.authentication.web.exception.MessageSupport;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static io.github.ones1kk.authentication.web.token.provider.constant.TokenHeaderName.X_AUTH_TOKEN;
import static org.springframework.util.StringUtils.hasText;

public class SecurityLogoutSuccessHandler extends AbstractAuthenticationHandler implements LogoutSuccessHandler {

    public SecurityLogoutSuccessHandler(ObjectMapper objectMapper, MessageSupport messageSupport) {
        super(objectMapper, messageSupport);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // write logout success message.
        setMessage(response, "M7", HttpStatus.OK);
    }
}
