package io.github.faimoh.todowebapp.controllers.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.faimoh.todowebapp.service.AccountService;
import io.github.faimoh.todowebapp.model.Account;
import jakarta.servlet.http.HttpSession;

/**
 * Spring MVC Controller for User Profile Management
 * This controller handles user profile operations using Spring WebMVC
 * Updated to use Spring Data JPA Service layer instead of DAO pattern
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AccountService accountService;

    /**
     * Show user profile
     * Equivalent to UserReadProfileAction
     */
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        Account sessionUser = (Account) session.getAttribute("account");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        
        // The account is already in the session, no need to fetch from database
        // The JSP will access it via ${sessionScope.account}
        return "users/viewProfile";
    }

    /**
     * Update user profile
     * Equivalent to UserUpdateProfileAction
     */
    @PostMapping("/update")
    public String updateProfile(@RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam(required = false) String password,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        try {
            Account sessionUser = (Account) session.getAttribute("account");
            if (sessionUser == null) {
                return "redirect:/login";
            }

            // Update account details
            sessionUser.setFirstName(firstName);
            sessionUser.setLastName(lastName);

            // Save to database
            boolean isAccountUpdated;
            
            if (password == null || password.trim().isEmpty()) {
                // Update only profile information (no password change)
                isAccountUpdated = accountService.updateAccount(sessionUser);
            } else {
                // Update profile and password
                sessionUser.setPassword(password);
                isAccountUpdated = accountService.changePassword(sessionUser);
            }

            if (isAccountUpdated) {
                // Update session with new data
                session.setAttribute("account", sessionUser);
                redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Technical error. Please try again later.");
            }

            return "redirect:/users/profile";
        } catch (Exception e) {
            System.err.println("Error updating profile: " + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Error updating profile: " + e.getMessage());
            return "redirect:/users/profile";
        }
    }
}
