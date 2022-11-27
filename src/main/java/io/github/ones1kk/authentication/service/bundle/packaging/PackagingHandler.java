package io.github.ones1kk.authentication.service.bundle.packaging;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface PackagingHandler<T> {

    boolean isSupport() throws URISyntaxException, IOException;

    List<File> createProperty() throws URISyntaxException, IOException;
}
