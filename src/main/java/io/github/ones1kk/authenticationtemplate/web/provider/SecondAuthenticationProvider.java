package io.github.ones1kk.authenticationtemplate.web.provider;

import io.github.ones1kk.authenticationtemplate.web.dto.SecondLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class SecondAuthenticationProvider implements AuthenticationProvider {

    private static final String ANSWER = "123456";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SecondLoginDto secondLoginDto = (SecondLoginDto) authentication.getPrincipal();
        String authenticationNumber = secondLoginDto.getAuthenticationNumber();

        if (!ANSWER.equals(authenticationNumber)) {
            throw new BadCredentialsException("M3");
        }

//        Authentication token = new SecondAuthenticationToken();

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SecondAuthenticationProvider.class.isAssignableFrom(authentication);
    }
}
