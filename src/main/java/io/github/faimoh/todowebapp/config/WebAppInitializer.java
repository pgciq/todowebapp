package io.github.faimoh.todowebapp.config;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

/**
 * Spring WebMVC Initializer
 * Replaces traditional web.xml configuration for Spring setup
 * This class is automatically detected by Spring and used to configure
 * the DispatcherServlet and application contexts
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Root application context configuration classes
     * These are shared across the entire application
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class };
    }

    /**
     * Web application context configuration classes
     * These are specific to the web layer (controllers, view resolvers, etc.)
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }

    /**
     * Servlet mapping pattern
     * Maps all requests to the Spring DispatcherServlet
     * This completely replaces traditional servlet configuration
     */
    @Override
    protected @NonNull String[] getServletMappings() {
        return new String[] { "/" };
    }
    
    /**
     * Configure ServletContext settings
     * Equivalent to <session-config> in web.xml
     */
    @Override
    public void onStartup(@NonNull ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        
        // Configure session timeout (30 minutes in minutes)
        servletContext.getSessionCookieConfig().setMaxAge(30 * 60);
        servletContext.setSessionTimeout(30);
        
        // Set default Spring profile if not already set
        String activeProfile = System.getProperty("spring.profiles.active");
        if (activeProfile == null || activeProfile.isEmpty()) {
            // Check environment variable as fallback
            activeProfile = System.getenv("SPRING_PROFILES_ACTIVE");
            if (activeProfile == null || activeProfile.isEmpty()) {
                // Default to development profile
                servletContext.setInitParameter("spring.profiles.active", "dev");
                System.setProperty("spring.profiles.active", "dev");
                System.out.println("No active profile specified, defaulting to 'dev' profile");
            } else {
                servletContext.setInitParameter("spring.profiles.active", activeProfile);
                System.setProperty("spring.profiles.active", activeProfile);
                System.out.println("Using profile from environment variable: " + activeProfile);
            }
        } else {
            servletContext.setInitParameter("spring.profiles.active", activeProfile);
            System.out.println("Using profile from system property: " + activeProfile);
        }
    }
}
