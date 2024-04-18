package dev.abarmin.common.upload.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.nio.file.Path;

@Data
@Validated
@ConfigurationProperties(prefix = "smart-collections.file-upload")
public class FileUploadConfiguration {
    @NotNull
    private Path storage;
}
