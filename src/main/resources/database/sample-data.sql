-- TodoWebApp Sample Data
-- Insert sample data for testing the application

USE todo;

-- Sample users (in addition to admin)
INSERT INTO accounts (username, first_name, last_name, password, status_id) VALUES
('john.doe', 'John', 'Doe', 'password123', 1),
('jane.smith', 'Jane', 'Smith', 'password123', 1),
('bob.wilson', 'Bob', 'Wilson', 'password123', 1),
('alice.brown', 'Alice', 'Brown', 'password123', 2);

-- Sample tasks for John Doe (account_id = 2)
INSERT INTO tasks (account_id, details, deadline, priority_id, status_id) VALUES
(2, 'Complete project documentation', '2025-08-15 17:00:00', 3, 1),
(2, 'Review code changes', '2025-08-10 12:00:00', 2, 2),
(2, 'Attend team meeting', '2025-08-05 10:00:00', 2, 3),
(2, 'Update user manual', '2025-08-20 16:00:00', 1, 1),
(2, 'Fix critical bug', '2025-08-06 09:00:00', 4, 2);

-- Sample tasks for Jane Smith (account_id = 3)
INSERT INTO tasks (account_id, details, deadline, priority_id, status_id) VALUES
(3, 'Design new UI mockups', '2025-08-12 14:00:00', 3, 1),
(3, 'Test new features', '2025-08-08 11:00:00', 2, 2),
(3, 'Prepare presentation', '2025-08-14 15:00:00', 3, 1),
(3, 'Database optimization', '2025-08-18 13:00:00', 2, 1);

-- Sample tasks for Bob Wilson (account_id = 4)
INSERT INTO tasks (account_id, details, deadline, priority_id, status_id) VALUES
(4, 'Setup development environment', '2025-08-07 16:00:00', 2, 3),
(4, 'Write unit tests', '2025-08-11 12:00:00', 2, 1),
(4, 'Deploy to staging', '2025-08-13 10:00:00', 3, 1);

-- Sample sessions (these would normally be created by the application)
INSERT INTO account_sessions (session_id, account_id, session_created, ip_address, user_agent) VALUES
('session_john_001', 2, NOW(), '192.168.1.10', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36'),
('session_jane_001', 3, DATE_SUB(NOW(), INTERVAL 2 HOUR), '192.168.1.11', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36'),
('session_bob_001', 4, DATE_SUB(NOW(), INTERVAL 1 DAY), '192.168.1.12', 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36');

-- Verify the sample data
SELECT 
    'Users' as category,
    COUNT(*) as count,
    GROUP_CONCAT(username ORDER BY account_id) as details
FROM accounts
WHERE account_id > 1

UNION ALL

SELECT 
    'Tasks' as category,
    COUNT(*) as count,
    CONCAT('Pending: ', SUM(CASE WHEN status_id = 1 THEN 1 ELSE 0 END),
           ', In Progress: ', SUM(CASE WHEN status_id = 2 THEN 1 ELSE 0 END),
           ', Completed: ', SUM(CASE WHEN status_id = 3 THEN 1 ELSE 0 END)) as details
FROM tasks

UNION ALL

SELECT 
    'Active Sessions' as category,
    COUNT(*) as count,
    'Current active sessions' as details
FROM account_sessions
WHERE is_active = TRUE;

-- Sample queries to test the schema
SELECT 'Sample Query Results' as section;

-- Show all tasks with user and status information
SELECT 
    t.task_id,
    CONCAT(a.first_name, ' ', a.last_name) as user_name,
    t.details,
    ts.status as task_status,
    tp.priority as priority,
    t.deadline,
    t.created_at
FROM tasks t
JOIN accounts a ON t.account_id = a.account_id
JOIN task_statuses ts ON t.status_id = ts.status_id
JOIN task_priorities tp ON t.priority_id = tp.priority_id
ORDER BY t.deadline ASC, tp.sort_order DESC;

-- Show task summary by user
SELECT 
    CONCAT(a.first_name, ' ', a.last_name) as user_name,
    COUNT(t.task_id) as total_tasks,
    SUM(CASE WHEN ts.status = 'PENDING' THEN 1 ELSE 0 END) as pending_tasks,
    SUM(CASE WHEN ts.status = 'IN_PROGRESS' THEN 1 ELSE 0 END) as in_progress_tasks,
    SUM(CASE WHEN ts.status = 'COMPLETED' THEN 1 ELSE 0 END) as completed_tasks
FROM accounts a
LEFT JOIN tasks t ON a.account_id = t.account_id
LEFT JOIN task_statuses ts ON t.status_id = ts.status_id
WHERE a.account_id > 1  -- Exclude admin account
GROUP BY a.account_id, a.first_name, a.last_name
ORDER BY total_tasks DESC;
