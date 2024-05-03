package dev.abarmin.common.mail.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@AutoConfiguration
@ComponentScan("dev.abarmin.common.mail")
@EnableJdbcRepositories("dev.abarmin.common.mail.repository")
public class CommonMailAutoConfiguration {

}
