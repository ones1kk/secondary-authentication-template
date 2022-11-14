package io.github.ones1kk.authenticationtemplate.web.token.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecondAuthenticationUser {

    private Long id;
    private String certificationNumber;
}
