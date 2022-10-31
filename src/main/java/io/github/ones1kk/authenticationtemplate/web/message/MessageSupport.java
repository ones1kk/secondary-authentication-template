package io.github.ones1kk.authenticationtemplate.web.message;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.manager.util.SessionUtils;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageSupport {

    private final MessageSource messageSource;

    public String get(String code, @Nullable Object... args) {
        return messageSource.getMessage(code, args, Locale.getDefault());
    }
}
