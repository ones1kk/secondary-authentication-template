package io.github.ones1kk.authenticationtemplate.web.token;

import io.github.ones1kk.assertion.core.Asserts;
import io.github.ones1kk.authenticationtemplate.web.token.authority.CustomGrantedAuthority;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class FirstAuthenticationToken extends AbstractAuthenticationToken {

    public static final CustomGrantedAuthority AUTHORITY = new CustomGrantedAuthority("FIRST");

    private final Collection<CustomGrantedAuthority> authorities;

    private String name;

    private Object principal;

    private String credentials;

    protected FirstAuthenticationToken() {
        super(Collections.singletonList(AUTHORITY));
        this.authorities = Collections.singletonList(AUTHORITY);
    }

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

        Asserts.that(principal)
                .as("FirstAuthenticationToken.principal is not allowed to be null")
                .isNotNull();
        Asserts.that(credentials)
                .as("FirstAuthenticationToken.credentials should not be null").
                isNotNull();
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    @Override
    public String getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        if (this.principal instanceof Long) {
            return Long.parseLong(String.valueOf(this.principal));
        }
        return this.principal;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<GrantedAuthority> getAuthorities() {
        return (Collection<GrantedAuthority>) (Object) this.authorities;
    }
}
