package io.github.ones1kk.authenticationtemplate.service;

import io.github.ones1kk.authenticationtemplate.domain.Label;
import io.github.ones1kk.authenticationtemplate.repository.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LabelService {

    private final LabelRepository labelRepository;

    public Set<Label> findLabelSetBy() {
        return labelRepository.findLabelSetBy();
    }
}
