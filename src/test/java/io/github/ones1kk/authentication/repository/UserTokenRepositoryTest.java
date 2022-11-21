package io.github.ones1kk.authentication.repository;

import io.github.ones1kk.authentication.domain.User;
import io.github.ones1kk.authentication.domain.UserToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(readOnly = true)
class UserTokenRepositoryTest {

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void save() throws Exception {
        // given
        User user = new User("userA", "123456");
        userRepository.save(user);
        UserToken userToken = UserToken.builder()
                .user(user)
                .refreshToken(UUID.randomUUID().toString())
                .build();

        // when
        UserToken savedUserToken = userTokenRepository.save(userToken);

        // then
        assertThat(userToken).isEqualTo(savedUserToken);
    }

}