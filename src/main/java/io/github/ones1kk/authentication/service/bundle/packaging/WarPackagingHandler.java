package io.github.ones1kk.authentication.service.bundle.packaging;

import io.github.ones1kk.authentication.service.bundle.constant.BundleName;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

public class WarPackagingHandler<T> extends AbstractPackagingHandler<T> {

    private final LocalPackagingHandler<T> local;

    public WarPackagingHandler(BundleName bundleName, Set<T> set, Map<Locale, Function<T, String>[]> converterMap, LocalPackagingHandler<T> local) {
        super(bundleName, set, converterMap);
        this.local = local;
    }

    @Override
    public boolean isSupport() throws URISyntaxException, IOException {
        return getResourcesPath() != null && !local.isSupport();
    }

    @Override
    public List<File> createProperty() throws URISyntaxException, IOException {
        Path resourcesDir = getResourcesPath();
        if (resourcesDir == null) return Collections.emptyList();

        return createProperty(resourcesDir);
    }

    private Path getResourcesPath() throws URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(".");
        if (url == null || !url.getProtocol().equals("file")) return null;

        return new File(url.toURI()).toPath();
    }
}
