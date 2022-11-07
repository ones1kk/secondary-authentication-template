package io.github.ones1kk.authenticationtemplate;

import io.github.ones1kk.authenticationtemplate.domain.Label;
import io.github.ones1kk.authenticationtemplate.domain.Message;
import io.github.ones1kk.authenticationtemplate.domain.User;
import io.github.ones1kk.authenticationtemplate.repository.LabelRepository;
import io.github.ones1kk.authenticationtemplate.repository.MessageRepository;
import io.github.ones1kk.authenticationtemplate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class InitData {

    private final InitService service;

    @PostConstruct
    public void init() {
        service.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final UserRepository userRepository;

        private final LabelRepository labelRepository;

        private final MessageRepository messageRepository;

        public void dbInit() {
            User user = new User("ones1kk", "$2a$10$nwOTfZvyYwFlIDbrO/496eYGf3VaLup0htWeX/UH7IweIN/tsc5dO");
            userRepository.save(user);

            List<Label> labels = new ArrayList<>();
            List<Message> messages = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                labels.add(new Label("L" + i, "korean", "english"));
                messages.add(new Message("M" + i, "korean", "english"));
            }
            labelRepository.saveAll(labels);
            messageRepository.saveAll(messages);
        }
    }
}
