package io.github.ones1kk.authenticationtemplate.web.token;

import io.github.ones1kk.authenticationtemplate.web.token.authority.CustomGrantedAuthority;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class SecondAuthenticationToken extends AbstractAuthenticationToken {

    public static final GrantedAuthority AUTHORITY = new CustomGrantedAuthority("FIRST");

    private final Collection<GrantedAuthority> authorities;

    private final Object principal;

    public SecondAuthenticationToken(Object principal) {
        super(Collections.singletonList(AUTHORITY));
        this.authorities = Collections.singletonList(AUTHORITY);
        this.principal = principal;
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
