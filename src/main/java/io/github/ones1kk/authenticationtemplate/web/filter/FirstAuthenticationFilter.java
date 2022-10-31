package io.github.ones1kk.authenticationtemplate.web.filter;

import io.github.ones1kk.authenticationtemplate.config.constant.AuthenticationPath;
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

public class FirstAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final RequestMatcher DEFAULT_REQUEST_MATCHER = new AntPathRequestMatcher(
            AuthenticationPath.FIRST_LOGIN_API_PATH.getPath(), HttpMethod.POST.name());

    public FirstAuthenticationFilter() {
        super(DEFAULT_REQUEST_MATCHER);
    }

    public FirstAuthenticationFilter(String requestPattern) {
        super(new AntPathRequestMatcher(requestPattern, HttpMethod.POST.name()));
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        boolean required = super.requiresAuthentication(request, response);

        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        if (token == null) return required;

        return required;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        request.getParameterNames().asIterator().forEachRemaining(name -> System.out.println(request.getParameter(name)));

        // call authentication provider.
        return super.getAuthenticationManager().authenticate(null);
    }
}
