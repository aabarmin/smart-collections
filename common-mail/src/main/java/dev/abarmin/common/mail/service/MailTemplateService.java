package dev.abarmin.common.mail.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.common.mail.model.MailTemplate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MailTemplateService {
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public MailTemplate loadTemplate(String name) {
        ClassPathResource resource = new ClassPathResource("mail/" + name + ".json");
        if (!resource.exists()) {
            throw new IllegalArgumentException(String.format(
                    "Resource %s.json does not exist",
                    name
            ));
        }
        try (InputStream stream = resource.getInputStream()) {
            MailTemplate mailTemplate = objectMapper.readValue(stream, MailTemplate.class);
            mailTemplate.setTemplate(getContent(name));
            return mailTemplate;
        }
    }

    @SneakyThrows
    private String getContent(String name) {
        ClassPathResource resource = new ClassPathResource("mail/" + name + ".html");
        if (!resource.exists()) {
            throw new IllegalArgumentException(String.format(
                    "Resource %s.html does not exist",
                    name
            ));
        }
        try (InputStream stream = resource.getInputStream()) {
            return IOUtils.toString(stream, StandardCharsets.UTF_8);
        }
    }
}
