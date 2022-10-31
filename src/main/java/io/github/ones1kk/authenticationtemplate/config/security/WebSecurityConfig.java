package io.github.ones1kk.authenticationtemplate.config.security;

import io.github.ones1kk.authenticationtemplate.config.constant.AuthenticationPath;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.stream.Stream;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        configure(http);
        authorizeRequest(http);
        exceptionHandling(http);
        return http.build();
    }

    private void exceptionHandling(HttpSecurity http) {

    }


    private void configure(HttpSecurity http) throws Exception {
        // prevent to cross site request forgery.
        // if you establish REST API server, you do not need to configure this setting.
        http.csrf().disable();
        // prevent to cross-origin resource sharing.
        http.cors().disable();
        // REST API server should be session stateless.
        http.sessionManagement().sessionCreationPolicy(STATELESS);
    }

    private void authorizeRequest(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, staticResources()).permitAll()
                .antMatchers(HttpMethod.GET, "/favicon.ic").permitAll()

                // 1st login
                .antMatchers(AuthenticationPath.FIRST_LOGIN_API_PATH.getPath())
                .hasAnyAuthority()

                // 2nd login
                .antMatchers(AuthenticationPath.SECOND_LOGIN_API_PATH.getPath())
                .permitAll()

                // should be authenticated paths
                .antMatchers("/test")
                .hasAnyAuthority()

                // exceptionally permitted paths
//                .antMatchers(allowedResources())
//                .permitAll()

                // logout
                .antMatchers(AuthenticationPath.LOGOUT_API_PATH.getPath())
                .permitAll();
    }

    private static String[] staticResources() {
        return Stream.of("/static")
                .map(resource -> resource + "/*/**")
                .toArray(String[]::new);
    }

    private static String[] allowedResources() {
        return Stream.of("").toArray(String[]::new);
    }
}
