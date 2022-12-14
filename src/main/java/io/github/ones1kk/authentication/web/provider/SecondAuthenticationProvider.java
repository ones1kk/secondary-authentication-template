package io.github.ones1kk.authentication.web.provider;

import io.github.ones1kk.authentication.web.token.SecondAuthenticationToken;
import io.github.ones1kk.authentication.web.token.model.SecondAuthenticationUser;
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
        SecondAuthenticationUser secondUser = (SecondAuthenticationUser) authentication.getPrincipal();
        String authenticationNumber = secondUser.getCertificationNumber();

        if (!authenticationNumber.equals(ANSWER)) {
            throw new BadCredentialsException("M3");
        }

        Authentication token = new SecondAuthenticationToken(secondUser.getId());
        token.setAuthenticated(true);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SecondAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
