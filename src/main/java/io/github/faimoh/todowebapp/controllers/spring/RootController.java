package io.github.faimoh.todowebapp.controllers.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.faimoh.todowebapp.model.Account;
import jakarta.servlet.http.HttpSession;

/**
 * Root Controller for Todo Web Application
 * Handles the main entry point and routing for the application
 */
@Controller
public class RootController {

    /**
     * Application root - redirect to appropriate dashboard based on authentication
     */
    @GetMapping("/")
    public String home(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        
        if (account == null) {
            // Not logged in - redirect to login page
            return "redirect:/login";
        } else if (account.getAccountID().equals(1)) {
            // Admin user - redirect to admin dashboard
            return "redirect:/admin/accounts/dashboard";
        } else {
            // Regular user - redirect to tasks dashboard
            return "redirect:/tasks/dashboard";
        }
    }
    
    /**
     * Index page mapping
     */
    @GetMapping("/index")
    public String index(HttpSession session) {
        return home(session);
    }
}
