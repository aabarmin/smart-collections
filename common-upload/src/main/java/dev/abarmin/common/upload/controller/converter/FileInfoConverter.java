package dev.abarmin.common.upload.controller.converter;

import dev.abarmin.common.upload.controller.model.FileInfo;
import dev.abarmin.common.upload.entity.FileEntity;
import org.springframework.stereotype.Component;

@Component
public class FileInfoConverter {
    public FileInfo convert(FileEntity entity) {
        return FileInfo.builder()
                .id(entity.getId())
                .filename(entity.getFilename())
                .build();
    }
}
