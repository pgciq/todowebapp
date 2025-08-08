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

import io.github.faimoh.todowebapp.model.Account;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Account entity
 * Replaces the AccountDAO interface with Spring Data JPA functionality
 * 
 * @author Faisal Ahmed Pasha Mohammed https://github.com/faimoh
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    
    /**
     * Find account by username
     * @param username the username to search for
     * @return Optional<Account> the account if found
     */
    Optional<Account> findByUsername(String username);
    
    /**
     * Find account by ID
     * @param accountID the account ID to search for
     * @return Optional<Account> the account if found
     */
    Optional<Account> findByAccountID(Integer accountID);
    
    /**
     * Find all accounts (equivalent to getAllAccounts)
     * @return List<Account> list of all accounts
     */
    List<Account> findAll();
    
    /**
     * Update account password
     * @param accountID the account ID
     * @param password the new password
     * @return int number of rows affected
     */
    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.password = :password WHERE a.accountID = :accountID")
    int updatePassword(@Param("accountID") Integer accountID, @Param("password") String password);
    
    /**
     * Update account details (without password)
     * @param accountID the account ID
     * @param firstName the first name
     * @param lastName the last name
     * @param statusID the status ID
     * @return int number of rows affected
     */
    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.firstName = :firstName, a.lastName = :lastName, a.statusID = :statusID WHERE a.accountID = :accountID")
    int updateAccountDetails(@Param("accountID") Integer accountID, 
                           @Param("firstName") String firstName, 
                           @Param("lastName") String lastName, 
                           @Param("statusID") Integer statusID);
}
