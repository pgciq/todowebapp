-- TodoWebApp Sample Data for H2
-- Insert sample data for testing the application
-- H2-compatible syntax (no USE statement)
-- Admin account is created in schema-h2.sql

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

-- Sample sessions (matching the AccountSession entity structure)
INSERT INTO account_sessions (session_id, account_id, session_created, last_accessed) VALUES
('session_john_001', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('session_jane_001', 3, DATEADD('HOUR', -2, CURRENT_TIMESTAMP), DATEADD('HOUR', -1, CURRENT_TIMESTAMP)),
('session_bob_001', 4, DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('HOUR', -12, CURRENT_TIMESTAMP));
