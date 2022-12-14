package io.github.ones1kk.authentication.web.provider.hanlder;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ones1kk.authentication.web.exception.MessageSupport;
import io.github.ones1kk.authentication.web.token.FirstAuthenticationToken;
import io.github.ones1kk.authentication.web.token.provider.JwtProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static io.github.ones1kk.authentication.web.token.provider.constant.TokenHeaderName.X_AUTH_TOKEN;

public class FirstAuthenticationSuccessHandler extends AbstractResponseHandler implements AuthenticationSuccessHandler {

    private final JwtProvider<Authentication> jwtProvider;

    public FirstAuthenticationSuccessHandler(ObjectMapper objectMapper, MessageSupport messageSupport, JwtProvider<Authentication> jwtProvider) {
        super(objectMapper, messageSupport);
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Long id = (Long) authentication.getPrincipal();
        Authentication token = new FirstAuthenticationToken(id);
        String accessToken = jwtProvider.createAccessToken(token);

        response.setHeader(X_AUTH_TOKEN.getName(), accessToken);
    }
}
