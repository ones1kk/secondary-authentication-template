package io.github.ones1kk.authenticationtemplate.web.token;

import io.github.ones1kk.assertion.core.Asserts;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class SecondAuthenticationToken extends AbstractAuthenticationToken {

    public static final GrantedAuthority AUTHORITY = new SimpleGrantedAuthority("FIRST");

    private final Collection<GrantedAuthority> authorities;

    private final Object principal;

    public SecondAuthenticationToken(Object principal) {
        super(Collections.singletonList(AUTHORITY));
        this.authorities = Collections.singletonList(AUTHORITY);
        this.principal = principal;

        Asserts.that(principal)
                .as("SecondAuthenticationToken.principal is not allowed to be null")
                .isNotNull();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
}
