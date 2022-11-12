package io.github.ones1kk.authenticationtemplate.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecondAuthenticationDto {

    private Long id;
    private String authenticationNumber;
}
