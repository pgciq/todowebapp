-- TodoWebApp H2 Database Schema
-- Development database schema for H2 
-- Based on MySQL schema but adapted for H2 syntax
--
-- IDEMPOTENT SCRIPT: This script can be run multiple times safely.
-- All INSERT statements use conditional logic to prevent duplicate data.
-- Tables are created with "IF NOT EXISTS" and data insertion uses 
-- "WHERE NOT EXISTS" conditions to avoid constraint violations.

-- ============================================================================
-- LOOKUP TABLES (Reference Data) - Create and populate first
-- ============================================================================

-- Account Statuses Lookup Table
CREATE TABLE IF NOT EXISTS account_statuses (
    status_id INT PRIMARY KEY AUTO_INCREMENT,
    status VARCHAR(32) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert account statuses first (only if not exist)
INSERT INTO account_statuses (status, description) 
SELECT 'ACTIVE', 'Account is active and can log in' WHERE NOT EXISTS (SELECT 1 FROM account_statuses WHERE status = 'ACTIVE');
INSERT INTO account_statuses (status, description) 
SELECT 'INACTIVE', 'Account is temporarily disabled' WHERE NOT EXISTS (SELECT 1 FROM account_statuses WHERE status = 'INACTIVE');
INSERT INTO account_statuses (status, description) 
SELECT 'SUSPENDED', 'Account is suspended due to violations' WHERE NOT EXISTS (SELECT 1 FROM account_statuses WHERE status = 'SUSPENDED');
INSERT INTO account_statuses (status, description) 
SELECT 'PENDING', 'Account is pending activation' WHERE NOT EXISTS (SELECT 1 FROM account_statuses WHERE status = 'PENDING');

-- Task Priorities Lookup Table
CREATE TABLE IF NOT EXISTS task_priorities (
    priority_id INT PRIMARY KEY AUTO_INCREMENT,
    priority VARCHAR(32) NOT NULL UNIQUE,
    description VARCHAR(255),
    sort_order INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert task priorities (only if not exist)
INSERT INTO task_priorities (priority, description, sort_order) 
SELECT 'LOW', 'Low priority task', 1 WHERE NOT EXISTS (SELECT 1 FROM task_priorities WHERE priority = 'LOW');
INSERT INTO task_priorities (priority, description, sort_order) 
SELECT 'MEDIUM', 'Medium priority task', 2 WHERE NOT EXISTS (SELECT 1 FROM task_priorities WHERE priority = 'MEDIUM');
INSERT INTO task_priorities (priority, description, sort_order) 
SELECT 'HIGH', 'High priority task', 3 WHERE NOT EXISTS (SELECT 1 FROM task_priorities WHERE priority = 'HIGH');
INSERT INTO task_priorities (priority, description, sort_order) 
SELECT 'CRITICAL', 'Critical priority task', 4 WHERE NOT EXISTS (SELECT 1 FROM task_priorities WHERE priority = 'CRITICAL');

-- Task Statuses Lookup Table
CREATE TABLE IF NOT EXISTS task_statuses (
    status_id INT PRIMARY KEY AUTO_INCREMENT,
    status VARCHAR(32) NOT NULL UNIQUE,
    description VARCHAR(255),
    sort_order INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert task statuses (only if not exist)
INSERT INTO task_statuses (status, description, sort_order) 
SELECT 'PENDING', 'Task is pending and not started', 1 WHERE NOT EXISTS (SELECT 1 FROM task_statuses WHERE status = 'PENDING');
INSERT INTO task_statuses (status, description, sort_order) 
SELECT 'IN_PROGRESS', 'Task is currently being worked on', 2 WHERE NOT EXISTS (SELECT 1 FROM task_statuses WHERE status = 'IN_PROGRESS');
INSERT INTO task_statuses (status, description, sort_order) 
SELECT 'COMPLETED', 'Task has been completed', 3 WHERE NOT EXISTS (SELECT 1 FROM task_statuses WHERE status = 'COMPLETED');
INSERT INTO task_statuses (status, description, sort_order) 
SELECT 'CANCELLED', 'Task has been cancelled', 4 WHERE NOT EXISTS (SELECT 1 FROM task_statuses WHERE status = 'CANCELLED');

-- ============================================================================
-- MAIN TABLES (Now that lookup tables exist)
-- ============================================================================

-- Accounts Table
CREATE TABLE IF NOT EXISTS accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(32) NOT NULL UNIQUE,
    first_name VARCHAR(32) NOT NULL,
    last_name VARCHAR(32) NOT NULL,
    password VARCHAR(255) DEFAULT 'password123',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status_id INT DEFAULT 1 NOT NULL,
    FOREIGN KEY (status_id) REFERENCES account_statuses(status_id)
);

-- Tasks Table
CREATE TABLE IF NOT EXISTS tasks (
    task_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    details TEXT NOT NULL,
    deadline TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status_id INT DEFAULT 1 NOT NULL,
    priority_id INT DEFAULT 2 NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE,
    FOREIGN KEY (status_id) REFERENCES task_statuses(status_id),
    FOREIGN KEY (priority_id) REFERENCES task_priorities(priority_id)
);

-- Account Sessions Table
CREATE TABLE IF NOT EXISTS account_sessions (
    session_id VARCHAR(255) PRIMARY KEY,
    account_id INT NOT NULL,
    session_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_accessed TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    session_end TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);

-- ============================================================================
-- DEFAULT DATA
-- ============================================================================

-- Default admin account (only if not exist)
INSERT INTO accounts (username, first_name, last_name, password, status_id) 
SELECT 'admin', 'System', 'Administrator', 'admin123', 1 WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE username = 'admin');

-- Sample user account (only if not exist)
INSERT INTO accounts (username, first_name, last_name, password, status_id) 
SELECT 'user', 'Test', 'User', 'user123', 1 WHERE NOT EXISTS (SELECT 1 FROM accounts WHERE username = 'user');

-- Sample tasks for demonstration (only if accounts exist and no tasks exist yet)
INSERT INTO tasks (account_id, details, deadline, status_id, priority_id) 
SELECT 1, 'Review application security settings', DATEADD('DAY', 7, CURRENT_TIMESTAMP), 1, 3 
WHERE EXISTS (SELECT 1 FROM accounts WHERE account_id = 1) 
AND NOT EXISTS (SELECT 1 FROM tasks WHERE account_id = 1 AND details = 'Review application security settings');

INSERT INTO tasks (account_id, details, deadline, status_id, priority_id) 
SELECT 1, 'Update database documentation', DATEADD('DAY', 14, CURRENT_TIMESTAMP), 1, 2 
WHERE EXISTS (SELECT 1 FROM accounts WHERE account_id = 1) 
AND NOT EXISTS (SELECT 1 FROM tasks WHERE account_id = 1 AND details = 'Update database documentation');

INSERT INTO tasks (account_id, details, deadline, status_id, priority_id) 
SELECT 2, 'Complete user profile setup', DATEADD('DAY', 3, CURRENT_TIMESTAMP), 1, 2 
WHERE EXISTS (SELECT 1 FROM accounts WHERE account_id = 2) 
AND NOT EXISTS (SELECT 1 FROM tasks WHERE account_id = 2 AND details = 'Complete user profile setup');

INSERT INTO tasks (account_id, details, deadline, status_id, priority_id) 
SELECT 2, 'Test new features', DATEADD('DAY', 5, CURRENT_TIMESTAMP), 2, 1 
WHERE EXISTS (SELECT 1 FROM accounts WHERE account_id = 2) 
AND NOT EXISTS (SELECT 1 FROM tasks WHERE account_id = 2 AND details = 'Test new features');
