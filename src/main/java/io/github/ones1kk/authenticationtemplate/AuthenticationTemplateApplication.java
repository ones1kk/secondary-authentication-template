package io.github.ones1kk.authenticationtemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class AuthenticationTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationTemplateApplication.class, args);
    }

}
