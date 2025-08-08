package io.github.faimoh.todowebapp.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Spring Boot test for database configuration
 */
@SpringBootTest
@ActiveProfiles("test")
public class SimpleDatabaseConfigTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testDataSourceConfiguration() throws Exception {
        assertNotNull(dataSource, "DataSource should be auto-configured by Spring Boot");
        
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection, "Should be able to get a connection");
            
            DatabaseMetaData metaData = connection.getMetaData();
            String databaseProductName = metaData.getDatabaseProductName();
            String url = metaData.getURL();
            
            // For dev profile, should be H2 in-memory
            assertTrue(databaseProductName.contains("H2"),
                "Should be using H2 database in dev profile, but was: " + databaseProductName);
            assertTrue(url.contains("h2:mem"),
                "URL should contain h2:mem, but was: " + url);
            
            System.out.println("Spring Boot DataSource Test Results:");
            System.out.println("Database Product: " + databaseProductName);
            System.out.println("Database URL: " + url);
            System.out.println("Driver: " + metaData.getDriverName());
        }
    }
}
