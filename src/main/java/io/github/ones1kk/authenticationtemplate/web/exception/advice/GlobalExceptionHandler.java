package io.github.ones1kk.authenticationtemplate.web.exception.advice;

import io.github.ones1kk.authenticationtemplate.web.exception.model.CodeException;
import io.github.ones1kk.authenticationtemplate.web.exception.model.ExceptionResponse;
import io.github.ones1kk.authenticationtemplate.web.message.MessageSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSupport messageSupport;

    @ExceptionHandler(CodeException.class)
    public ExceptionResponse handle(CodeException e, HttpServletResponse response) {
        String message = messageSupport.get(e.getCode(), e.getArgs());
        response.setStatus(e.getStatus().value());
        return new ExceptionResponse(e.getCode(), message);
    }
}
