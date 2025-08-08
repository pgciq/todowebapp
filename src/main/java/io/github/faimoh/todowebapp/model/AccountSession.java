package io.github.faimoh.todowebapp.model;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.*;

/**
 * AccountSession entity representing user session tracking
 */
@Entity
@Table(name = "account_sessions")
public class AccountSession implements Serializable {

    @Id
    @Column(name = "session_id", length = 100)
    private String sessionID;
    
    @Column(name = "account_id", nullable = false)
    private int accountID;
    
    @Column(name = "session_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp sessionCreated;
    
    @Column(name = "last_accessed")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp lastAccessed;
    
    @Column(name = "session_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp sessionEnd;

    public AccountSession() {

    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getSessionID() {
        return this.sessionID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public Integer getAccountID() {
        return this.accountID;
    }

    public void setSessionCreated(Timestamp sessionCreated) {
        this.sessionCreated = sessionCreated;
    }

    public Timestamp getSessionCreated() {
        return this.sessionCreated;
    }

    public void setLastAccessed(Timestamp lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public Timestamp getLastAccessed() {
        return this.lastAccessed;
    }

    public void setSessionEnd(Timestamp sessionEnd) {
        this.sessionEnd = sessionEnd;
    }

    public Timestamp getSessionEnd() {
        return this.sessionEnd;
    }

    @Override
    public String toString() {
        return "AccountSession[" 
                + this.sessionID + ", "
                + this.accountID + ", "
                + this.sessionCreated + ", "
                + this.lastAccessed + ", "
                + this.sessionEnd
                + "]";
    }
}
