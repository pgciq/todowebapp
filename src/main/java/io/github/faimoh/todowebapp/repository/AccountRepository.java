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
