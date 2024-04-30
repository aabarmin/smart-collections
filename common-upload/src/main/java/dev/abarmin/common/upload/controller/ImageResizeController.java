package dev.abarmin.common.upload.controller;

import dev.abarmin.common.upload.config.FileUploadConfiguration;
import dev.abarmin.common.upload.entity.FileEntity;
import dev.abarmin.common.upload.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.imgscalr.Scalr;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Controller
@RequiredArgsConstructor
public class ImageResizeController {
    private final FileRepository fileRepository;
    private final FileUploadConfiguration configuration;

    @SneakyThrows
    @ResponseBody
    @GetMapping("/images/{id}/thumbnail")
    public Resource getThumbnail(@PathVariable("id") int fileId) {
        // check if file already exists
        Path targetPath = configuration.getStorage().resolve("thumbnails").resolve(fileId + ".jpg");
        if (Files.exists(targetPath)) {
            return new PathResource(targetPath);
        }
        // does not exist, create a thumbnail
        FileEntity fileEntry = fileRepository.findById(fileId).orElseThrow();
        Path sourceFilePath = configuration.getStorage().resolve(fileEntry.getFilename());
        try (InputStream sourceStream = Files.newInputStream(sourceFilePath);
             OutputStream targetStream = Files.newOutputStream(targetPath, StandardOpenOption.CREATE_NEW)) {
            BufferedImage sourceImage = ImageIO.read(sourceStream);
            BufferedImage resizedImage = Scalr.resize(sourceImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_EXACT, 300, 300);
            ImageIO.write(resizedImage, "jpg", targetStream);
        }
        return new PathResource(targetPath);
    }
}
