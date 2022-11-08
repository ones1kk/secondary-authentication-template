package io.github.ones1kk.authenticationtemplate.service.bundle.constant;

import lombok.Getter;

@Getter
public enum BundleName {
    LABELS, MESSAGES;

    private final String bundleName;

    BundleName() {
        this.bundleName = name().toLowerCase()
                .replace('_', '-');
    }
}
