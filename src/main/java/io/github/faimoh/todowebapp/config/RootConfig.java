package io.github.faimoh.todowebapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Root Application Context Configuration
 * This configuration is shared across the entire application
 * and contains beans that are not web-specific (services, repositories, etc.)
 */
@Configuration
@Import(DatabaseConfig.class)
@PropertySource({
    "classpath:application.properties",
    "classpath:application-${spring.profiles.active:dev}.properties"
})
@ComponentScan(basePackages = {
    "io.github.faimoh.todowebapp.model",
    "io.github.faimoh.todowebapp.service",
    "io.github.faimoh.todowebapp.repository",
    "io.github.faimoh.todowebapp.config"
})
public class RootConfig {
    
    // This configuration class can be extended to include:
    // - Database configuration
    // - Transaction management
    // - Security configuration
    // - Service layer beans
    // - Repository beans
}
