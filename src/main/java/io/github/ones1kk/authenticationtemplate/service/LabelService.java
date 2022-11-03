package io.github.ones1kk.authenticationtemplate.service;

import io.github.ones1kk.authenticationtemplate.repository.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;
}
