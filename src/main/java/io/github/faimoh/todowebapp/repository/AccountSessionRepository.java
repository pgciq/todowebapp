package io.github.faimoh.todowebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.github.faimoh.todowebapp.model.AccountSession;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for AccountSession entity
 * Replaces the AccountSessionDAO interface with Spring Data JPA functionality
 */
@Repository
public interface AccountSessionRepository extends JpaRepository<AccountSession, String> {
    
    /**
     * Find session by session ID
     * @param sessionID the session ID to search for
     * @return Optional<AccountSession> the session if found
     */
    Optional<AccountSession> findBySessionID(String sessionID);
    
    /**
     * Find all sessions for a specific account
     * @param accountID the account ID
     * @return List<AccountSession> list of sessions for the account
     */
    List<AccountSession> findByAccountID(Integer accountID);
    
    /**
     * Find all sessions for a specific account ordered by creation time
     * @param accountID the account ID
     * @return List<AccountSession> list of sessions for the account
     */
    List<AccountSession> findByAccountIDOrderBySessionCreatedDesc(Integer accountID);
    
    /**
     * Find the last session for an account (excluding the most recent one)
     * @param accountID the account ID
     * @return Optional<AccountSession> the last session if found
     */
    @Query("SELECT a FROM AccountSession a WHERE a.accountID = :accountID ORDER BY a.sessionCreated DESC")
    List<AccountSession> findLastAccountSessionsOrderedByCreated(@Param("accountID") Integer accountID);
    
    /**
     * Update session end time
     * @param sessionID the session ID
     * @param sessionEnd the end timestamp
     * @return int number of rows affected
     */
    @Modifying
    @Transactional
    @Query("UPDATE AccountSession a SET a.sessionEnd = :sessionEnd WHERE a.sessionID = :sessionID")
    int updateSessionEnd(@Param("sessionID") String sessionID, @Param("sessionEnd") Timestamp sessionEnd);
    
    /**
     * Update last accessed time
     * @param sessionID the session ID
     * @param lastAccessed the last accessed timestamp
     * @return int number of rows affected
     */
    @Modifying
    @Transactional
    @Query("UPDATE AccountSession a SET a.lastAccessed = :lastAccessed WHERE a.sessionID = :sessionID")
    int updateLastAccessed(@Param("sessionID") String sessionID, @Param("lastAccessed") Timestamp lastAccessed);
}
