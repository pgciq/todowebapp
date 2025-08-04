-- TodoWebApp Database Schema
-- Generated from ToDoWebAppDataModel.png
-- MySQL Database Schema

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS todo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE todo;

-- ============================================================================
-- LOOKUP TABLES (Reference Data)
-- ============================================================================

-- Account Statuses Lookup Table
CREATE TABLE account_statuses (
    status_id INT PRIMARY KEY AUTO_INCREMENT,
    status VARCHAR(32) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Task Priorities Lookup Table
CREATE TABLE task_priorities (
    priority_id INT PRIMARY KEY AUTO_INCREMENT,
    priority VARCHAR(32) NOT NULL UNIQUE,
    description VARCHAR(255),
    sort_order INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Task Statuses Lookup Table
CREATE TABLE task_statuses (
    status_id INT PRIMARY KEY AUTO_INCREMENT,
    status VARCHAR(32) NOT NULL UNIQUE,
    description VARCHAR(255),
    sort_order INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- MAIN TABLES
-- ============================================================================

-- Accounts Table
CREATE TABLE accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(32) NOT NULL UNIQUE,
    first_name VARCHAR(32) NOT NULL,
    last_name VARCHAR(32) NOT NULL,
    password VARCHAR(32) NOT NULL, -- Note: In production, use longer hash fields
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status_id INT NOT NULL DEFAULT 1,
    
    -- Foreign key constraints
    CONSTRAINT fk_accounts_status 
        FOREIGN KEY (status_id) 
        REFERENCES account_statuses(status_id) 
        ON UPDATE CASCADE 
        ON DELETE RESTRICT,
    
    -- Indexes
    INDEX idx_accounts_username (username),
    INDEX idx_accounts_status (status_id),
    INDEX idx_accounts_name (last_name, first_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Account Sessions Table
CREATE TABLE account_sessions (
    session_id VARCHAR(100) PRIMARY KEY,
    account_id INT NOT NULL,
    session_created DATETIME DEFAULT CURRENT_TIMESTAMP,
    session_end DATETIME NULL,
    ip_address VARCHAR(45), -- Support IPv6
    user_agent TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    
    -- Foreign key constraints
    CONSTRAINT fk_sessions_account 
        FOREIGN KEY (account_id) 
        REFERENCES accounts(account_id) 
        ON UPDATE CASCADE 
        ON DELETE CASCADE,
    
    -- Indexes
    INDEX idx_sessions_account (account_id),
    INDEX idx_sessions_created (session_created),
    INDEX idx_sessions_active (is_active, session_created)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tasks Table
CREATE TABLE tasks (
    task_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    details VARCHAR(256) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    deadline DATETIME NULL,
    last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status_id INT NOT NULL DEFAULT 1,
    priority_id INT NOT NULL DEFAULT 1,
    
    -- Foreign key constraints
    CONSTRAINT fk_tasks_account 
        FOREIGN KEY (account_id) 
        REFERENCES accounts(account_id) 
        ON UPDATE CASCADE 
        ON DELETE CASCADE,
    
    CONSTRAINT fk_tasks_status 
        FOREIGN KEY (status_id) 
        REFERENCES task_statuses(status_id) 
        ON UPDATE CASCADE 
        ON DELETE RESTRICT,
    
    CONSTRAINT fk_tasks_priority 
        FOREIGN KEY (priority_id) 
        REFERENCES task_priorities(priority_id) 
        ON UPDATE CASCADE 
        ON DELETE RESTRICT,
    
    -- Indexes
    INDEX idx_tasks_account (account_id),
    INDEX idx_tasks_status (status_id),
    INDEX idx_tasks_priority (priority_id),
    INDEX idx_tasks_deadline (deadline),
    INDEX idx_tasks_created (created_at),
    INDEX idx_tasks_account_status (account_id, status_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- INITIAL DATA / SEED DATA
-- ============================================================================

-- Insert default account statuses
INSERT INTO account_statuses (status_id, status, description) VALUES
(1, 'ACTIVE', 'Active account'),
(2, 'INACTIVE', 'Inactive account'),
(3, 'SUSPENDED', 'Suspended account'),
(4, 'DELETED', 'Deleted account');

-- Insert default task priorities
INSERT INTO task_priorities (priority_id, priority, description, sort_order) VALUES
(1, 'LOW', 'Low priority task', 1),
(2, 'MEDIUM', 'Medium priority task', 2),
(3, 'HIGH', 'High priority task', 3),
(4, 'URGENT', 'Urgent priority task', 4);

-- Insert default task statuses
INSERT INTO task_statuses (status_id, status, description, sort_order) VALUES
(1, 'PENDING', 'Task is pending', 1),
(2, 'IN_PROGRESS', 'Task is in progress', 2),
(3, 'COMPLETED', 'Task is completed', 3),
(4, 'CANCELLED', 'Task is cancelled', 4);

-- Insert default admin account (password should be hashed in production)
INSERT INTO accounts (account_id, username, first_name, last_name, password, status_id) VALUES
(1, 'admin', 'System', 'Administrator', 'admin123', 1);

-- ============================================================================
-- VIEWS (Optional - for easier data access)
-- ============================================================================

-- View for accounts with status information
CREATE VIEW v_accounts_with_status AS
SELECT 
    a.account_id,
    a.username,
    a.first_name,
    a.last_name,
    a.created_at,
    a.updated_at,
    ast.status as account_status,
    ast.description as status_description
FROM accounts a
JOIN account_statuses ast ON a.status_id = ast.status_id;

-- View for tasks with full details
CREATE VIEW v_tasks_full AS
SELECT 
    t.task_id,
    t.account_id,
    a.username,
    CONCAT(a.first_name, ' ', a.last_name) as account_name,
    t.details,
    t.created_at,
    t.deadline,
    t.last_updated,
    ts.status as task_status,
    tp.priority as task_priority,
    tp.sort_order as priority_order
FROM tasks t
JOIN accounts a ON t.account_id = a.account_id
JOIN task_statuses ts ON t.status_id = ts.status_id
JOIN task_priorities tp ON t.priority_id = tp.priority_id;

-- View for active sessions
CREATE VIEW v_active_sessions AS
SELECT 
    s.session_id,
    s.account_id,
    a.username,
    CONCAT(a.first_name, ' ', a.last_name) as account_name,
    s.session_created,
    s.ip_address,
    s.user_agent
FROM account_sessions s
JOIN accounts a ON s.account_id = a.account_id
WHERE s.is_active = TRUE
AND s.session_end IS NULL;

-- ============================================================================
-- STORED PROCEDURES (Optional)
-- ============================================================================

DELIMITER //

-- Procedure to create a new task
CREATE PROCEDURE sp_create_task(
    IN p_account_id INT,
    IN p_details VARCHAR(256),
    IN p_deadline DATETIME,
    IN p_priority_id INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    INSERT INTO tasks (account_id, details, deadline, priority_id, status_id)
    VALUES (p_account_id, p_details, p_deadline, p_priority_id, 1);
    
    COMMIT;
    
    SELECT LAST_INSERT_ID() as task_id;
END //

-- Procedure to update task status
CREATE PROCEDURE sp_update_task_status(
    IN p_task_id INT,
    IN p_status_id INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    UPDATE tasks 
    SET status_id = p_status_id,
        last_updated = CURRENT_TIMESTAMP
    WHERE task_id = p_task_id;
    
    COMMIT;
END //

-- Procedure to create user session
CREATE PROCEDURE sp_create_session(
    IN p_session_id VARCHAR(100),
    IN p_account_id INT,
    IN p_ip_address VARCHAR(45),
    IN p_user_agent TEXT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- End any existing active sessions for this account
    UPDATE account_sessions 
    SET is_active = FALSE, 
        session_end = CURRENT_TIMESTAMP
    WHERE account_id = p_account_id 
    AND is_active = TRUE;
    
    -- Create new session
    INSERT INTO account_sessions (session_id, account_id, ip_address, user_agent)
    VALUES (p_session_id, p_account_id, p_ip_address, p_user_agent);
    
    COMMIT;
END //

DELIMITER ;

-- ============================================================================
-- TRIGGERS (Optional - for audit logging)
-- ============================================================================

-- Trigger to log task changes
CREATE TABLE task_audit_log (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    task_id INT,
    action VARCHAR(20),
    old_status_id INT,
    new_status_id INT,
    old_priority_id INT,
    new_priority_id INT,
    changed_by INT,
    changed_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

DELIMITER //

CREATE TRIGGER tr_tasks_update_audit
AFTER UPDATE ON tasks
FOR EACH ROW
BEGIN
    IF OLD.status_id != NEW.status_id OR OLD.priority_id != NEW.priority_id THEN
        INSERT INTO task_audit_log (
            task_id, action, old_status_id, new_status_id, 
            old_priority_id, new_priority_id, changed_by
        ) VALUES (
            NEW.task_id, 'UPDATE', OLD.status_id, NEW.status_id,
            OLD.priority_id, NEW.priority_id, NEW.account_id
        );
    END IF;
END //

DELIMITER ;

-- ============================================================================
-- PERFORMANCE OPTIMIZATION
-- ============================================================================

-- Additional indexes for common queries
CREATE INDEX idx_tasks_account_deadline ON tasks(account_id, deadline);
CREATE INDEX idx_tasks_status_priority ON tasks(status_id, priority_id);
CREATE INDEX idx_sessions_account_active ON account_sessions(account_id, is_active);

-- ============================================================================
-- CLEANUP AND MAINTENANCE PROCEDURES
-- ============================================================================

DELIMITER //

-- Procedure to cleanup old sessions
CREATE PROCEDURE sp_cleanup_old_sessions()
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- Mark sessions older than 30 days as inactive
    UPDATE account_sessions 
    SET is_active = FALSE, 
        session_end = CURRENT_TIMESTAMP
    WHERE session_created < DATE_SUB(NOW(), INTERVAL 30 DAY)
    AND is_active = TRUE;
    
    -- Delete sessions older than 90 days
    DELETE FROM account_sessions 
    WHERE session_created < DATE_SUB(NOW(), INTERVAL 90 DAY);
    
    COMMIT;
END //

DELIMITER ;

-- ============================================================================
-- GRANTS AND PERMISSIONS (Adjust according to your needs)
-- ============================================================================

-- Example: Create application user with limited permissions
-- CREATE USER 'todoapp'@'%' IDENTIFIED BY 'secure_password_here';
-- GRANT SELECT, INSERT, UPDATE, DELETE ON todo.* TO 'todoapp'@'%';
-- GRANT EXECUTE ON PROCEDURE todo.sp_create_task TO 'todoapp'@'%';
-- GRANT EXECUTE ON PROCEDURE todo.sp_update_task_status TO 'todoapp'@'%';
-- GRANT EXECUTE ON PROCEDURE todo.sp_create_session TO 'todoapp'@'%';
-- FLUSH PRIVILEGES;

-- ============================================================================
-- VERIFICATION QUERIES
-- ============================================================================

-- Verify table structure
SHOW TABLES;

-- Check foreign key constraints
SELECT 
    TABLE_NAME,
    COLUMN_NAME,
    CONSTRAINT_NAME,
    REFERENCED_TABLE_NAME,
    REFERENCED_COLUMN_NAME
FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE REFERENCED_TABLE_SCHEMA = 'todo'
AND REFERENCED_TABLE_NAME IS NOT NULL;

-- Check initial data
SELECT 'Account Statuses' as table_name, COUNT(*) as record_count FROM account_statuses
UNION ALL
SELECT 'Task Priorities', COUNT(*) FROM task_priorities
UNION ALL
SELECT 'Task Statuses', COUNT(*) FROM task_statuses
UNION ALL
SELECT 'Accounts', COUNT(*) FROM accounts;

-- ============================================================================
-- END OF SCHEMA
-- ============================================================================
