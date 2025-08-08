package io.github.faimoh.todowebapp.controllers.spring;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.faimoh.todowebapp.model.Account;
import io.github.faimoh.todowebapp.model.AccountSession;
import io.github.faimoh.todowebapp.service.AccountService;
import io.github.faimoh.todowebapp.service.AccountSessionService;
import jakarta.servlet.http.HttpSession;

/**
 * Spring MVC Controller for Authentication
 * This controller handles login and logout operations using Spring WebMVC
 * Updated to use Spring Data JPA Service layer instead of DAO pattern
 */
@Controller
public class AuthController {

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private AccountSessionService accountSessionService;

    /**
     * Show login form
     * Equivalent to LoginAction (GET)
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    /**
     * Process login
     * Equivalent to LoginAction (POST)
     * Updated to use AccountService instead of DAO pattern
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                       @RequestParam String password,
                       HttpSession session,
                       Model model) {
        try {
            if (username == null || username.trim().isEmpty() || 
                password == null || password.trim().isEmpty()) {
                model.addAttribute("message", "Username and password are required.");
                return "login";
            }

            Account account = accountService.findByUsername(username);

            if (account == null) {
                model.addAttribute("message", "Account doesn't exist.");
                return "login";
            } else if (account.getStatusID().equals(2)) {
                model.addAttribute("message", "Account disabled. Contact administrator.");
                return "login";
            } else if (!account.getPassword().equals(password)) {
                model.addAttribute("message", "Authentication failed. Check credentials supplied.");
                return "login";
            } else {
                // Authentication successful
                
                // Get the most recent previous session for this account (before current login)
                List<AccountSession> previousSessions = accountSessionService.getSessionsByAccountIDOrderByCreated(account.getAccountID());
                AccountSession previousSession = null;
                if (previousSessions != null && !previousSessions.isEmpty()) {
                    // Get the most recent session (sessions are ordered by creation date desc)
                    previousSession = previousSessions.get(0);
                }
                
                // Create new session record
                AccountSession newSession = new AccountSession();
                newSession.setSessionID(session.getId());
                newSession.setAccountID(account.getAccountID());
                newSession.setSessionCreated(new Timestamp(System.currentTimeMillis()));
                newSession.setLastAccessed(new Timestamp(System.currentTimeMillis()));
                
                // Save the new session
                accountSessionService.insertSession(newSession);
                
                // Store account and previous session info in HTTP session
                session.setAttribute("account", account);
                session.setAttribute("accountPreviousSession", previousSession);
                
                // Redirect based on account type
                if (account.getAccountID().equals(1)) {
                    // Admin user
                    return "redirect:/admin/accounts/dashboard";
                } else {
                    // Regular user
                    return "redirect:/tasks/dashboard";
                }
            }
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            model.addAttribute("message", "Technical error during login. Please try again later.");
            return "login";
        }
    }

    /**
     * Process logout
     * Equivalent to LogoutAction
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}
