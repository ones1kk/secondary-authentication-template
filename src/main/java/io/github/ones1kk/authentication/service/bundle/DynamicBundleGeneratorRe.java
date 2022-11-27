package io.github.ones1kk.authentication.service.bundle;

import io.github.ones1kk.authentication.service.bundle.constant.BundleName;
import io.github.ones1kk.authentication.service.bundle.packaging.PackagingFactory;
import io.github.ones1kk.authentication.service.bundle.packaging.PackagingHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static java.util.Collections.emptyList;

@Slf4j
@RequiredArgsConstructor
public class DynamicBundleGeneratorRe<T> {

    private static final String DYNAMIC_RESOURCE_DIRECTORY_NAME = "bundles";

    private final BundleName bundleName;

    private final Set<T> set;

    private PackagingFactory<T> packagingFactory;

    private final Map<Locale, Function<T, String>[]> converterMap;

    public List<File> make() throws URISyntaxException, IOException {
        List<File> files = emptyList();
        if (this.converterMap.isEmpty()) return files;

        PackagingHandler<T> handler = packagingFactory.getHandler();
        return handler.createProperty();
    }

}
