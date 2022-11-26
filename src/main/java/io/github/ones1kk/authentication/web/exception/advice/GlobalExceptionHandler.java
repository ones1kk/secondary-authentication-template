package io.github.ones1kk.authentication.web.exception.advice;

import io.github.ones1kk.authentication.web.exception.MessageSupport;
import io.github.ones1kk.authentication.web.exception.model.GlobalResponseModel;
import io.github.ones1kk.authentication.web.exception.model.GlobalException;
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

    @ExceptionHandler(GlobalException.class)
    GlobalResponseModel handle(GlobalException e, HttpServletResponse response) {
        String message = messageSupport.get(e.getCode(), e.getArgs());
        response.setStatus(e.getStatus().value());
        return new GlobalResponseModel(response.getStatus(), message);
    }
}
