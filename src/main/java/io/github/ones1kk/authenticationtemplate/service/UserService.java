package io.github.ones1kk.authenticationtemplate.service;

import io.github.ones1kk.authenticationtemplate.domain.User;
import io.github.ones1kk.authenticationtemplate.repository.UserRepository;
import io.github.ones1kk.authenticationtemplate.web.exception.model.CodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new CodeException("not found user"));
    }
}
