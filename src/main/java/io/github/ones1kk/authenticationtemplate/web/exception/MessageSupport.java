package io.github.ones1kk.authenticationtemplate.web.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageSupport {

    private final MessageSource messageSource;

    public String get(String code, @Nullable Object... args) {
        return messageSource.getMessage(code, args, getLocale());
    }

    @NonNull
    private Locale getLocale() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes == null ? Locale.getDefault() : RequestContextUtils.getLocale(attributes.getRequest());
    }
}
