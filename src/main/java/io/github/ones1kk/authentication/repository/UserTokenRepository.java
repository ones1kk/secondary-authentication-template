package io.github.ones1kk.authentication.repository;

import io.github.ones1kk.authentication.domain.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    UserToken findByUserId(Long userId);
}
