package io.github.ones1kk.authenticationtemplate.repository;

import io.github.ones1kk.authenticationtemplate.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Set<Message> findMessagesSetBy();

}
