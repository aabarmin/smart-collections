package dev.abarmin.common.upload.service;

import dev.abarmin.common.upload.config.FileUploadConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileUploadStorageChecker implements ApplicationRunner {
    private final FileUploadConfiguration configuration;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!Files.exists(configuration.getStorage())) {
            log.warn("Storage path {} does not exist", configuration.getStorage());
            Files.createDirectories(configuration.getStorage());
            log.info("Storage path {} created", configuration.getStorage());
        }
        // check if thumbnails folder exists
        Path thumbnailsPath = configuration.getStorage().resolve("thumbnails");
        if (!Files.exists(thumbnailsPath)) {
            Files.createDirectories(thumbnailsPath);
        }
    }
}
