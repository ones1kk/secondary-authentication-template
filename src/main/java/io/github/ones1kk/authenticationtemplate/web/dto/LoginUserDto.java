package io.github.ones1kk.authenticationtemplate.web.dto;

import io.github.ones1kk.authenticationtemplate.service.dto.LoginUser;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginUserDto {
    private String id;
    private String password;

    public void encodePassword(String encoded) {
        this.password = encoded;
    }

    public static LoginUser to(String id, String password) {
        return LoginUser.builder()
                .id(id)
                .password(password)
                .build();
    }
}
