package io.github.ones1kk.authentication.service;

import io.github.ones1kk.authentication.domain.UserToken;
import io.github.ones1kk.authentication.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserTokenService {

    private final UserTokenRepository userTokenRepository;

    public UserToken findByUserId(Long userId) {
        return userTokenRepository.findByUserId(userId);
    }

    @Transactional
    public UserToken save(UserToken userToken) {
        return userTokenRepository.save(userToken);
    }
}
