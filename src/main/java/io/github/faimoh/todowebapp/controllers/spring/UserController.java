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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.faimoh.todowebapp.dao.AccountDAO;
import io.github.faimoh.todowebapp.dao.DAOFactory;
import io.github.faimoh.todowebapp.dao.DatabaseConfigurationManager;
import io.github.faimoh.todowebapp.model.Account;
import jakarta.servlet.http.HttpSession;

/**
 * Spring MVC Controller for User Profile Management
 * This controller handles user profile operations using Spring WebMVC
 * 
 * @author Faisal Ahmed Pasha Mohammed https://github.com/faimoh
 */
@Controller
@RequestMapping("/users")
public class UserController {

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
            DAOFactory daoFactory = DatabaseConfigurationManager.getDAOFactory();
            AccountDAO accountDAO = daoFactory.getAccountDAO();
            boolean isUpdated = accountDAO.updateAccount(sessionUser);

            if (isUpdated) {
                // Update session with new data
                session.setAttribute("account", sessionUser);
                redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Technical error. Please try again later.");
            }

            return "redirect:/spring/users/profile";
        } catch (Exception e) {
            System.err.println("Error updating profile: " + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Error updating profile: " + e.getMessage());
            return "redirect:/spring/users/profile";
        }
    }
}
