package io.github.faimoh.todowebapp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Simple DataSource provider that can be used by legacy DAO factories
 * to get the Spring-managed DataSource without complex static initialization
 * 
 * @author Faisal Ahmed Pasha Mohammed https://github.com/faimoh
 */
@Component
public class DataSourceProvider {
    
    private static DataSource dataSource;
    
    @Autowired
    public void setDataSource(DataSource dataSource) {
        DataSourceProvider.dataSource = dataSource;
    }
    
    /**
     * Get the Spring-managed DataSource
     * @return DataSource instance
     */
    public static DataSource getDataSource() {
        return dataSource;
    }
    
    /**
     * Check if DataSource is available
     * @return true if DataSource is initialized
     */
    public static boolean isAvailable() {
        return dataSource != null;
    }
}
