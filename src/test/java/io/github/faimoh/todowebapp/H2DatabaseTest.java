package io.github.faimoh.todowebapp;

import java.sql.*;

/**
 * Test class to verify H2 database setup and initialization
 */
public class H2DatabaseTest {
    
    public static void main(String[] args) {
        System.out.println("Testing H2 Database Setup...");
        
        // H2 database URL for file-based database
        String jdbcUrl = "jdbc:h2:./h2db/todo;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;INIT=RUNSCRIPT FROM 'src/main/resources/database/schema-h2.sql'";
        String username = "sa";
        String password = "";
        
        try {
            // Load H2 driver
            Class.forName("org.h2.Driver");
            System.out.println("✓ H2 Driver loaded successfully");
            
            // Connect to database
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("✓ Connected to H2 database");
            
            // Test database schema
            testDatabaseSchema(conn);
            
            // Test sample data
            testSampleData(conn);
            
            conn.close();
            System.out.println("✓ Database connection closed");
            System.out.println("\n✓ H2 Database test completed successfully!");
            
        } catch (Exception e) {
            System.err.println("✗ H2 Database test failed:");
            e.printStackTrace();
        }
    }
    
    private static void testDatabaseSchema(Connection conn) throws SQLException {
        System.out.println("\nTesting database schema...");
        
        DatabaseMetaData metaData = conn.getMetaData();
        
        // Check if tables exist
        String[] tables = {"account_statuses", "task_statuses", "accounts", "tasks", "account_sessions"};
        
        for (String table : tables) {
            ResultSet rs = metaData.getTables(null, null, table, null);
            if (rs.next()) {
                System.out.println("  ✓ Table " + table + " exists");
            } else {
                System.out.println("  ✗ Table " + table + " missing");
            }
            rs.close();
        }
    }
    
    private static void testSampleData(Connection conn) throws SQLException {
        System.out.println("\nTesting sample data...");
        
        // Test ACCOUNT_STATUS lookup data
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM account_statuses");
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int statusCount = rs.getInt(1);
        System.out.println("  ✓ account_statuses records: " + statusCount);
        rs.close();
        stmt.close();
        
        // Test TASK_STATUS lookup data  
        stmt = conn.prepareStatement("SELECT COUNT(*) FROM task_statuses");
        rs = stmt.executeQuery();
        rs.next();
        int taskStatusCount = rs.getInt(1);
        System.out.println("  ✓ task_statuses records: " + taskStatusCount);
        rs.close();
        stmt.close();
        
        // Test sample accounts
        stmt = conn.prepareStatement("SELECT COUNT(*) FROM accounts");
        rs = stmt.executeQuery();
        rs.next();
        int accountCount = rs.getInt(1);
        System.out.println("  ✓ accounts records: " + accountCount);
        rs.close();
        stmt.close();
        
        if (accountCount > 0) {
            // Show sample account details
            stmt = conn.prepareStatement("SELECT username, first_name, last_name FROM accounts LIMIT 1");
            rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("    Sample account: " + rs.getString("username") + 
                                 " (" + rs.getString("first_name") + " " + rs.getString("last_name") + ")");
            }
            rs.close();
            stmt.close();
        }
    }
}
