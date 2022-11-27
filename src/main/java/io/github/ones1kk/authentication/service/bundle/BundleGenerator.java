package io.github.ones1kk.authentication.service.bundle;

import java.util.Locale;
import java.util.function.Function;

public interface BundleGenerator<R, T> {

    default R addDefault(Locale locale, Function<T, String> key, Function<T, String> value) {
        return addLocale(Locale.ROOT, key, value);
    }

    R addLocale(Locale locale, Function<T, String> key, Function<T, String> value);
}
