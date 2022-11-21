package io.github.ones1kk.authentication.service.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginUser {

    private String id;
    private String password;
}
