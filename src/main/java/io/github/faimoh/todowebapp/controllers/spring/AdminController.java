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

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.faimoh.todowebapp.actions.Utilities;
import io.github.faimoh.todowebapp.dao.AccountDAO;
import io.github.faimoh.todowebapp.dao.DAOFactory;
import io.github.faimoh.todowebapp.dao.DatabaseConfigurationManager;
import io.github.faimoh.todowebapp.model.Account;
import jakarta.servlet.http.HttpSession;

/**
 * Spring MVC Controller for Admin Account Management
 * This controller handles all admin operations for account management using Spring WebMVC
 * instead of the traditional action-based approach
 * 
 * @author Faisal Ahmed Pasha Mohammed https://github.com/faimoh
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     * Check if the current user is an admin
     * Admin user has accountID = 1
     */
    private boolean isAdmin(HttpSession session) {
        Account sessionUser = (Account) session.getAttribute("account");
        return sessionUser != null && sessionUser.getAccountID().equals(1);
    }

    /**
     * Display admin accounts dashboard
     * Equivalent to AdminAccountsDashboardAction
     */
    @GetMapping("/accounts/dashboard")
    public String accountsDashboard(Model model, HttpSession session) {
        try {
            if (!isAdmin(session)) {
                return "redirect:/spring/login";
            }

            DAOFactory daoFactory = DatabaseConfigurationManager.getDAOFactory();
            AccountDAO accountDAO = daoFactory.getAccountDAO();
            ArrayList<Account> accountsList = accountDAO.getAllAccounts();

            model.addAttribute("accountsList", accountsList);
            return "admin/accounts/dashboard";
        } catch (Exception e) {
            System.err.println("Error loading accounts dashboard: " + e.getMessage());
            model.addAttribute("message", "Error loading accounts: " + e.getMessage());
            return "admin/accounts/dashboard";
        }
    }

    /**
     * Show new account form
     * Equivalent to AdminNewAccountFormAction
     */
    @GetMapping("/accounts/new")
    public String newAccountForm(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/spring/login";
        }
        return "admin/accounts/newAccount";
    }

    /**
     * Create a new account
     * Equivalent to AdminCreateAccountAction
     */
    @PostMapping("/accounts/create")
    public String createAccount(@RequestParam String username,
                              @RequestParam String firstName,
                              @RequestParam String lastName,
                              HttpSession session,
                              Model model) {
        try {
            if (!isAdmin(session)) {
                return "redirect:/spring/login";
            }

            // Validate input parameters
            username = Utilities.parseRequestParameterWithDefault(username, "");
            firstName = Utilities.parseRequestParameterWithDefault(firstName, "");
            lastName = Utilities.parseRequestParameterWithDefault(lastName, "");

            String message = "";

            // Check for empty fields
            if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                message = "One or more required fields are empty.";
                model.addAttribute("username", username);
                model.addAttribute("firstName", firstName);
                model.addAttribute("lastName", lastName);
                model.addAttribute("message", message);
                return "admin/accounts/createAccountResult";
            }

            // Check if username already exists
            DAOFactory daoFactory = DatabaseConfigurationManager.getDAOFactory();
            AccountDAO accountDAO = daoFactory.getAccountDAO();
            Account existingAccount = accountDAO.findAccount(username);

            if (existingAccount != null) {
                message = "Username already exists. Try another.";
                model.addAttribute("username", username);
                model.addAttribute("firstName", firstName);
                model.addAttribute("lastName", lastName);
                model.addAttribute("message", message);
                return "admin/accounts/createAccountResult";
            }

            // Create new account
            Account newAccount = new Account();
            newAccount.setUsername(username);
            newAccount.setFirstName(firstName);
            newAccount.setLastName(lastName);

            boolean isAccountCreated = accountDAO.insertAccount(newAccount);

            if (isAccountCreated) {
                message = "Successfully created a new account.<br> Create another account:";
            } else {
                message = "Technical error. Please try again later.";
            }

            model.addAttribute("message", message);
            return "admin/accounts/createAccountResult";

        } catch (Exception e) {
            System.err.println("Error creating account: " + e.getMessage());
            model.addAttribute("message", "Error creating account: " + e.getMessage());
            return "admin/accounts/createAccountResult";
        }
    }

    /**
     * Show account details
     * Equivalent to AdminReadAccountDetailsAction
     */
    @GetMapping("/accounts/details")
    public String accountDetails(@RequestParam(required = false) String id,
                               Model model,
                               HttpSession session) {
        try {
            if (!isAdmin(session)) {
                return "redirect:/spring/login";
            }

            String message = "";

            if (id == null || id.trim().isEmpty()) {
                message = "No such account exists.";
                model.addAttribute("message", message);
            } else {
                try {
                    int accountId = Integer.parseInt(id);
                    DAOFactory daoFactory = DatabaseConfigurationManager.getDAOFactory();
                    AccountDAO accountDAO = daoFactory.getAccountDAO();
                    Account account = accountDAO.findAccount(accountId);

                    if (account == null) {
                        message = "No such account exists.";
                        model.addAttribute("message", message);
                    } else {
                        model.addAttribute("account", account);
                    }
                } catch (NumberFormatException e) {
                    message = "Invalid account ID format.";
                    model.addAttribute("message", message);
                }
            }

            return "admin/accounts/accountDetails";
        } catch (Exception e) {
            System.err.println("Error loading account details: " + e.getMessage());
            model.addAttribute("message", "Error loading account details: " + e.getMessage());
            return "admin/accounts/accountDetails";
        }
    }

    /**
     * Update an account
     * Equivalent to AdminUpdateAccountAction
     */
    @PostMapping("/accounts/update")
    public String updateAccount(@RequestParam String accountID,
                              @RequestParam String username,
                              @RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam(required = false) String password,
                              @RequestParam String status,
                              @RequestParam(required = false, defaultValue = "false") boolean resetPassword,
                              HttpSession session,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            if (!isAdmin(session)) {
                return "redirect:/spring/login";
            }

            String message = "";

            // Validate required fields
            if (username == null || username.trim().isEmpty() || 
                firstName == null || firstName.trim().isEmpty() || 
                lastName == null || lastName.trim().isEmpty()) {
                
                message = "One or more required fields are empty.";
                Account account = new Account();
                account.setAccountID(Integer.parseInt(accountID));
                account.setUsername(username);
                account.setFirstName(firstName);
                account.setLastName(lastName);
                account.setStatusID(Integer.parseInt(status));
                
                model.addAttribute("account", account);
                model.addAttribute("message", message);
                return "admin/accounts/updateAccountResult";
            }

            DAOFactory daoFactory = DatabaseConfigurationManager.getDAOFactory();
            AccountDAO accountDAO = daoFactory.getAccountDAO();
            Account accountToUpdate = accountDAO.findAccount(username);

            if (accountToUpdate == null) {
                message = "Update failed - Account doesn't exist!";
                model.addAttribute("accountID", accountID);
                model.addAttribute("username", username);
                model.addAttribute("firstName", firstName);
                model.addAttribute("lastName", lastName);
                model.addAttribute("message", message);
                return "admin/accounts/updateAccountResult";
            }

            // Update account details
            accountToUpdate.setFirstName(firstName);
            accountToUpdate.setLastName(lastName);
            accountToUpdate.setStatusID(Integer.parseInt(status));

            boolean isUpdated;

            if (resetPassword) {
                // Reset password functionality - use the method that handles both password reset and account update
                isUpdated = accountDAO.updateAccount(accountToUpdate, true);
                if (isUpdated) {
                    message = "Account updated successfully! Password has been reset.";
                    // Update session if admin updated their own account
                    Account sessionUser = (Account) session.getAttribute("account");
                    if (accountToUpdate.getAccountID().equals(sessionUser.getAccountID())) {
                        sessionUser.setFirstName(accountToUpdate.getFirstName());
                        sessionUser.setLastName(accountToUpdate.getLastName());
                        session.setAttribute("account", sessionUser);
                    }
                } else {
                    message = "Technical error. Please try again later.";
                }
            } else if (password != null && !password.trim().isEmpty()) {
                // Change password functionality
                accountToUpdate.setPassword(password);
                isUpdated = accountDAO.changePassword(accountToUpdate);
                if (isUpdated) {
                    message = "Account updated successfully! Password has been changed.";
                    // Update session if admin updated their own account
                    Account sessionUser = (Account) session.getAttribute("account");
                    if (accountToUpdate.getAccountID().equals(sessionUser.getAccountID())) {
                        sessionUser.setFirstName(accountToUpdate.getFirstName());
                        sessionUser.setLastName(accountToUpdate.getLastName());
                        session.setAttribute("account", sessionUser);
                    }
                } else {
                    message = "Technical error. Please try again later.";
                }
            } else {
                // Regular update without password change
                isUpdated = accountDAO.updateAccount(accountToUpdate, false);
                if (isUpdated) {
                    message = "Account updated successfully!";
                    // Update session if admin updated their own account
                    Account sessionUser = (Account) session.getAttribute("account");
                    if (accountToUpdate.getAccountID().equals(sessionUser.getAccountID())) {
                        sessionUser.setFirstName(accountToUpdate.getFirstName());
                        sessionUser.setLastName(accountToUpdate.getLastName());
                        session.setAttribute("account", sessionUser);
                    }
                } else {
                    message = "Technical error. Please try again later.";
                }
            }

            // Always add the account to the model for the result page
            model.addAttribute("account", accountToUpdate);
            model.addAttribute("message", message);
            return "admin/accounts/updateAccountResult";

        } catch (Exception e) {
            System.err.println("Error updating account: " + e.getMessage());
            model.addAttribute("message", "Error updating account: " + e.getMessage());
            return "admin/accounts/updateAccountResult";
        }
    }

    /**
     * Admin home page - redirects to dashboard
     */
    @GetMapping("/")
    public String adminHome(HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/spring/login";
        }
        return "redirect:/spring/admin/accounts/dashboard";
    }

    /**
     * Alternative admin home mapping
     */
    @GetMapping("")
    public String adminHomeAlt(HttpSession session) {
        return adminHome(session);
    }
}
