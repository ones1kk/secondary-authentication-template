package io.github.ones1kk.authentication.service;

import io.github.ones1kk.authentication.domain.Message;
import io.github.ones1kk.authentication.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;

    public Set<Message> findMessagesSetBy() {
        return messageRepository.findMessagesSetBy();
    }
}
