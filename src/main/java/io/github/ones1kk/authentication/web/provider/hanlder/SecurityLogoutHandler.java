package io.github.ones1kk.authentication.web.provider.hanlder;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static io.github.ones1kk.authentication.web.token.provider.constant.TokenHeaderName.X_AUTH_TOKEN;
import static org.springframework.util.StringUtils.hasText;

public class SecurityLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader(X_AUTH_TOKEN.getName());
        if (!hasText(token)) {
            throw new AccessDeniedException("M3");
        }
    }
}
