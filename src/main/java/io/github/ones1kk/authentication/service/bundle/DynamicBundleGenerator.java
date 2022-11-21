package io.github.ones1kk.authentication.service.bundle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Slf4j
public class DynamicBundleGenerator<T> {

    public static final String DYNAMIC_RESOURCE_DIRECTORY_NAME = "dynamic";

    private final String bundleName;

    private final Set<T> set;

    private final Map<Locale, Function<T, String>[]> converterMap;

    public List<File> make() throws URISyntaxException, IOException {
        List<File> files = Collections.emptyList();
        if (this.converterMap.isEmpty()) return files;

        for (PackagingType type : PackagingType.values()) {
            if (type.isSupported()) {
                type.createProperties(this);
                break;
            }
        }
        return files;
    }

    private List<File> createProperties(Path resourcesDir) throws IOException {
        Path path = resourcesDir.resolve(DYNAMIC_RESOURCE_DIRECTORY_NAME)
                .resolve(this.bundleName);

        // Clears all files in the bundle directory.
        FileSystemUtils.deleteRecursively(path.toFile());
        Files.createDirectories(path);

        List<File> files = new ArrayList<>();

        this.converterMap.forEach(((locale, functions) -> {
            Function<T, String> keyFunc = functions[0];
            Function<T, String> valFunc = functions[1];

            List<String> lines = this.set.stream()
                    .map(it -> keyFunc.apply(it) + '=' + valFunc.apply(it))
                    .sorted(String::compareTo)
                    .collect(toList());
            Path filePath = writeProperties(path, lines, locale);
            files.add(filePath.toFile());
        }));

        if (!files.isEmpty()) {
            List<String> fileNames = files.stream()
                    .map(File::getName)
                    .collect(toList());
            log.info("Generated properties: {} to '{}'", fileNames, path);
        }

        return files;
    }

    private static Path writeProperties(Path path, Iterable<? extends CharSequence> lines, @Nullable Locale locale) {
        try {
            if (!Files.isWritable(path)) throw new FileSystemException("Path is not writable: " + path);

            String dirName = path.getFileName().toString();
            String filename = locale == null || locale == Locale.ROOT ? dirName + ".properties"
                    : String.format("%s_%s.properties", dirName, locale);
            Path filePath = path.resolve(filename);

            Files.write(filePath, lines, StandardCharsets.UTF_8);

            return filePath;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private enum PackagingType {
        LOCAL {
            private final List<Path> segments = Stream.of("src", "main", "resources")
                    .map(Paths::get)
                    .collect(toList());

            @Override
            boolean isSupported() throws URISyntaxException, IOException {
                Path resourcesDir = getResourcesPath();
                if (resourcesDir == null) return false;

                return Files.exists(resourcesDir);
            }

            @Override
            <T> List<File> createProperties(DynamicBundleGenerator<T> generator) throws URISyntaxException, IOException {
                Path resourcesDir = getResourcesPath();
                if (resourcesDir == null) return Collections.emptyList();

                List<File> files = WAR.createProperties(generator);
                files.addAll(generator.createProperties(resourcesDir));

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
        },

        JAR {
            @Override
            boolean isSupported() {
                return false; // Not supported.
            }

            @Override
            <T> List<File> createProperties(DynamicBundleGenerator<T> generator) {
                return Collections.emptyList();
            }
        },

        WAR {
            @Override
            boolean isSupported() throws URISyntaxException, IOException {
                return getResourcesPath() != null && !LOCAL.isSupported();
            }

            @Override
            <T> List<File> createProperties(DynamicBundleGenerator<T> generator) throws URISyntaxException, IOException {
                Path resourcesDir = getResourcesPath();
                if (resourcesDir == null) return Collections.emptyList();

                return generator.createProperties(resourcesDir);
            }

            private Path getResourcesPath() throws URISyntaxException {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                URL url = classLoader.getResource(".");
                if (url == null || !url.getProtocol().equals("file")) return null;

                return new File(url.toURI()).toPath();
            }
        };

        abstract boolean isSupported() throws URISyntaxException, IOException;

        abstract <T> List<File> createProperties(DynamicBundleGenerator<T> generator) throws URISyntaxException, IOException;
    }

}
