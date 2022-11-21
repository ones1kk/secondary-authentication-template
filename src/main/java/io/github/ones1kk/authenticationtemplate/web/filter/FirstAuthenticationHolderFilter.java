package io.github.ones1kk.authenticationtemplate.web.filter;

import io.github.ones1kk.authenticationtemplate.web.exception.model.AuthenticationHolderException;
import io.github.ones1kk.authenticationtemplate.web.token.FirstAuthenticationToken;
import io.github.ones1kk.authenticationtemplate.web.token.SecondAuthenticationToken;
import io.github.ones1kk.authenticationtemplate.web.token.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class FirstAuthenticationHolderFilter extends OncePerRequestFilter {

    private final JwtProvider<Authentication> jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.resolveToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = jwtProvider.getAuthentication(token, FirstAuthenticationToken.class);
        if (authentication instanceof SecondAuthenticationToken) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authentication == null) {
            throw new AuthenticationHolderException("M1");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        authentication.setAuthenticated(true);

        filterChain.doFilter(request, response);
    }
}
