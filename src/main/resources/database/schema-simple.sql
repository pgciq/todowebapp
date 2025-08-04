-- Simplified TodoWebApp Database Schema
-- Basic version for quick setup

CREATE DATABASE IF NOT EXISTS todo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE todo;

-- Account Statuses
CREATE TABLE account_statuses (
    status_id INT PRIMARY KEY AUTO_INCREMENT,
    status VARCHAR(32) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- Task Priorities
CREATE TABLE task_priorities (
    priority_id INT PRIMARY KEY AUTO_INCREMENT,
    priority VARCHAR(32) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- Task Statuses
CREATE TABLE task_statuses (
    status_id INT PRIMARY KEY AUTO_INCREMENT,
    status VARCHAR(32) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- Accounts
CREATE TABLE accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(32) NOT NULL UNIQUE,
    first_name VARCHAR(32) NOT NULL,
    last_name VARCHAR(32) NOT NULL,
    password VARCHAR(32) DEFAULT 'changeit',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    status_id INT NOT NULL DEFAULT 1,
    FOREIGN KEY (status_id) REFERENCES account_statuses(status_id)
) ENGINE=InnoDB;

-- Account Sessions
CREATE TABLE account_sessions (
    session_id VARCHAR(100) PRIMARY KEY,
    account_id INT NOT NULL,
    session_created DATETIME DEFAULT CURRENT_TIMESTAMP,
    session_end DATETIME NULL,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tasks
CREATE TABLE tasks (
    task_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    details VARCHAR(256) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    deadline DATETIME NULL,
    last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status_id INT NOT NULL DEFAULT 1,
    priority_id INT NOT NULL DEFAULT 1,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE,
    FOREIGN KEY (status_id) REFERENCES task_statuses(status_id),
    FOREIGN KEY (priority_id) REFERENCES task_priorities(priority_id)
) ENGINE=InnoDB;

-- Basic data
INSERT INTO account_statuses (status_id, status) VALUES
(1, 'ACTIVE'), (2, 'INACTIVE'), (3, 'SUSPENDED');

INSERT INTO task_priorities (priority_id, priority) VALUES
(1, 'LOW'), (2, 'MEDIUM'), (3, 'HIGH'), (4, 'URGENT');

INSERT INTO task_statuses (status_id, status) VALUES
(1, 'PENDING'), (2, 'IN_PROGRESS'), (3, 'COMPLETED'), (4, 'CANCELLED');

-- Admin user
INSERT INTO accounts (account_id, username, first_name, last_name, password, status_id) VALUES
(1, 'admin', 'System', 'Administrator', 'admin123', 1);
