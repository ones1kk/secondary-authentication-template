package io.github.ones1kk.authenticationtemplate.web.token;

import io.github.ones1kk.authenticationtemplate.web.token.authority.CustomGrantedAuthority;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class FirstAuthenticationToken extends AbstractAuthenticationToken {

    public static final GrantedAuthority AUTHORITY = new CustomGrantedAuthority("FIRST");

    private final Collection<GrantedAuthority> authorities;

    private final Object principal;

    private String credentials;

    public FirstAuthenticationToken(Object principal) {
        super(Collections.singletonList(AUTHORITY));
        this.authorities = Collections.singletonList(AUTHORITY);
        this.principal = principal;
    }

    public FirstAuthenticationToken(Object principal, String credentials) {
        super(Collections.singletonList(AUTHORITY));
        this.authorities = Collections.singletonList(AUTHORITY);
        this.principal = principal;
        this.credentials = credentials;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

    @Override
    public String getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
}
