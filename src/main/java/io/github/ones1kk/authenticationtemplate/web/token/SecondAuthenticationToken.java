package io.github.ones1kk.authenticationtemplate.web.token;

import io.github.ones1kk.assertion.core.Asserts;
import io.github.ones1kk.authenticationtemplate.web.token.authority.CustomGrantedAuthority;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class SecondAuthenticationToken extends AbstractAuthenticationToken {

    public static final CustomGrantedAuthority AUTHORITY = new CustomGrantedAuthority("FIRST");

    private final Collection<CustomGrantedAuthority> authorities;

    private Object principal;

    private String name;

    protected SecondAuthenticationToken() {
        super(Collections.singletonList(AUTHORITY));
        this.authorities = Collections.singletonList(AUTHORITY);
    }

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
    @SuppressWarnings("unchecked")
    public Collection<GrantedAuthority> getAuthorities() {
        return (Collection<GrantedAuthority>) (Object) this.authorities;
    }
}
