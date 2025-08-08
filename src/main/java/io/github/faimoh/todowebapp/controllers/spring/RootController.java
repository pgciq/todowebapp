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
import org.springframework.web.bind.annotation.GetMapping;

import io.github.faimoh.todowebapp.model.Account;
import jakarta.servlet.http.HttpSession;

/**
 * Root Controller for Todo Web Application
 * Handles the main entry point and routing for the application
 * 
 * @author Faisal Ahmed Pasha Mohammed https://github.com/faimoh
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
