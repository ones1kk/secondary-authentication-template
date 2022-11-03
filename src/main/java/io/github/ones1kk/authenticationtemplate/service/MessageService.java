package io.github.ones1kk.authenticationtemplate.service;

import io.github.ones1kk.authenticationtemplate.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
}
