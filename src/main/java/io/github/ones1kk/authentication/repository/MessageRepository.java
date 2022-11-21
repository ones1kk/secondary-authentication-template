package io.github.ones1kk.authentication.repository;

import io.github.ones1kk.authentication.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Set<Message> findMessagesSetBy();

}
