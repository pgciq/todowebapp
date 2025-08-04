# Gradle Tomcat Deployment - Quick Reference

## 🚀 Available Tasks

### Build Tasks
```bash
./gradlew clean          # Clean build directory
./gradlew war            # Build WAR file
./gradlew clean war      # Clean build and create WAR
./gradlew info           # Show project information
```

### Embedded Tomcat (Development)
```bash
./gradlew tomcatRun      # Start embedded Tomcat (http://localhost:8080/todowebapp)
./gradlew tomcatRunWar   # Run with packaged WAR
./gradlew tomcatStop     # Stop embedded Tomcat
```

### External Tomcat Deployment
```bash
./gradlew deployToTomcat      # Deploy WAR to Tomcat webapps
./gradlew undeployFromTomcat  # Remove from Tomcat
./gradlew redeployToTomcat    # Undeploy + Deploy
./gradlew restartTomcat       # Restart Tomcat service (Windows)
```

## 📁 File Locations

- **WAR File**: `build/libs/todowebapp-1.0.0.war`
- **Tomcat webapps**: `C:/apache-tomcat-9.0.65/webapps/` (default)
- **Application URL**: http://localhost:8080/todowebapp

## ⚙️ Configuration

### Environment Variables
```bash
# Set Tomcat home directory
set CATALINA_HOME=C:\your-tomcat-path

# Set Java home
set JAVA_HOME=C:\Program Files\Java\jdk-11
```

### Database Setup
1. Create database: `CREATE DATABASE todo;`
2. Run schema: `SOURCE src/main/resources/database/schema.sql;`
3. Update password in `src/main/webapp/META-INF/context.xml`

## 🔧 Common Workflows

### Development (Quick Start)
```bash
# Start development server
./gradlew tomcatRun
# Access: http://localhost:8080/todowebapp
```

### Production Deployment
```bash
# Build and deploy to external Tomcat
./gradlew clean war deployToTomcat
```

### Update Existing Deployment
```bash
# Redeploy with automatic cleanup
./gradlew redeployToTomcat
```

## 🎯 Pro Tips

1. **First time setup**: Ensure database is created and configured
2. **Port conflicts**: Check if port 8080 is in use before starting
3. **Permissions**: Ensure Tomcat has write access to webapps directory
4. **Logs**: Check Tomcat logs in `{CATALINA_HOME}/logs/catalina.out`
5. **Hot reload**: Use `tomcatRun` for development with automatic reloading

## 🚨 Troubleshooting

- **Port 8080 busy**: `netstat -ano | findstr :8080` then kill process
- **Database connection failed**: Check MySQL service and credentials
- **WAR not deploying**: Verify Tomcat webapps directory permissions
- **Context path issues**: Ensure context.xml path matches Gradle config

---
📖 For detailed instructions, see `DEPLOYMENT.md`
