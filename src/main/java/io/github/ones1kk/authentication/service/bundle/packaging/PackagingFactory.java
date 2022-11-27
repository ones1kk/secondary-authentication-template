package io.github.ones1kk.authentication.service.bundle.packaging;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PackagingFactory<T> {
    private final List<PackagingHandler<T>> packagingAdapterHandlers;

    public PackagingHandler<T> getHandler() throws URISyntaxException, IOException {
        for (PackagingHandler<T> packagingAdapterHandler : packagingAdapterHandlers) {
            if (packagingAdapterHandler.isSupport()) {
                return packagingAdapterHandler;
            }
        }
        return null;
    }

}
