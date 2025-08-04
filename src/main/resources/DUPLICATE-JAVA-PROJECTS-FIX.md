# Fix Duplicate Java Projects in VS Code

## Problem
VS Code Java Projects panel shows duplicate entries for the same project.

## Root Causes
1. **Multiple Build Systems**: Gradle + Maven + Eclipse configurations
2. **Java Extension Cache**: Corrupted workspace cache
3. **Import Settings**: Multiple importers enabled simultaneously
4. **Gradle Multi-Project**: Incorrect project structure detection

## Solutions (Try in Order)

### 1. **Clean Java Workspace Cache**
```
Ctrl+Shift+P → "Java: Clean Workspace"
```
This clears the Java Language Server cache and reimports projects.

### 2. **Reload VS Code Window**
```
Ctrl+Shift+P → "Developer: Reload Window"
```

### 3. **Check Java Extension Settings**
Open VS Code Settings (`Ctrl+,`) and verify:

**Enable only Gradle import:**
- ✅ `java.import.gradle.enabled`: `true`
- ❌ `java.import.maven.enabled`: `false`
- ❌ `java.import.eclipse.enabled`: `false`

**Build Configuration:**
- `java.configuration.updateBuildConfiguration`: `"interactive"`
- `java.autobuild.enabled`: `false`
- `java.maxConcurrentBuilds`: `1`

### 4. **Update .vscode/settings.json**
Your workspace settings have been updated with:
```json
{
    "java.import.gradle.enabled": true,
    "java.import.maven.enabled": false,
    "java.import.eclipse.enabled": false,
    "java.autobuild.enabled": false,
    "java.maxConcurrentBuilds": 1,
    "java.import.exclusions": [
        "**/node_modules/**",
        "**/.metadata/**",
        "**/archetype-resources/**",
        "**/META-INF/maven/**",
        "**/build/**",
        "**/.gradle/**"
    ]
}
```

### 5. **Remove Conflicting Project Files**
If you find any of these files, delete them:
```bash
# Eclipse files
rm .project .classpath
rm -rf .settings/

# Maven files (if not needed)
rm pom.xml

# Old Ant files (if not needed)
rm build.xml
```

### 6. **Restart Java Language Server**
```
Ctrl+Shift+P → "Java: Restart Projects"
```

### 7. **Check Gradle Settings**
Verify `settings.gradle` has only one project:
```gradle
rootProject.name = 'todowebapp'
// No include statements for subprojects
```

### 8. **Force Reimport**
```
Ctrl+Shift+P → "Java: Reload Projects"
```

## Verification Steps

After applying fixes:

1. **Check Java Projects Panel**: Should show only one `todowebapp` project
2. **Check Explorer**: No duplicate folder icons with Java logos
3. **Check Build**: `Ctrl+Shift+P → "Java: Compile Workspace"` should work without errors

## Prevention Tips

### ✅ **Best Practices:**
- Use only one build system (Gradle in your case)
- Keep `.vscode/settings.json` in `.gitignore`
- Regularly clean workspace cache
- Don't mix Gradle with Maven/Eclipse configurations

### ❌ **Avoid:**
- Having multiple build files (`pom.xml` + `build.gradle`)
- Enabling multiple importers simultaneously
- Manually editing `.project` or `.classpath` files
- Ignoring Java extension warnings

## Troubleshooting Commands

If issues persist, try these VS Code commands in order:

1. `Java: Clean Workspace`
2. `Java: Restart Projects`
3. `Java: Reload Projects`
4. `Developer: Reload Window`
5. `Java: Compile Workspace`

## When All Else Fails

1. **Close VS Code completely**
2. **Delete workspace cache**:
   ```bash
   # Windows
   del /s /q "%APPDATA%\Code\User\workspaceStorage\*todowebapp*"
   
   # Or manually delete in:
   # %APPDATA%\Code\User\workspaceStorage\
   ```
3. **Reopen project folder in VS Code**
4. **Wait for Java extension to import project**

## Current Project Status ✅

Your project is configured as:
- ✅ **Single Gradle Project**: `todowebapp`
- ✅ **No Maven**: No `pom.xml` found
- ✅ **No Eclipse**: No `.project`/`.classpath` found
- ✅ **Clean Structure**: Standard Maven/Gradle layout
- ✅ **Updated Settings**: VS Code configured for Gradle only

The updated `.vscode/settings.json` should resolve the duplicate project issue!
