package dev.abarmin.common.upload.service;

import dev.abarmin.common.security.domain.UserInfo;
import dev.abarmin.common.upload.config.FileUploadConfiguration;
import dev.abarmin.common.upload.entity.FileEntity;
import dev.abarmin.common.upload.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final FileUploadConfiguration configuration;
    private final FileRepository repository;

    @SneakyThrows
    public FileEntity upload(MultipartFile multipartFile, UserInfo owner) {
        String targetFilename = generateTargetFilename(multipartFile);
        Path target = configuration.getStorage().resolve(targetFilename);
        try (InputStream sourceStream = multipartFile.getInputStream();
             OutputStream targetStream = Files.newOutputStream(target, StandardOpenOption.CREATE_NEW)) {
            IOUtils.copy(sourceStream, targetStream);
        }

        return repository.save(FileEntity.builder()
                .filename(targetFilename)
                .userId(AggregateReference.to(owner.getId()))
                .build());
    }

    private String generateTargetFilename(MultipartFile multipartFile) {
        String filename = multipartFile.getOriginalFilename();
        String baseName = FilenameUtils.getBaseName(filename);
        String extension = FilenameUtils.getExtension(filename);
        int index = 0;
        Path candidate = configuration.getStorage().resolve(filename);
        while (Files.exists(candidate)) {
            filename = String.format("%s-%d.%s", baseName, index++, extension);
            candidate = configuration.getStorage().resolve(filename);
        }
        return filename;
    }
}
