# TodoWebApp Database Schema

This directory contains the database schema and sample data for the TodoWebApp application.

## Database Files

### 1. `schema.sql` - Complete Database Schema
**Full-featured schema with:**
- All tables with proper constraints and indexes
- Views for easier data access
- Stored procedures for common operations
- Triggers for audit logging
- Performance optimizations
- Sample cleanup procedures

**Recommended for:** Production environments, development with full features

### 2. `schema-simple.sql` - Simplified Schema
**Basic schema with:**
- Essential tables only
- Primary and foreign key constraints
- Basic seed data
- Minimal setup for quick start

**Recommended for:** Quick testing, minimal setup, learning purposes

### 3. `sample-data.sql` - Test Data
**Contains:**
- Sample user accounts
- Sample tasks with various statuses and priorities
- Sample session data
- Verification queries

**Use after:** Running either schema file to populate with test data

## Database Structure

Based on the provided data model diagram, the schema includes:

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│ account_statuses│    │    accounts      │    │account_sessions │
├─────────────────┤    ├──────────────────┤    ├─────────────────┤
│ status_id (PK)  │◄──┤│ account_id (PK)  │├──►│ session_id (PK) │
│ status          │    │ username         │    │ account_id (FK) │
└─────────────────┘    │ first_name       │    │ session_created │
                       │ last_name        │    │ session_end     │
                       │ password         │    └─────────────────┘
                       │ created_at       │
                       │ status_id (FK)   │
                       └──────────────────┘
                              │
                              ▼
                       ┌──────────────────┐
                       │      tasks       │
                       ├──────────────────┤
                       │ task_id (PK)     │
                       │ account_id (FK)  │
                       │ details          │
                       │ created_at       │
                       │ deadline         │
                       │ last_updated     │
                       │ status_id (FK)   │◄─┐
                       │ priority_id (FK) │◄─┤
                       └──────────────────┘  │
                              ▲              │
                              │              │
        ┌─────────────────┐   │   ┌─────────────────┐
        │ task_statuses   │───┘   │ task_priorities │
        ├─────────────────┤       ├─────────────────┤
        │ status_id (PK)  │       │ priority_id(PK) │
        │ status          │       │ priority        │
        └─────────────────┘       └─────────────────┘
```

## Setup Instructions

### Option 1: Complete Setup (Recommended)
```sql
-- Run the complete schema
SOURCE schema.sql;

-- Add sample data (optional)
SOURCE sample-data.sql;
```

### Option 2: Simple Setup (Quick Start)
```sql
-- Run the simplified schema with basic data
SOURCE schema-simple.sql;

-- Add more sample data (optional)
SOURCE sample-data.sql;
```

### Option 3: MySQL Command Line
```bash
# Create database and run schema
mysql -u root -p < schema.sql

# Add sample data
mysql -u root -p todo < sample-data.sql
```

## Default Data

### Account Statuses
- `ACTIVE` (ID: 1) - Default for new accounts
- `INACTIVE` (ID: 2) - Disabled accounts
- `SUSPENDED` (ID: 3) - Temporarily suspended

### Task Priorities
- `LOW` (ID: 1) - Default priority
- `MEDIUM` (ID: 2) - Normal priority
- `HIGH` (ID: 3) - Important tasks
- `URGENT` (ID: 4) - Critical tasks

### Task Statuses
- `PENDING` (ID: 1) - Default for new tasks
- `IN_PROGRESS` (ID: 2) - Currently being worked on
- `COMPLETED` (ID: 3) - Finished tasks
- `CANCELLED` (ID: 4) - Cancelled tasks

### Default Admin Account
- **Username:** `admin`
- **Password:** `admin123` (⚠️ Change in production!)
- **Name:** System Administrator

## Application Configuration

Update your application's database configuration to match:

### JDBC URL
```
jdbc:mysql://localhost:3306/todo?useSSL=false&serverTimezone=UTC
```

### Connection Properties
```properties
# Database connection
db.url=jdbc:mysql://localhost:3306/todo
db.username=todoapp
db.password=your_secure_password
db.driver=com.mysql.cj.jdbc.Driver
```

## Security Considerations

### For Production:
1. **Change default passwords**
2. **Use proper password hashing** (bcrypt, scrypt, etc.)
3. **Create dedicated database user** with limited permissions
4. **Enable SSL connections**
5. **Regular backups**
6. **Monitor for suspicious activity**

### Recommended User Setup:
```sql
-- Create application user
CREATE USER 'todoapp'@'%' IDENTIFIED BY 'secure_random_password';
GRANT SELECT, INSERT, UPDATE, DELETE ON todo.* TO 'todoapp'@'%';
FLUSH PRIVILEGES;
```

## Maintenance

### Regular Cleanup
The complete schema includes a cleanup procedure:
```sql
-- Clean old sessions (run periodically)
CALL sp_cleanup_old_sessions();
```

### Backup
```bash
# Backup database
mysqldump -u root -p todo > todo_backup_$(date +%Y%m%d).sql

# Restore database
mysql -u root -p todo < todo_backup_20250804.sql
```

## Troubleshooting

### Common Issues:

1. **Foreign key constraint errors**
   - Ensure tables are created in the correct order
   - Check that referenced IDs exist

2. **Character encoding issues**
   - Database and tables use UTF-8 (utf8mb4)
   - Ensure application connection uses UTF-8

3. **Permission denied**
   - Check user privileges
   - Verify database user exists and has correct permissions

### Verification Queries:
```sql
-- Check table structure
SHOW TABLES;
DESCRIBE accounts;

-- Check foreign keys
SELECT * FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE 
WHERE REFERENCED_TABLE_SCHEMA = 'todo';

-- Check sample data
SELECT COUNT(*) FROM accounts;
SELECT COUNT(*) FROM tasks;
```

## Development Tips

1. **Use transactions** for data consistency
2. **Index frequently queried columns**
3. **Monitor slow queries**
4. **Use prepared statements** to prevent SQL injection
5. **Validate data** before database operations

## Version History

- **v1.0** - Initial schema based on data model diagram
- Includes all core tables and relationships
- Added sample data and documentation
