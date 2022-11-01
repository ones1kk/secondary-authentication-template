package io.github.ones1kk.authenticationtemplate;

import io.github.ones1kk.authenticationtemplate.domain.User;
import io.github.ones1kk.authenticationtemplate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

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

        public void dbInit() {
            User user = new User("ones1kk", "$2a$10$nwOTfZvyYwFlIDbrO/496eYGf3VaLup0htWeX/UH7IweIN/tsc5dO");
            userRepository.save(user);
        }

    }
}
