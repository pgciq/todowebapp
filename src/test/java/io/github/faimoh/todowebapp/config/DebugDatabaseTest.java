package io.github.faimoh.todowebapp.config;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

import static org.junit.Assert.*;

/**
 * Debug test to check the actual database URL
 */
public class DebugDatabaseTest {

    @Test
    public void debugDatabaseUrl() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment env = context.getEnvironment();
        env.setActiveProfiles("dev");
        
        context.register(DatabaseConfig.class);
        context.refresh();
        
        try {
            DataSource dataSource = context.getBean(DataSource.class);
            try (Connection connection = dataSource.getConnection()) {
                DatabaseMetaData metaData = connection.getMetaData();
                String url = metaData.getURL();
                
                System.out.println("=== DEBUG DATABASE INFO ===");
                System.out.println("Actual Database URL: " + url);
                System.out.println("Database Product: " + metaData.getDatabaseProductName());
                System.out.println("Driver: " + metaData.getDriverName());
                System.out.println("=========================");
                
                // Use assertion to show the URL in case of failure
                if (url.contains("jdbc:h2:mem")) {
                    System.out.println("SUCCESS: Using in-memory H2");
                } else if (url.contains("jdbc:h2:file")) {
                    System.out.println("WARNING: Still using file-based H2");
                } else {
                    System.out.println("UNKNOWN: Different database type");
                }
                
                // Fail with detailed information
                assertTrue("Expected URL to contain 'jdbc:h2:mem' but was: " + url, 
                    url.contains("jdbc:h2:mem"));
            }
        } finally {
            context.close();
        }
    }
}
