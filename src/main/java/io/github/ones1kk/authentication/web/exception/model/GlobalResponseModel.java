package io.github.ones1kk.authentication.web.exception.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GlobalResponseModel {

    private final int status;

    private final String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime dateTime = LocalDateTime.now();

    public static GlobalResponseModel from(HttpStatus httpStatus) {
        return new GlobalResponseModel(httpStatus.value(), httpStatus.getReasonPhrase());
    }

}
