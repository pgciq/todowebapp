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

import io.github.faimoh.todowebapp.model.AccountSession;
import io.github.faimoh.todowebapp.repository.AccountSessionRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for AccountSession operations
 * Replaces direct DAO usage in controllers with service layer pattern
 * 
 * @author Faisal Ahmed Pasha Mohammed https://github.com/faimoh
 */
@Service
@Transactional
public class AccountSessionService {
    
    @Autowired
    private AccountSessionRepository accountSessionRepository;
    
    /**
     * Find account session by session ID
     * @param sessionID the session ID to search for
     * @return AccountSession the session if found, null otherwise
     */
    public AccountSession findSessionBySessionID(String sessionID) {
        Optional<AccountSession> session = accountSessionRepository.findBySessionID(sessionID);
        return session.orElse(null);
    }
    
    /**
     * Get all sessions for a specific account
     * @param accountID the account ID to get sessions for
     * @return List<AccountSession> list of sessions for the account
     */
    public List<AccountSession> getSessionsByAccountID(Integer accountID) {
        return accountSessionRepository.findByAccountID(accountID);
    }
    
    /**
     * Create a new account session
     * @param accountSession the session to create
     * @return Boolean true if successful, false otherwise
     */
    public Boolean insertSession(AccountSession accountSession) {
        try {
            // Set creation timestamp
            accountSession.setSessionCreated(new Timestamp(System.currentTimeMillis()));
            accountSession.setLastAccessed(new Timestamp(System.currentTimeMillis()));
            
            accountSessionRepository.save(accountSession);
            return true;
        } catch (Exception e) {
            System.err.println("Error creating session: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update session's last accessed timestamp
     * @param sessionID the session ID to update
     * @return Boolean true if successful, false otherwise
     */
    public Boolean updateSessionTimestamp(String sessionID) {
        try {
            int rowsAffected = accountSessionRepository.updateLastAccessed(
                sessionID, 
                new Timestamp(System.currentTimeMillis())
            );
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Error updating session timestamp: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete a session
     * @param sessionID the session ID to delete
     * @return Boolean true if successful, false otherwise
     */
    public Boolean deleteSession(String sessionID) {
        try {
            if (sessionID == null || sessionID.trim().isEmpty()) {
                return false;
            }
            
            accountSessionRepository.deleteById(sessionID);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting session: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete all sessions for an account
     * @param accountID the account ID to delete sessions for
     * @return Boolean true if successful, false otherwise
     */
    public Boolean deleteAllSessionsForAccount(Integer accountID) {
        try {
            List<AccountSession> sessions = getSessionsByAccountID(accountID);
            for (AccountSession session : sessions) {
                accountSessionRepository.deleteById(session.getSessionID());
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting sessions for account: " + e.getMessage());
            return false;
        }
    }
}
