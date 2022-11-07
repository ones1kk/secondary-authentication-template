package io.github.ones1kk.authenticationtemplate.service.bundle.constant;

import lombok.Getter;

@Getter
public enum DynamicBundle {
    LABELS, MESSAGES;

    private final String bundleName;

    DynamicBundle() {
        this.bundleName = name().toLowerCase()
                .replace('_', '-');
    }
}
