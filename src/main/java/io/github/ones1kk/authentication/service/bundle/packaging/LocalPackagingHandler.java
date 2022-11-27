package io.github.ones1kk.authentication.service.bundle.packaging;

import io.github.ones1kk.authentication.service.bundle.constant.BundleName;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class LocalPackagingHandler<T> extends AbstractPackagingHandler<T> {

    private static final List<Path> segments = Stream.of("src", "main", "resources")
            .map(Paths::get)
            .collect(toList());

    public LocalPackagingHandler(BundleName bundleName, Set<T> set, Map<Locale, Function<T, String>[]> converterMap) {
        super(bundleName, set, converterMap);
    }

    @Override
    public boolean isSupport() throws URISyntaxException, IOException {
        Path resourcesDir = getResourcesPath();
        if (resourcesDir == null) return false;

        return Files.exists(resourcesDir);
    }

    @Override
    public List<File> createProperty() throws URISyntaxException, IOException {
        Path resourcesDir = getResourcesPath();
        if (resourcesDir == null) return Collections.emptyList();

        List<File> files = createProperty(resourcesDir);
        files.addAll(createProperty(resourcesDir));

        return files;
    }

    private Path getResourcesPath() throws URISyntaxException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(".");
        if (url == null || !url.getProtocol().equals("file")) return null;


        Path projectPath = getProjectPath(url);
        if (Files.notExists(projectPath)) return null;
        projectPath = projectPath.toRealPath();

        return segments.stream()
                .reduce(projectPath, Path::resolve);
    }

    // Maven: {project}/target/classes/
    // Gradle: {project}/build/libs/
    private Path getProjectPath(URL url) throws URISyntaxException {
        return new File(url.toURI())
                .getParentFile()
                .getParentFile()
                .getParentFile()
                .toPath();
    }
}
