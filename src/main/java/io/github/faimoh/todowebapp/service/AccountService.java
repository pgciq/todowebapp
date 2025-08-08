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
