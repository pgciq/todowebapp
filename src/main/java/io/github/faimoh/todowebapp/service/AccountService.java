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
package io.github.faimoh.todowebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.faimoh.todowebapp.model.Account;
import io.github.faimoh.todowebapp.repository.AccountRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for Account operations
 * Replaces direct DAO usage in controllers with service layer pattern
 * 
 * @author Faisal Ahmed Pasha Mohammed https://github.com/faimoh
 */
@Service
@Transactional
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    /**
     * Find account by username
     * @param username the username to search for
     * @return Account the account if found, null otherwise
     */
    public Account findByUsername(String username) {
        Optional<Account> account = accountRepository.findByUsername(username);
        return account.orElse(null);
    }
    
    /**
     * Find account by ID
     * @param accountID the account ID to search for
     * @return Account the account if found, null otherwise
     */
    public Account findByAccountID(Integer accountID) {
        Optional<Account> account = accountRepository.findByAccountID(accountID);
        return account.orElse(null);
    }
    
    /**
     * Get all accounts
     * @return List<Account> list of all accounts
     */
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    
    /**
     * Create a new account
     * @param account the account to create
     * @return Boolean true if successful, false otherwise
     */
    public Boolean createAccount(Account account) {
        try {
            // Set created timestamp
            account.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            // Set default status (enabled)
            if (account.getStatusID() == null) {
                account.setStatusID(1);
            }
            accountRepository.save(account);
            return true;
        } catch (Exception e) {
            System.err.println("Error creating account: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update account details (without password)
     * @param account the account to update
     * @return Boolean true if successful, false otherwise
     */
    public Boolean updateAccount(Account account) {
        try {
            if (account.getAccountID() == null) {
                return false;
            }
            
            int rowsAffected = accountRepository.updateAccountDetails(
                account.getAccountID(),
                account.getFirstName(),
                account.getLastName(),
                account.getStatusID()
            );
            
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Error updating account: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update account with password reset option
     * @param account the account to update
     * @param resetPassword whether to reset password
     * @return Boolean true if successful, false otherwise
     */
    public Boolean updateAccount(Account account, boolean resetPassword) {
        try {
            if (account.getAccountID() == null) {
                return false;
            }
            
            if (resetPassword) {
                // Reset password to default
                account.setPassword("password");
                return changePassword(account);
            } else {
                return updateAccount(account);
            }
        } catch (Exception e) {
            System.err.println("Error updating account with reset: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Change account password
     * @param account the account with new password
     * @return Boolean true if successful, false otherwise
     */
    public Boolean changePassword(Account account) {
        try {
            if (account.getAccountID() == null || account.getPassword() == null) {
                return false;
            }
            
            int rowsAffected = accountRepository.updatePassword(
                account.getAccountID(),
                account.getPassword()
            );
            
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Error changing password: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Reset account password to default
     * @param account the account to reset
     * @return Boolean true if successful, false otherwise
     */
    public Boolean resetPassword(Account account) {
        try {
            account.setPassword("password");
            return changePassword(account);
        } catch (Exception e) {
            System.err.println("Error resetting password: " + e.getMessage());
            return false;
        }
    }
}
