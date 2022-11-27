package io.github.ones1kk.authentication.service.bundle.packaging;

import io.github.ones1kk.authentication.service.bundle.constant.BundleName;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.function.Function;

public class JarPackagingHandler<T> extends AbstractPackagingHandler<T> {

    public JarPackagingHandler(BundleName bundleName, Set<T> set, Map<Locale, Function<T, String>[]> converterMap) {
        super(bundleName, set, converterMap);
    }

    @Override
    public boolean isSupport() throws URISyntaxException, IOException {
        // not supported.
        return false;
    }

    @Override
    public List<File> createProperty() throws URISyntaxException, IOException {
        return Collections.emptyList();
    }
}
