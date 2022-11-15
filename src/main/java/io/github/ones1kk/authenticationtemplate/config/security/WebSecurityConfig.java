package io.github.ones1kk.authenticationtemplate.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ones1kk.authenticationtemplate.service.UserService;
import io.github.ones1kk.authenticationtemplate.web.exception.MessageSupport;
import io.github.ones1kk.authenticationtemplate.web.filter.FirstAuthenticationFilter;
import io.github.ones1kk.authenticationtemplate.web.filter.FirstAuthenticationHolderFilter;
import io.github.ones1kk.authenticationtemplate.web.filter.SecondAuthenticationFilter;
import io.github.ones1kk.authenticationtemplate.web.filter.SecondAuthenticationHolderFilter;
import io.github.ones1kk.authenticationtemplate.web.provider.FirstAuthenticationProvider;
import io.github.ones1kk.authenticationtemplate.web.provider.SecondAuthenticationProvider;
import io.github.ones1kk.authenticationtemplate.web.provider.hanlder.FirstAuthenticationFailureHandler;
import io.github.ones1kk.authenticationtemplate.web.provider.hanlder.FirstAuthenticationSuccessHandler;
import io.github.ones1kk.authenticationtemplate.web.provider.hanlder.SecondAuthenticationFailureHandler;
import io.github.ones1kk.authenticationtemplate.web.provider.hanlder.SecondAuthenticationSuccessHandler;
import io.github.ones1kk.authenticationtemplate.web.token.FirstAuthenticationToken;
import io.github.ones1kk.authenticationtemplate.web.token.SecondAuthenticationToken;
import io.github.ones1kk.authenticationtemplate.web.token.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.util.List;
import java.util.stream.Stream;

import static io.github.ones1kk.authenticationtemplate.config.constant.AuthenticationPath.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final ObjectMapper objectMapper;

    private final UserService userService;

    private final MessageSupport messageSupport;

    @Value(value = "${token.secret-key}")
    private String secretKey;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        configure(http);
        authorizeRequest(http);
        login(http);
        logout(http);
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(new FirstAuthenticationProvider(passwordEncoder(), userService), new SecondAuthenticationProvider()));
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtProvider<Authentication> tokenProvider() {
        return new JwtProvider<>(secretKey, messageSupport, objectMapper);
    }

    private void login(HttpSecurity http) throws Exception {
        AbstractAuthenticationProcessingFilter firstFilter = new FirstAuthenticationFilter(objectMapper);
        firstFilter.setAuthenticationManager(authenticationManager());
        firstFilter.setAuthenticationFailureHandler(
                new FirstAuthenticationFailureHandler(objectMapper, messageSupport));
        firstFilter.setAuthenticationSuccessHandler(new FirstAuthenticationSuccessHandler(objectMapper, messageSupport, tokenProvider()));

        AbstractAuthenticationProcessingFilter secondFilter = new SecondAuthenticationFilter(objectMapper, tokenProvider());
        secondFilter.setAuthenticationManager(authenticationManager());
        secondFilter.setAuthenticationFailureHandler(
                new SecondAuthenticationFailureHandler(objectMapper, messageSupport));
        secondFilter.setAuthenticationSuccessHandler(new SecondAuthenticationSuccessHandler(objectMapper, messageSupport, tokenProvider()));

        http.addFilterBefore(firstFilter, FilterSecurityInterceptor.class)
                .addFilterBefore(new FirstAuthenticationHolderFilter(tokenProvider()), FilterSecurityInterceptor.class)
                .addFilterBefore(secondFilter, FilterSecurityInterceptor.class)
                .addFilterBefore(new SecondAuthenticationHolderFilter(tokenProvider()), FilterSecurityInterceptor.class);
    }

    private void logout(HttpSecurity http) throws Exception {
        http.logout()
                .logoutUrl(LOGOUT_API_PATH.getPath());
    }

    private void configure(HttpSecurity http) throws Exception {
        // prevent to cross site request forgery.
        // if you establish REST API server, you do not need to configure this setting.
        http.csrf().disable();
        // prevent to cross-origin resource sharing.
        http.cors().disable();
        // REST API server should be session stateless.
        http.sessionManagement()
                .sessionCreationPolicy(STATELESS);
    }

    private void authorizeRequest(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, staticResources()).permitAll()
                .antMatchers(HttpMethod.GET, "/favicon.ic").permitAll()

                // 1st login
                .antMatchers(FIRST_LOGIN_API_PATH.getPath())
                .permitAll()

                // 2nd login
                .antMatchers(SECOND_LOGIN_API_PATH.getPath())
                .hasAnyAuthority(FirstAuthenticationToken.AUTHORITY.getAuthority())

                // should be authenticated paths
                .antMatchers(notAllowedResources())
                .hasAnyAuthority(SecondAuthenticationToken.AUTHORITY.getAuthority())

                // exceptionally permitted paths
                .antMatchers(allowedResources())
                .permitAll()

                // logout
                .antMatchers(LOGOUT_API_PATH.getPath())
                .hasAnyAuthority(SecondAuthenticationToken.AUTHORITY.getAuthority());
    }


    private static String[] staticResources() {
        return Stream.of("/static")
                .map(resource -> resource + "/*/**")
                .toArray(String[]::new);
    }

    private String[] notAllowedResources() {
        return Stream.of("/not")
                .toArray(String[]::new);
    }

    private static String[] allowedResources() {
        return Stream.of("/test")
                .toArray(String[]::new);
    }
}
