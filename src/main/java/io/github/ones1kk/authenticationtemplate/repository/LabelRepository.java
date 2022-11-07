package io.github.ones1kk.authenticationtemplate.repository;

import io.github.ones1kk.authenticationtemplate.domain.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

    Set<Label> findLabelSetBy();
}
