/*
 * Copyright (c) 2020, Faisal Ahmed Pasha Mohammed https://github.com/faimoh
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package io.github.faimoh.todowebapp.controllers.spring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.faimoh.todowebapp.dao.AccountDAO;
import io.github.faimoh.todowebapp.dao.DAOFactory;
import io.github.faimoh.todowebapp.dao.DatabaseConfigurationManager;
import io.github.faimoh.todowebapp.model.Account;
import jakarta.servlet.http.HttpSession;

/**
 * Spring MVC Controller for Authentication
 * This controller handles login and logout operations using Spring WebMVC
 * 
 * @author Faisal Ahmed Pasha Mohammed https://github.com/faimoh
 */
@Controller
public class AuthController {

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

            DAOFactory daoFactory = DatabaseConfigurationManager.getDAOFactory();
            AccountDAO accountDAO = daoFactory.getAccountDAO();
            Account account = accountDAO.findAccount(username);

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
                session.setAttribute("account", account);
                
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
