package dev.abarmin.smart.collections;

import dev.abarmin.common.security.config.CommonSecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Import(CommonSecurityAutoConfiguration.class)
@SpringBootApplication
@EnableJdbcRepositories("dev.abarmin.smart.collections.repository")
public class SmartCollectionsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartCollectionsApplication.class, args);
    }
}
