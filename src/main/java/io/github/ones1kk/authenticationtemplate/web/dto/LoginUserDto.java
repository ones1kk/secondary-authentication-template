package io.github.ones1kk.authenticationtemplate.web.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginUserDto {
    private String id;
    private String password;
}
