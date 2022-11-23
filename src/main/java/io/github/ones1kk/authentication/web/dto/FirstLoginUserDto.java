package io.github.ones1kk.authentication.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FirstLoginUserDto {
    private String id;
    private String password;
}
