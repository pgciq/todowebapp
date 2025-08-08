package io.github.faimoh.todowebapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring Boot Web Configuration
 * Minimal configuration - Spring Boot auto-configures most web components
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    /**
     * Configure simple view controllers for static pages
     */
    @Override
    public void addViewControllers(@NonNull ViewControllerRegistry registry) {
        // Map root to login page
        registry.addViewController("/").setViewName("login");
        
        // Add view controllers for error pages if needed
        registry.addViewController("/error").setViewName("unknownAction");
    }
}
