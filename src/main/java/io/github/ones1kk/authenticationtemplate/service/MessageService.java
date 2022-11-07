package io.github.ones1kk.authenticationtemplate.service;

import io.github.ones1kk.authenticationtemplate.domain.Message;
import io.github.ones1kk.authenticationtemplate.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Set<Message> findMessagesSetBy() {
        return messageRepository.findMessagesSetBy();
    }
}
