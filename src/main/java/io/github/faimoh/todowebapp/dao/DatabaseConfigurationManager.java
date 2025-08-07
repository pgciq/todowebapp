/* 
 * Copyright (c) 2020, Faisal Ahmed Pasha Mohammed https://github.com/faimoh
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package io.github.faimoh.todowebapp.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import io.github.faimoh.todowebapp.config.DataSourceProvider;

/**
 * Database Configuration Manager
 * Automatically detects the configured database type and returns the appropriate DAO factory.
 * Supports H2 (development) and MySQL (production) databases.
 * 
 * @author Faisal Ahmed Pasha Mohammed https://github.com/faimoh
 */
public class DatabaseConfigurationManager {
    
    private static DatabaseType detectedDatabaseType = null;
    private static boolean initialized = false;
    
    public enum DatabaseType {
        H2("H2", "Development"),
        MYSQL("MySQL", "Production"),
        UNKNOWN("Unknown", "Unknown");
        
        private final String name;
        private final String environment;
        
        DatabaseType(String name, String environment) {
            this.name = name;
            this.environment = environment;
        }
        
        public String getName() { return name; }
        public String getEnvironment() { return environment; }
    }
    
    /**
     * Gets the appropriate DAO factory based on the configured database
     * @return DAOFactory instance for the detected database type
     */
    public static DAOFactory getDAOFactory() {
        DatabaseType dbType = detectDatabaseType();
        
        switch (dbType) {
            case H2:
                System.out.println("DatabaseConfigurationManager: Using H2 DAO Factory (Development)");
                return DAOFactory.getDAOFactory(DAOFactory.H2DataSource);
            case MYSQL:
                System.out.println("DatabaseConfigurationManager: Using MySQL DAO Factory (Production)");
                return DAOFactory.getDAOFactory(DAOFactory.MySQLDataSource);
            default:
                System.err.println("DatabaseConfigurationManager: Unknown database type, defaulting to MySQL");
                return DAOFactory.getDAOFactory(DAOFactory.MySQLDataSource);
        }
    }
    
    /**
     * Detects the database type by examining the Spring-managed DataSource
     * @return DatabaseType enum representing the detected database
     */
    public static DatabaseType detectDatabaseType() {
        if (initialized && detectedDatabaseType != null) {
            return detectedDatabaseType;
        }
        
        try {
            // Get DataSource from Spring DataSourceProvider
            if (!DataSourceProvider.isAvailable()) {
                System.err.println("DatabaseConfigurationManager: DataSource not available from DataSourceProvider");
                detectedDatabaseType = DatabaseType.UNKNOWN;
                initialized = true;
                return detectedDatabaseType;
            }
            
            DataSource dataSource = DataSourceProvider.getDataSource();
            
            if (dataSource != null) {
                try (Connection connection = dataSource.getConnection()) {
                    String databaseProductName = connection.getMetaData().getDatabaseProductName().toLowerCase();
                    
                    if (databaseProductName.contains("h2")) {
                        detectedDatabaseType = DatabaseType.H2;
                        System.out.println("DatabaseConfigurationManager: Detected H2 database (Development environment)");
                    } else if (databaseProductName.contains("mysql")) {
                        detectedDatabaseType = DatabaseType.MYSQL;
                        System.out.println("DatabaseConfigurationManager: Detected MySQL database (Production environment)");
                    } else {
                        detectedDatabaseType = DatabaseType.UNKNOWN;
                        System.err.println("DatabaseConfigurationManager: Unknown database type: " + databaseProductName);
                    }
                } catch (SQLException e) {
                    System.err.println("DatabaseConfigurationManager: Database connection failed - " + e.getMessage());
                    System.err.println("DatabaseConfigurationManager: This may indicate schema or configuration issues");
                    
                    // Try to detect database type from connection URL if metadata fails
                    String connectionUrl = dataSource.toString().toLowerCase();
                    if (connectionUrl.contains("h2")) {
                        System.out.println("DatabaseConfigurationManager: Fallback detection - H2 database found in connection URL");
                        detectedDatabaseType = DatabaseType.H2;
                    } else if (connectionUrl.contains("mysql")) {
                        System.out.println("DatabaseConfigurationManager: Fallback detection - MySQL database found in connection URL");
                        detectedDatabaseType = DatabaseType.MYSQL;
                    } else {
                        System.err.println("DatabaseConfigurationManager: Unable to determine database type from URL: " + connectionUrl);
                        detectedDatabaseType = DatabaseType.UNKNOWN;
                    }
                }
            } else {
                System.err.println("DatabaseConfigurationManager: DataSource not found in DataSourceProvider");
                detectedDatabaseType = DatabaseType.UNKNOWN;
            }
        } catch (Exception e) {
            System.err.println("DatabaseConfigurationManager: Error getting DataSource from DataSourceProvider: " + e.getMessage());
            detectedDatabaseType = DatabaseType.UNKNOWN;
        }
        
        initialized = true;
        return detectedDatabaseType != null ? detectedDatabaseType : DatabaseType.UNKNOWN;
    }
    
    /**
     * Gets the current database type without triggering detection
     * @return DatabaseType enum, or null if not yet detected
     */
    public static DatabaseType getCurrentDatabaseType() {
        return detectedDatabaseType;
    }
    
    /**
     * Forces re-detection of database type (useful after configuration changes)
     */
    public static void resetDetection() {
        detectedDatabaseType = null;
        initialized = false;
    }
    
    /**
     * Checks if we're currently running in development mode (H2)
     * @return true if H2 database is detected
     */
    public static boolean isDevelopmentMode() {
        return detectDatabaseType() == DatabaseType.H2;
    }
    
    /**
     * Checks if we're currently running in production mode (MySQL)
     * @return true if MySQL database is detected
     */
    public static boolean isProductionMode() {
        return detectDatabaseType() == DatabaseType.MYSQL;
    }
}
