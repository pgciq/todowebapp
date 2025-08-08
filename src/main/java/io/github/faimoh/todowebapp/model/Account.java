package io.github.faimoh.todowebapp.model;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.*;

/**
 * Account entity representing user accounts in the todo application
 */
@Entity
@Table(name = "accounts")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer accountID;
    
    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;
    
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;
    
    @Column(name = "status_id", nullable = false)
    private Integer statusID;    

    public Account() {

    }

    public Integer getAccountID() {
        return this.accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String emailAddress) {
        this.username = emailAddress;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    public Integer getStatusID() {
        return this.statusID;
    }

    @Override
    public String toString() {
        return "Account[id=" + this.accountID
                + ", username=" + this.username
                + ", firstName=" + this.firstName
                + ", lastName=" + this.lastName
                + ", password=" + this.password
                + ", createdAt=" + this.createdAt
                + ", status=" + this.statusID                
                + "]";
    }

}
