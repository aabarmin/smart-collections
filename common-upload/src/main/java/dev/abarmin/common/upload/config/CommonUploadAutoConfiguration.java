package dev.abarmin.common.upload.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@AutoConfiguration
@ComponentScan("dev.abarmin.common.upload")
@EnableJdbcRepositories("dev.abarmin.common.upload.repository")
public class CommonUploadAutoConfiguration {
}
