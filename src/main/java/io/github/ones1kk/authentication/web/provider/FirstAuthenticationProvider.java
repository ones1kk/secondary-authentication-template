package io.github.ones1kk.authentication.web.provider;

import io.github.ones1kk.authentication.domain.User;
import io.github.ones1kk.authentication.service.UserService;
import io.github.ones1kk.authentication.web.token.FirstAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class FirstAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User findUser = userService.findByUserId(id);
        if (!passwordEncoder.matches(password, findUser.getPassword())) {
            throw new BadCredentialsException("M1");
        }

        Authentication token = new FirstAuthenticationToken(findUser.getId());
        token.setAuthenticated(true);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return FirstAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
