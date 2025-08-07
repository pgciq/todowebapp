package io.github.faimoh.todowebapp.config;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.*;

/**
 * Test to verify that H2 database initialization is idempotent
 * (can be run multiple times without creating duplicate data)
 */
public class IdempotentDatabaseTest {

    @Test
    public void testIdempotentInitialization() throws Exception {
        // Create application context with dev profile multiple times
        // This should trigger database initialization multiple times
        
        int userCountAfterFirst = 0;
        int taskCountAfterFirst = 0;
        int userCountAfterSecond = 0;
        int taskCountAfterSecond = 0;
        
        // First initialization
        try (AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext()) {
            ConfigurableEnvironment env = context1.getEnvironment();
            env.setActiveProfiles("dev");
            context1.register(DatabaseConfig.class);
            context1.refresh();
            
            DataSource dataSource = context1.getBean(DataSource.class);
            try (Connection conn = dataSource.getConnection();
                 Statement stmt = conn.createStatement()) {
                
                // Count users after first initialization
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM accounts");
                if (rs.next()) {
                    userCountAfterFirst = rs.getInt(1);
                }
                rs.close();
                
                // Count tasks after first initialization
                rs = stmt.executeQuery("SELECT COUNT(*) FROM tasks");
                if (rs.next()) {
                    taskCountAfterFirst = rs.getInt(1);
                }
                rs.close();
                
                System.out.println("After first initialization:");
                System.out.println("  Users: " + userCountAfterFirst);
                System.out.println("  Tasks: " + taskCountAfterFirst);
            }
        }
        
        // Second initialization (should not add duplicate data)
        try (AnnotationConfigApplicationContext context2 = new AnnotationConfigApplicationContext()) {
            ConfigurableEnvironment env = context2.getEnvironment();
            env.setActiveProfiles("dev");
            context2.register(DatabaseConfig.class);
            context2.refresh();
            
            DataSource dataSource = context2.getBean(DataSource.class);
            try (Connection conn = dataSource.getConnection();
                 Statement stmt = conn.createStatement()) {
                
                // Count users after second initialization
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM accounts");
                if (rs.next()) {
                    userCountAfterSecond = rs.getInt(1);
                }
                rs.close();
                
                // Count tasks after second initialization
                rs = stmt.executeQuery("SELECT COUNT(*) FROM tasks");
                if (rs.next()) {
                    taskCountAfterSecond = rs.getInt(1);
                }
                rs.close();
                
                System.out.println("After second initialization:");
                System.out.println("  Users: " + userCountAfterSecond);
                System.out.println("  Tasks: " + taskCountAfterSecond);
            }
        }
        
        // Verify that counts are the same (no duplicates created)
        assertEquals("User count should be the same after multiple initializations", 
                    userCountAfterFirst, userCountAfterSecond);
        assertEquals("Task count should be the same after multiple initializations", 
                    taskCountAfterFirst, taskCountAfterSecond);
        
        // Verify we have the expected initial data
        assertTrue("Should have at least 2 users (admin and user)", userCountAfterFirst >= 2);
        assertTrue("Should have sample tasks", taskCountAfterFirst >= 4);
        
        System.out.println("✅ Idempotent initialization test passed!");
    }
}
