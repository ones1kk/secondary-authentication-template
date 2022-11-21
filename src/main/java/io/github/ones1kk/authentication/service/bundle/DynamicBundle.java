package io.github.ones1kk.authentication.service.bundle;

import io.github.ones1kk.authentication.service.bundle.constant.BundleName;

import java.util.Set;

public class DynamicBundle<T> {

    private DynamicBundle() {
    }

    public static <T> DynamicBundleBuilder<T> builder(BundleName bundleName, Set<T> set) {
        return new DynamicBundleBuilder<>(bundleName.getBundleName(), set);
    }
}
