package io.github.ones1kk.authentication.service.bundle;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class DynamicBundleBuilder<T> {

    private final String bundleName;
    private final Set<T> set;
    private final Map<Locale, Function<T, String>[]> converterMap = new HashMap<>();

    public DynamicBundleBuilder<T> addLocale(Locale locale, Function<T, String> keyFunc, Function<T, String> valFunc) {
        this.converterMap.put(locale, new Function[]{keyFunc, valFunc});
        return this;
    }

    public DynamicBundleBuilder<T> addDefault(Function<T, String> keyFunc, Function<T, String> valFunc) {
        return addLocale(Locale.ROOT, keyFunc, valFunc);
    }

    public DynamicBundleGenerator<T> build() {
        return new DynamicBundleGenerator<>(bundleName, set, converterMap);
    }
}
