package io.github.ones1kk.authenticationtemplate.service;

import io.github.ones1kk.authenticationtemplate.domain.User;
import io.github.ones1kk.authenticationtemplate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("not found user"));
    }
}
