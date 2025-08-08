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
