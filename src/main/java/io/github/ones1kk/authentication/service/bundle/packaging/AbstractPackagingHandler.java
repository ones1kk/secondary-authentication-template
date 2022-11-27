package io.github.ones1kk.authentication.service.bundle.packaging;

import io.github.ones1kk.authentication.service.bundle.constant.BundleName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractPackagingHandler<T> implements PackagingHandler<T> {
    protected static final String DYNAMIC_RESOURCE_DIRECTORY_NAME = "bundles";

    private final BundleName bundleName;

    private final Set<T> set;
    private final Map<Locale, Function<T, String>[]> converterMap;

    public List<File> createProperty(Path resourcesDir) throws IOException {
        Path path = resourcesDir.resolve(DYNAMIC_RESOURCE_DIRECTORY_NAME)
                .resolve(this.bundleName.getBundleName());

        // clears all files in the bundle directory.
        FileSystemUtils.deleteRecursively(path.toFile());
        Files.createDirectories(path);

        List<File> files = getFiles(path);
        logging(files, path);

        return files;
    }

    private List<File> getFiles(Path path) {
        List<File> files = new ArrayList<>();
        this.converterMap.forEach(((locale, functions) -> {
            Function<T, String> keyFunc = functions[0];
            Function<T, String> valFunc = functions[1];

            List<String> lines = this.set.stream()
                    .map(it -> keyFunc.apply(it) + '=' + valFunc.apply(it))
                    .sorted(String::compareTo)
                    .collect(toList());

            Path filePath = writeProperty(path, lines, locale);

            files.add(filePath.toFile());
        }));
        return files;
    }

    private Path writeProperty(Path path, Iterable<? extends CharSequence> lines, @Nullable Locale locale) {
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

    private void logging(List<File> files, Path path) {
        if (!files.isEmpty()) {
            List<String> fileNames = files.stream()
                    .map(File::getName)
                    .collect(toList());
            log.info("Generated properties: {} to '{}'", fileNames, path);
        }
    }
}
