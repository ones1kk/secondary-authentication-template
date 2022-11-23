package io.github.ones1kk.authentication.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SecondLoginDto {
    private String authenticationNumber;
}
