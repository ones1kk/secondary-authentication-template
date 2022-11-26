package io.github.ones1kk.authentication.web.provider.hanlder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ones1kk.authentication.web.exception.MessageSupport;
import io.github.ones1kk.authentication.web.exception.model.GlobalResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public abstract class AbstractAuthenticationHandler {

    private final ObjectMapper objectMapper;

    private final MessageSupport messageSupport;

    protected void setMessage(HttpServletResponse response, String  message, HttpStatus status) throws IOException {
        GlobalResponseModel globalResponseModel = new GlobalResponseModel(status.value(), messageSupport.get(message));
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON.getType());
        response.getWriter()
                .write(writePretty(globalResponseModel));
    }

    private <V> String writePretty(V value) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(value);
    }

}
