# Database Configuration Guide

This application **automatically detects and switches** between H2 (development) and MySQL (production) databases based on the configured JNDI datasource.

## ✨ **Automatic Database Detection**

The application uses `DatabaseConfigurationManager` to automatically:
- **Detect** which database is configured in `context.xml`
- **Switch** DAO implementations (H2DataSource vs MySQLDataSource) 
- **Configure** appropriate connection settings
- **Log** the detected environment for debugging

**No code changes required** - just change the database configuration!

## Quick Start - Development

By default, the application is configured for development using H2 in-memory database:

```bash
# Build and run with H2 (default)
gradle tomcatRun
```

The H2 database will automatically:
- Initialize with the schema from `database/schema-h2.sql`
- Load sample data for testing
- Reset on each application restart

## How It Works

### Automatic Detection Process

1. **JNDI Lookup**: Application queries the `jdbc/todo` datasource
2. **Database Metadata**: Examines `DatabaseMetaData.getDatabaseProductName()`
3. **DAO Factory Selection**: 
   - `H2` detected → `H2DataSourceDAOFactory` 
   - `MySQL` detected → `MySQLDataSourceDAOFactory`
4. **Runtime Logging**: Logs detected database type for verification

### Code Example

```java
// Old way (hardcoded)
DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MySQLDataSource);

// New way (automatic detection)
DAOFactory daoFactory = DatabaseConfigurationManager.getDAOFactory();
```

### Environment Management

### Development Environment (H2)

```bash
# Set up development environment
gradle setupDev

# Build and run
gradle tomcatRun
```

**Features:**
- In-memory H2 database
- Auto-initialization with sample data  
- No external database setup required
- Perfect for development and testing

**Default Accounts:**
- Admin: `admin` / `admin123`
- User: `user` / `user123`

### Production Environment (MySQL)

```bash
# Set up production environment
gradle setupProd

# Build production WAR
gradle warProd

# Deploy the WAR from build/libs/todo-prod-1.0.0.war
```

**Prerequisites:**
1. MySQL server running
2. Create database: `CREATE DATABASE todo;`
3. Run schema: `mysql todo < src/main/resources/database/schema.sql`
4. Update credentials in `context-prod.xml` if needed

## Database Schemas

- **MySQL Schema**: `src/main/resources/database/schema.sql`
- **H2 Schema**: `src/main/resources/database/schema-h2.sql`

## Configuration Files

- **Development**: `src/main/webapp/META-INF/context-dev.xml` (H2)
- **Production**: `src/main/webapp/META-INF/context-prod.xml` (MySQL)
- **Active**: `src/main/webapp/META-INF/context.xml` (current environment)

## Switching Environments

```bash
# Check current configuration
gradle showConfig

# Switch to development
gradle setupDev

# Switch to production  
gradle setupProd
```

## Benefits

### Development (H2)
- ✅ Zero configuration
- ✅ Fast startup
- ✅ No external dependencies
- ✅ Automatic sample data
- ✅ Clean state on restart

### Production (MySQL)
- ✅ Persistent data
- ✅ Production-grade performance
- ✅ Backup and recovery
- ✅ Scaling capabilities
- ✅ ACID compliance

## Troubleshooting

### H2 Issues
- Database resets on restart (this is expected)
- Check H2 console at: `http://localhost:8080/h2-console` (if enabled)

### MySQL Issues  
- Ensure MySQL is running: `systemctl status mysql`
- Check database exists: `SHOW DATABASES;`
- Verify user permissions: `SHOW GRANTS FOR 'todo'@'%';`

### Connection Issues
- Check context.xml for correct database URL
- Verify JNDI resource name matches: `jdbc/todo`
- Check Tomcat logs for detailed error messages
