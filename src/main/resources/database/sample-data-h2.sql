-- TodoWebApp Sample Data for H2
-- Insert sample data for testing the application
-- H2-compatible syntax (no USE statement)
-- Admin account is created in schema-h2.sql

-- Sample users (in addition to admin)
INSERT INTO accounts (username, first_name, last_name, password, status_id, created_at) VALUES
('john.doe', 'John', 'Doe', 'password123', 1, DATEADD('DAY', -30, CURRENT_TIMESTAMP)),
('jane.smith', 'Jane', 'Smith', 'password123', 1, DATEADD('DAY', -25, CURRENT_TIMESTAMP)),
('bob.wilson', 'Bob', 'Wilson', 'password123', 1, DATEADD('DAY', -20, CURRENT_TIMESTAMP)),
('alice.brown', 'Alice', 'Brown', 'password123', 2, DATEADD('DAY', -15, CURRENT_TIMESTAMP));

-- Sample tasks for John Doe (account_id = 2)
INSERT INTO tasks (account_id, details, deadline, priority_id, status_id, created_at, last_updated) VALUES
(2, 'Complete project documentation', '2025-08-15 17:00:00', 3, 1, DATEADD('DAY', -3, CURRENT_TIMESTAMP), DATEADD('DAY', -1, CURRENT_TIMESTAMP)),
(2, 'Review code changes', '2025-08-10 12:00:00', 2, 2, DATEADD('DAY', -2, CURRENT_TIMESTAMP), DATEADD('HOUR', -6, CURRENT_TIMESTAMP)),
(2, 'Attend team meeting', '2025-08-05 10:00:00', 2, 3, DATEADD('DAY', -5, CURRENT_TIMESTAMP), DATEADD('DAY', -3, CURRENT_TIMESTAMP)),
(2, 'Update user manual', '2025-08-20 16:00:00', 1, 1, DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('HOUR', -2, CURRENT_TIMESTAMP)),
(2, 'Fix critical bug', '2025-08-06 09:00:00', 4, 2, DATEADD('DAY', -4, CURRENT_TIMESTAMP), DATEADD('DAY', -2, CURRENT_TIMESTAMP));

-- Sample tasks for Jane Smith (account_id = 3)
INSERT INTO tasks (account_id, details, deadline, priority_id, status_id, created_at, last_updated) VALUES
(3, 'Design new UI mockups', '2025-08-12 14:00:00', 3, 1, DATEADD('DAY', -2, CURRENT_TIMESTAMP), DATEADD('HOUR', -4, CURRENT_TIMESTAMP)),
(3, 'Test new features', '2025-08-08 11:00:00', 2, 2, DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('HOUR', -8, CURRENT_TIMESTAMP)),
(3, 'Prepare presentation', '2025-08-14 15:00:00', 3, 1, DATEADD('DAY', -3, CURRENT_TIMESTAMP), DATEADD('HOUR', -12, CURRENT_TIMESTAMP)),
(3, 'Database optimization', '2025-08-18 13:00:00', 2, 1, DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('HOUR', -3, CURRENT_TIMESTAMP));

-- Sample tasks for Bob Wilson (account_id = 4)
INSERT INTO tasks (account_id, details, deadline, priority_id, status_id, created_at, last_updated) VALUES
(4, 'Setup development environment', '2025-08-07 16:00:00', 2, 3, DATEADD('DAY', -6, CURRENT_TIMESTAMP), DATEADD('DAY', -4, CURRENT_TIMESTAMP)),
(4, 'Write unit tests', '2025-08-11 12:00:00', 2, 1, DATEADD('DAY', -2, CURRENT_TIMESTAMP), DATEADD('HOUR', -10, CURRENT_TIMESTAMP)),
(4, 'Deploy to staging', '2025-08-13 10:00:00', 3, 1, DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('HOUR', -5, CURRENT_TIMESTAMP));

-- Sample sessions (matching the AccountSession entity structure)
INSERT INTO account_sessions (session_id, account_id, session_created, last_accessed) VALUES
('session_john_001', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('session_jane_001', 3, DATEADD('HOUR', -2, CURRENT_TIMESTAMP), DATEADD('HOUR', -1, CURRENT_TIMESTAMP)),
('session_bob_001', 4, DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('HOUR', -12, CURRENT_TIMESTAMP));
