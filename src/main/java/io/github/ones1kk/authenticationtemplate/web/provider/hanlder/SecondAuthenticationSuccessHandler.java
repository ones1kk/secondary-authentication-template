package io.github.ones1kk.authenticationtemplate.web.provider.hanlder;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ones1kk.authenticationtemplate.domain.User;
import io.github.ones1kk.authenticationtemplate.domain.UserToken;
import io.github.ones1kk.authenticationtemplate.service.UserService;
import io.github.ones1kk.authenticationtemplate.service.UserTokenService;
import io.github.ones1kk.authenticationtemplate.web.exception.MessageSupport;
import io.github.ones1kk.authenticationtemplate.web.token.SecondAuthenticationToken;
import io.github.ones1kk.authenticationtemplate.web.token.provider.JwtProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static io.github.ones1kk.authenticationtemplate.web.token.provider.constant.TokenHeaderName.X_AUTH_TOKEN;

public class SecondAuthenticationSuccessHandler extends AbstractAuthenticationHandler implements AuthenticationSuccessHandler {

    private final JwtProvider<Authentication> jwtProvider;

    private final UserTokenService userTokenService;

    private final UserService userService;

    public SecondAuthenticationSuccessHandler(ObjectMapper objectMapper, MessageSupport messageSupport,
                                              JwtProvider<Authentication> jwtProvider, UserTokenService userTokenService, UserService userService) {
        super(objectMapper, messageSupport);
        this.jwtProvider = jwtProvider;
        this.userTokenService = userTokenService;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Long id = (Long) authentication.getPrincipal();
        Authentication token = new SecondAuthenticationToken(id);

        String accessToken = jwtProvider.createAccessToken(token);
        String refreshToken = jwtProvider.createRefreshToken(token);

        User user = userService.findById(id);
        UserToken userToken = UserToken.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();

        userTokenService.save(userToken);

        response.setHeader(X_AUTH_TOKEN.getName(), accessToken);
    }
}
