# Tomcat Deployment Guide

This guide explains how to deploy the TodoWebApp to Apache Tomcat using Gradle tasks.

## Prerequisites

### 1. Apache Tomcat Installation
Download and install Apache Tomcat 9.x:
- **Windows**: Download from https://tomcat.apache.org/
- **Default Installation**: `C:\apache-tomcat-9.0.65\`
- **Service Name**: Usually `Tomcat9`

### 2. MySQL Database Setup
Ensure MySQL is running and the database is configured:
```sql
-- Run the database schema
SOURCE src/main/resources/database/schema.sql;

-- Create application user
CREATE USER 'todoapp'@'%' IDENTIFIED BY 'your_secure_password';
GRANT SELECT, INSERT, UPDATE, DELETE ON todo.* TO 'todoapp'@'%';
FLUSH PRIVILEGES;
```

### 3. Update Configuration
1. **Update `src/main/webapp/META-INF/context.xml`**:
   - Change database password from `your_secure_password` to actual password
   - Adjust database connection parameters if needed

2. **Update Gradle properties** (optional):
   Create `gradle.properties` file with:
   ```properties
   tomcat.home=C:/your-tomcat-path
   tomcat.service.name=YourTomcatServiceName
   ```

## Available Gradle Tasks

### 1. Build and Package
```bash
# Build the WAR file
./gradlew war

# Build and show project info
./gradlew war info
```

### 2. Embedded Tomcat (Development)
```bash
# Start embedded Tomcat server
./gradlew tomcatRun

# Start embedded Tomcat and monitor for changes
./gradlew tomcatRunWar

# Stop embedded Tomcat
./gradlew tomcatStop
```
**Access at**: http://localhost:8080/todowebapp

### 3. External Tomcat Deployment
```bash
# Deploy to external Tomcat
./gradlew deployToTomcat

# Undeploy from Tomcat
./gradlew undeployFromTomcat

# Redeploy (undeploy + deploy)
./gradlew redeployToTomcat

# Restart Tomcat service (Windows)
./gradlew restartTomcat
```

## Deployment Workflows

### Development Workflow (Embedded Tomcat)
```bash
# Quick development cycle
./gradlew tomcatRun
# Make changes to your code
# Tomcat automatically reloads changes
```

### Production Deployment (External Tomcat)
```bash
# Build and deploy
./gradlew clean war deployToTomcat

# Or use the combined redeploy task
./gradlew redeployToTomcat
```

### CI/CD Pipeline
```bash
# Build, test, and deploy
./gradlew clean test war deployToTomcat
```

## Configuration Details

### Tomcat Plugin Configuration
The `build.gradle` includes:
- **HTTP Port**: 8080
- **HTTPS Port**: 8443
- **Stop Port**: 8005
- **Context Path**: `/todowebapp`

### Database Connection
Configured in `META-INF/context.xml`:
- **JNDI Name**: `java:/comp/env/jdbc/todo`
- **Database**: `todo`
- **User**: `todoapp`
- **Connection Pool**: 20 max connections

## Troubleshooting

### Common Issues

1. **Port Already in Use**
   ```bash
   # Check what's using port 8080
   netstat -ano | findstr :8080
   
   # Kill the process (replace PID)
   taskkill /PID <PID> /F
   ```

2. **Database Connection Failed**
   - Verify MySQL is running
   - Check database credentials in `context.xml`
   - Ensure `todoapp` user exists and has permissions
   - Verify database `todo` exists

3. **Tomcat Service Not Found**
   - Update `tomcat.service.name` in `gradle.properties`
   - Use Windows Services to check actual service name
   - For manual Tomcat: use `startup.bat` and `shutdown.bat`

4. **WAR File Not Deploying**
   - Check Tomcat logs: `{tomcat.home}/logs/catalina.out`
   - Verify `{tomcat.home}/webapps` directory permissions
   - Ensure Tomcat has write access to webapps folder

### Verification Steps

1. **Check WAR file built successfully**:
   ```bash
   ./gradlew war
   # Look for: build/libs/todowebapp-1.0.0.war
   ```

2. **Verify deployment**:
   - Check `{tomcat.home}/webapps/todowebapp.war` exists
   - Wait for Tomcat to extract (creates `todowebapp/` folder)
   - Check Tomcat logs for deployment messages

3. **Test application**:
   - Open: http://localhost:8080/todowebapp
   - Should see the login page
   - Check browser network tab for errors

### Log Files

- **Tomcat Logs**: `{tomcat.home}/logs/`
  - `catalina.out` - Main Tomcat log
  - `localhost.log` - Application logs
  - `manager.log` - Deployment logs

- **Application Logs**: Check Java console output and Tomcat logs for:
  - Database connection errors
  - JNDI lookup failures
  - Class loading issues

## Production Considerations

### Security
1. **Change default passwords** in `context.xml`
2. **Enable SSL** for production
3. **Configure firewall** to restrict access
4. **Use secure database credentials**

### Performance
1. **Tune connection pool** settings in `context.xml`
2. **Configure JVM memory** settings for Tomcat
3. **Enable connection validation** (already configured)
4. **Monitor resource usage**

### Monitoring
1. **Enable Tomcat Manager** for deployment monitoring
2. **Configure logging levels** appropriately
3. **Set up health checks** for the application
4. **Monitor database connections**

## Quick Commands Reference

```bash
# Development
./gradlew tomcatRun                    # Start dev server
./gradlew tomcatStop                   # Stop dev server

# Production Deployment
./gradlew deployToTomcat               # Deploy WAR to Tomcat
./gradlew redeployToTomcat             # Redeploy application
./gradlew undeployFromTomcat           # Remove from Tomcat

# Utilities
./gradlew info                         # Show project information
./gradlew clean war                    # Clean build and create WAR
./gradlew restartTomcat                # Restart Tomcat service
```

## Environment Variables

You can override default settings using environment variables:
```bash
# Windows
set CATALINA_HOME=C:\apache-tomcat-9.0.65
set JAVA_HOME=C:\Program Files\Java\jdk-11

# PowerShell
$env:CATALINA_HOME="C:\apache-tomcat-9.0.65"

# Then run deployment
./gradlew deployToTomcat
```

For any issues, check the Tomcat logs and ensure all prerequisites are properly configured.
