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
 * 
 * @author Faisal Ahmed Pasha Mohammed https://github.com/faimoh
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
