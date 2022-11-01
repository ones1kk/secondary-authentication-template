package io.github.ones1kk.authenticationtemplate.web.exception.advice;

import io.github.ones1kk.authenticationtemplate.web.exception.model.CodeException;
import io.github.ones1kk.authenticationtemplate.web.exception.model.ExceptionResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CodeException.class)
    public ExceptionResponse handle(CodeException e, HttpServletResponse response) {
        String message = e.getMessage();
        System.out.println(e.getStatus().value());
        response.setStatus(e.getStatus().value());
        return new ExceptionResponse(response.getStatus(), message);
    }
}
