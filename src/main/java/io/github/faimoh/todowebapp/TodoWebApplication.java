package io.github.faimoh.todowebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Spring Boot Main Application Class
 * This class serves as both:
 * 1. Entry point for embedded server (java -jar)
 * 2. WAR deployment initializer for external Tomcat
 */
@SpringBootApplication
public class TodoWebApplication extends SpringBootServletInitializer {

    /**
     * Main method for running as standalone application
     */
    public static void main(String[] args) {
        SpringApplication.run(TodoWebApplication.class, args);
    }

    /**
     * Configure for WAR deployment to external servlet container
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TodoWebApplication.class);
    }
}
