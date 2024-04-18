package dev.abarmin.common.upload.controller;

import dev.abarmin.common.security.domain.UserInfo;
import dev.abarmin.common.security.service.SessionService;
import dev.abarmin.common.upload.controller.converter.FileInfoConverter;
import dev.abarmin.common.upload.controller.model.FileInfo;
import dev.abarmin.common.upload.entity.FileEntity;
import dev.abarmin.common.upload.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileUploadController {
    private final FileUploadService fileUploadService;
    private final SessionService sessionService;
    private final FileInfoConverter converter;

    @PostMapping("/files/upload-single")
    public FileInfo uploadFile(@RequestParam("file") MultipartFile file) {
        UserInfo currentUser = sessionService.getCurrentUser().orElseThrow();
        FileEntity uploadedFile = fileUploadService.upload(file, currentUser);
        return converter.convert(uploadedFile);
    }

}
