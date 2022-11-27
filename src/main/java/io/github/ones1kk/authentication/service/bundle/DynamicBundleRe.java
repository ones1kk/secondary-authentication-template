package io.github.ones1kk.authentication.service.bundle;

import io.github.ones1kk.authentication.service.bundle.constant.BundleName;

import java.util.Set;

public class DynamicBundleRe {

    private DynamicBundleRe() {
    }

    public static <T> DynamicBundleBuilderRe<T> builder(BundleName bundleName, Set<T> set) {
        return new DynamicBundleBuilderRe<>(bundleName, set);
    }
}
