package io.github.faimoh.todowebapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Spring Boot Database Configuration
 * Minimal configuration - Spring Boot auto-configures DataSource and JPA
 */
@Configuration
public class DatabaseConfig {
    
    /**
     * Development profile configuration
     * Spring Boot auto-configures H2 database based on application-dev.properties
     */
    @Configuration
    @Profile("dev")
    static class DevelopmentConfig {
        // H2 database auto-configured by Spring Boot
        // See application-dev.properties for configuration
    }
    
    /**
     * Production profile configuration  
     * Spring Boot auto-configures MySQL database based on application-prod.properties
     */
    @Configuration
    @Profile("prod")
    static class ProductionConfig {
        // MySQL database auto-configured by Spring Boot
        // See application-prod.properties for configuration
    }
}
