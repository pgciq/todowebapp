package io.github.faimoh.todowebapp;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;

/**
 * Spring Boot Application Context Test
 * Verifies that the Spring Boot application context loads successfully
 */
@SpringBootTest
@ActiveProfiles("test")
class TodoWebApplicationTests {

    @Test
    void contextLoads() {
        // This test will pass if the application context loads successfully
        // Spring Boot will auto-configure all necessary beans
    }
}
