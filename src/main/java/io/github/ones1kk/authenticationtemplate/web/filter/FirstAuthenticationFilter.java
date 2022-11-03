package io.github.ones1kk.authenticationtemplate.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ones1kk.authenticationtemplate.config.constant.AuthenticationPath;
import io.github.ones1kk.authenticationtemplate.web.dto.FirstLoginUserDto;
import io.github.ones1kk.authenticationtemplate.web.token.FirstAuthenticationToken;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FirstAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    private static final RequestMatcher DEFAULT_REQUEST_MATCHER = new AntPathRequestMatcher(
            AuthenticationPath.FIRST_LOGIN_API_PATH.getPath(), HttpMethod.POST.name());

    public FirstAuthenticationFilter(ObjectMapper objectMapper) {
        super(DEFAULT_REQUEST_MATCHER);
        this.objectMapper = objectMapper;
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return super.requiresAuthentication(request, response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        FirstLoginUserDto loginUser = objectMapper.readValue(request.getReader(), FirstLoginUserDto.class);
        Authentication token = new FirstAuthenticationToken(loginUser.getId(), loginUser.getPassword());

        return super.getAuthenticationManager().authenticate(token);
    }
}
