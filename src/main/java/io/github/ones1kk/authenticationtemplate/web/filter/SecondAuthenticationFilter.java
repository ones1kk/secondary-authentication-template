package io.github.ones1kk.authenticationtemplate.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ones1kk.authenticationtemplate.config.constant.AuthenticationPath;
import io.github.ones1kk.authenticationtemplate.web.dto.SecondLoginDto;
import io.github.ones1kk.authenticationtemplate.web.token.model.SecondAuthenticationUser;
import io.github.ones1kk.authenticationtemplate.web.token.FirstAuthenticationToken;
import io.github.ones1kk.authenticationtemplate.web.token.SecondAuthenticationToken;
import io.github.ones1kk.authenticationtemplate.web.token.provider.JwtProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

public class SecondAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    private final JwtProvider<Authentication> jwtProvider;

    private static final RequestMatcher DEFAULT_REQUEST_MATCHER = new AntPathRequestMatcher(
            AuthenticationPath.SECOND_LOGIN_API_PATH.getPath(), HttpMethod.POST.name());

    public SecondAuthenticationFilter(ObjectMapper objectMapper, JwtProvider<Authentication> jwtProvider) {
        super(DEFAULT_REQUEST_MATCHER);
        this.objectMapper = objectMapper;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        boolean required = super.requiresAuthentication(request, response);

        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        if (token == null) return required;

        // 2nd authentication is required when 1st authentication is succeeded.
        required &= (token instanceof FirstAuthenticationToken);
        return required;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        SecondLoginDto secondLoginDto = objectMapper.readValue(request.getReader(), SecondLoginDto.class);
        String token = jwtProvider.resolveToken(request);
        if (!hasText(token)) throw new SecurityException("M9");

        Authentication authentication = jwtProvider.getAuthentication(token, FirstAuthenticationToken.class);
        Long id = (Long) authentication.getPrincipal();

        Authentication authenticationToken = new SecondAuthenticationToken(new SecondAuthenticationUser(id, secondLoginDto.getAuthenticationNumber()));
        return super.getAuthenticationManager().authenticate(authenticationToken);
    }
}
