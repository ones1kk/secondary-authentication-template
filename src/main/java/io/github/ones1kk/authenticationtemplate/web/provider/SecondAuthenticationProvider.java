package io.github.ones1kk.authenticationtemplate.web.provider;

import io.github.ones1kk.authenticationtemplate.web.dto.SecondLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class SecondAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SecondLoginDto secondLoginDto = (SecondLoginDto) authentication.getPrincipal();

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SecondAuthenticationProvider.class.isAssignableFrom(authentication);
    }
}
