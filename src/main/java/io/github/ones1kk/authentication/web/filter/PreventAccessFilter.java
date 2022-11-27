package io.github.ones1kk.authentication.web.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ones1kk.authentication.web.exception.MessageSupport;
import io.github.ones1kk.authentication.web.exception.model.GlobalResponseModel;
import io.github.ones1kk.authentication.web.token.SecondAuthenticationToken;
import io.github.ones1kk.authentication.web.token.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor
public class PreventAccessFilter extends OncePerRequestFilter {

    private final JwtProvider<Authentication> jwtProvider;

    private final ObjectMapper objectMapper;

    private final MessageSupport messageSupport;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.resolveToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
        }
        Authentication authentication = jwtProvider.getAuthentication(token, SecondAuthenticationToken.class);

        // if user have had 2nd AuthenticationToken
        if (authentication != null) {
            if (authentication instanceof SecondAuthenticationToken) {
                GlobalResponseModel globalResponseModel = new GlobalResponseModel(UNAUTHORIZED.value(), messageSupport.get("M6"));
                response.setStatus(UNAUTHORIZED.value());
                response.setContentType(APPLICATION_JSON.getType());
                response.getWriter()
                        .write(writePretty(globalResponseModel));
            }
        }

        filterChain.doFilter(request, response);
    }

    private <V> String writePretty(V value) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(value);
    }
}
