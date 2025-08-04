# Aliyun Repository Configuration

This project has been configured to use Aliyun (阿里云) repository mirrors for faster downloads in China.

## Configuration Files

### 1. gradle-wrapper.properties
Updated to use Aliyun Gradle distribution mirror:
```properties
distributionUrl=https\://mirrors.aliyun.com/gradle/gradle-8.12-bin.zip
```

### 2. build.gradle
Repository configuration prioritizes Aliyun mirrors:
```gradle
repositories {
    // Use Aliyun mirrors for faster downloads in China
    maven { url 'https://maven.aliyun.com/repository/public' }
    maven { url 'https://maven.aliyun.com/repository/central' }
    maven { url 'https://maven.aliyun.com/repository/gradle-plugin' }
    // Fallback to Maven Central
    mavenCentral()
}
```

### 3. gradle.properties
Performance optimizations for faster builds:
```properties
# Enable Gradle daemon for faster builds
org.gradle.daemon=true
# Enable parallel builds
org.gradle.parallel=true
# Configure JVM for Gradle daemon
org.gradle.jvmargs=-Xmx2g -XX:MaxMetaspaceSize=512m
# Enable build cache
org.gradle.caching=true
```

### 4. init.gradle
Global repository configuration script:
```gradle
allprojects {
    repositories {
        maven { url 'https://maven.aliyun.com/repository/central' }
        maven { url 'https://maven.aliyun.com/repository/public' }
        maven { url 'https://maven.aliyun.com/repository/gradle-plugin' }
        mavenCentral()  // Fallback
    }
}
```

## Aliyun Repository URLs

| Repository Type | Aliyun Mirror URL |
|----------------|-------------------|
| Maven Central | `https://maven.aliyun.com/repository/central` |
| Maven Public | `https://maven.aliyun.com/repository/public` |
| Gradle Plugin | `https://maven.aliyun.com/repository/gradle-plugin` |
| JCenter | `https://maven.aliyun.com/repository/jcenter` |
| Google | `https://maven.aliyun.com/repository/google` |
| Gradle Distribution | `https://mirrors.aliyun.com/gradle/` |

## Usage

### Standard Build
```bash
# Uses repositories configured in build.gradle
gradle clean build
```

### Using Init Script (Global Configuration)
```bash
# Uses init.gradle for all repositories
gradle clean build --init-script init.gradle
```

### First-time Setup
If this is the first time using the Gradle wrapper, it will download from Aliyun:
```bash
# Downloads Gradle from Aliyun mirror
./gradlew clean build
```

## Benefits

1. **Faster Downloads**: Aliyun mirrors are optimized for users in China
2. **Reduced Latency**: Lower network latency compared to international repositories
3. **High Availability**: Aliyun provides reliable mirror services
4. **Automatic Fallback**: If Aliyun mirrors are unavailable, falls back to original repositories

## Performance Optimizations

The configuration includes several performance optimizations:

- **Gradle Daemon**: Keeps Gradle running in background for faster subsequent builds
- **Parallel Builds**: Enables parallel execution of independent tasks
- **Build Cache**: Caches build outputs to avoid redundant work
- **Configuration Cache**: Speeds up configuration phase (experimental)
- **Optimized JVM Settings**: Allocates appropriate memory for build process

## Troubleshooting

### If builds are slow:
1. Check network connectivity to Aliyun mirrors
2. Verify DNS resolution for `maven.aliyun.com`
3. Consider using VPN or proxy if needed

### If dependencies fail to resolve:
1. Dependencies will automatically fall back to Maven Central
2. Check if specific dependency exists in Aliyun mirrors
3. Temporarily disable Aliyun mirrors by commenting them out

### To disable Aliyun mirrors:
Comment out the Aliyun repository URLs in `build.gradle`:
```gradle
repositories {
    // maven { url 'https://maven.aliyun.com/repository/public' }
    // maven { url 'https://maven.aliyun.com/repository/central' }
    // maven { url 'https://maven.aliyun.com/repository/gradle-plugin' }
    mavenCentral()
}
```

## Global Configuration (Optional)

To use Aliyun mirrors for all Gradle projects on your system:

1. Create `~/.gradle/init.gradle` (Linux/Mac) or `%USERPROFILE%\.gradle\init.gradle` (Windows)
2. Copy the content from the project's `init.gradle` file
3. All future Gradle builds will use Aliyun mirrors by default

## Verification

To verify that Aliyun mirrors are being used:
```bash
# Enable debug logging to see repository access
gradle dependencies --debug | grep -i aliyun
```

## Mirror Status

Check Aliyun mirror status at: https://developer.aliyun.com/mirror/

## Alternative Chinese Mirrors

If Aliyun mirrors are unavailable, consider these alternatives:

- **Tencent Cloud**: `https://mirrors.cloud.tencent.com/`
- **Huawei Cloud**: `https://mirrors.huaweicloud.com/`
- **USTC**: `https://mirrors.ustc.edu.cn/`
