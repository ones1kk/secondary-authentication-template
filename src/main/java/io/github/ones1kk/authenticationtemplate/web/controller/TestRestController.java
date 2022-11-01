package io.github.ones1kk.authenticationtemplate.web.controller;

import io.github.ones1kk.authenticationtemplate.web.exception.model.CodeException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

    @GetMapping("/test")
    public String hello() {
        throw new CodeException("test");
    }
}
