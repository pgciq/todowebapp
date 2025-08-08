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
package io.github.faimoh.todowebapp.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;
import java.util.Properties;

/**
 * Database Configuration with JPA Support
 * Replaces traditional context.xml JNDI DataSource configuration
 * Supports multiple profiles: dev (H2), prod (MySQL)
 * Now includes Spring Data JPA configuration
 * 
 * @author Faisal Ahmed Pasha Mohammed https://github.com/faimoh
 */
@Configuration
@EnableJpaRepositories(basePackages = "io.github.faimoh.todowebapp.repository")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@PropertySource(value = "classpath:application-${spring.profiles.active:dev}.properties", 
                ignoreResourceNotFound = true)
public class DatabaseConfig {
    
    // Database connection settings (externalized to properties)
    @Value("${database.driver-class-name}")
    private String driverClassName;
    
    @Value("${database.url}")
    private String url;
    
    @Value("${database.username}")
    private String username;
    
    @Value("${database.password}")
    private String password;
    
    // Common pool settings (can be externalized to properties)
    @Value("${database.pool.max-total:20}")
    private int maxTotal;
    
    @Value("${database.pool.max-idle:30}")
    private int maxIdle;
    
    @Value("${database.pool.max-wait:10000}")
    private int maxWait;
    
    @Value("${database.pool.validation-query:SELECT 1}")
    private String validationQuery;
    
    @Value("${database.pool.test-on-borrow:true}")
    private boolean testOnBorrow;
    
    @Value("${database.pool.test-while-idle:true}")
    private boolean testWhileIdle;
    
    @Value("${database.pool.time-between-eviction-runs:300000}")
    private long timeBetweenEvictionRuns;
    
    @Value("${database.pool.min-evictable-idle-time:600000}")
    private long minEvictableIdleTime;
    
    @Value("${database.pool.log-abandoned:true}")
    private boolean logAbandoned;
    
    @Value("${database.pool.remove-abandoned-on-borrow:true}")
    private boolean removeAbandonedOnBorrow;
    
    @Value("${database.pool.remove-abandoned-timeout:60}")
    private int removeAbandonedTimeout;
    
    /**
     * Configure DataSource bean using externalized properties
     * Supports both H2 (dev) and MySQL (prod) based on active profile
     */
    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        
        // Basic connection settings from properties
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        
        // Apply common pool settings
        configureConnectionPool(dataSource);
        
        System.out.println("Configured DataSource:");
        System.out.println("  - Driver: " + driverClassName);
        System.out.println("  - URL: " + url);
        System.out.println("  - Username: " + username);
        
        return dataSource;
    }
    
    /**
     * Configure common connection pool settings
     * Uses externalized properties for configuration
     */
    private void configureConnectionPool(BasicDataSource dataSource) {
        // Connection pool settings
        dataSource.setMaxTotal(maxTotal);
        dataSource.setMaxIdle(maxIdle);
        dataSource.setMaxWaitMillis(maxWait);
        
        // Connection validation settings
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRuns);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTime);
        
        // Abandoned connection settings
        dataSource.setLogAbandoned(logAbandoned);
        dataSource.setRemoveAbandonedOnBorrow(removeAbandonedOnBorrow);
        dataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
    }
    
    /**
     * Configure JPA EntityManagerFactory
     * Uses Hibernate as JPA provider
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource());
        factory.setPackagesToScan("io.github.faimoh.todowebapp.model");
        
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factory.setJpaVendorAdapter(vendorAdapter);
        
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", getDialectForActiveProfile());
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        jpaProperties.put("hibernate.show_sql", "false");
        jpaProperties.put("hibernate.format_sql", "true");
        factory.setJpaProperties(jpaProperties);
        
        return factory;
    }
    
    /**
     * Configure JPA Transaction Manager
     */
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
    
    /**
     * Get Hibernate dialect based on active profile
     */
    private String getDialectForActiveProfile() {
        // Default to H2 dialect, override for MySQL in production
        if (driverClassName.contains("mysql")) {
            return "org.hibernate.dialect.MySQLDialect";
        } else {
            return "org.hibernate.dialect.H2Dialect";
        }
    }
}
