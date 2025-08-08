package io.github.faimoh.todowebapp.config;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

/**
 * Debug test to check the actual database URL with Spring Boot
 */
@SpringBootTest
@ActiveProfiles("test")
public class DebugDatabaseTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void debugDatabaseUrl() throws Exception {
        assertNotNull(dataSource, "DataSource should be auto-configured");
        
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
            assertTrue(url.contains("jdbc:h2:mem"),
                "Expected URL to contain 'jdbc:h2:mem' but was: " + url);
        }
    }
}
