package io.github.faimoh.todowebapp.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import javax.sql.DataSource;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Simple test class to verify basic database configuration without SpringApplicationContextHolder
 */
public class SimpleDatabaseConfigTest {

    @Test
    public void testDataSourceDirectAccess() throws Exception {
        // Create application context with dev profile
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment env = context.getEnvironment();
        env.setActiveProfiles("dev");
        
        // Register only DatabaseConfig to avoid SpringApplicationContextHolder issues
        context.register(DatabaseConfig.class);
        context.refresh();
        
        try {
            DataSource dataSource = context.getBean(DataSource.class);
            assertNotNull("DataSource should be configured", dataSource);
            
            try (Connection connection = dataSource.getConnection()) {
                assertNotNull("Should be able to get a connection", connection);
                
                DatabaseMetaData metaData = connection.getMetaData();
                String databaseProductName = metaData.getDatabaseProductName();
                String url = metaData.getURL();
                
                // For dev profile, should be H2
                assertTrue("Should be using H2 database in dev profile, but was: " + databaseProductName,
                    databaseProductName.contains("H2"));
                assertTrue("URL should contain h2, but was: " + url,
                    url.contains("h2"));
                
                System.out.println("Simple DataSource Test Results:");
                System.out.println("Database Product: " + databaseProductName);
                System.out.println("Database URL: " + url);
                System.out.println("Driver: " + metaData.getDriverName());
            }
        } finally {
            context.close();
        }
    }
}
