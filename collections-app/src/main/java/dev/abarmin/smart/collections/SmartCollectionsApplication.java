package dev.abarmin.smart.collections;

import dev.abarmin.common.security.config.CommonSecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(CommonSecurityAutoConfiguration.class)
@SpringBootApplication
public class SmartCollectionsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartCollectionsApplication.class, args);
    }
}
