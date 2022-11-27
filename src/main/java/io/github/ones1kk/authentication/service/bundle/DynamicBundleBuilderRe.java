package io.github.ones1kk.authentication.service.bundle;

import io.github.ones1kk.authentication.service.bundle.constant.BundleName;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Function;

@RequiredArgsConstructor
public class DynamicBundleBuilderRe<T> implements BundleGenerator<DynamicBundleBuilderRe<T>, T> {

    private final BundleName bundleName;

    private final Set<T> set;

    private final Map<Locale, Function<T, String>[]> converterMap = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public DynamicBundleBuilderRe<T> addLocale(Locale locale, Function<T, String> key, Function<T, String> value) {
        this.converterMap.put(locale, new Function[]{key, value});
        return this;
    }

    public DynamicBundleGeneratorRe<T> build() {
        return new DynamicBundleGeneratorRe<>(bundleName, set, converterMap);
    }
}
