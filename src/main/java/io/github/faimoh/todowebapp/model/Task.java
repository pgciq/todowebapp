package io.github.faimoh.todowebapp.model;

import java.sql.Timestamp;

import jakarta.persistence.*;

/**
 * Task entity representing todo tasks in the application
 */
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer taskID;
    
    @Column(name = "account_id", nullable = false)
    private Integer accountID;
    
    @Column(name = "details", nullable = false, length = 1000)
    private String details;
    
    @Column(name = "status_id", nullable = false)
    private Integer statusID;
    
    @Column(name = "priority_id", nullable = false)
    private Integer priorityID;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;
    
    @Column(name = "deadline")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp deadline;
    
    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp lastUpdated;

    public Task() {

    }

    public void setTaskID(Integer taskID) {
        this.taskID = taskID;
    }

    public Integer getTaskID() {
        return this.taskID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public Integer getAccountID() {
        return this.accountID;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return this.details;
    }

    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    public Integer getStatusID() {
        return this.statusID;
    }

    public void setPriorityID(Integer priorityID) {
        this.priorityID = priorityID;
    }

    public Integer getPriorityID() {
        return this.priorityID;
    }

    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }
    
    public Timestamp getDeadline() {
        return this.deadline;
    }

    public Timestamp getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "Task[id=" + this.taskID
                + ", username=" + this.accountID
                + ", details=" + this.details
                + ", createdAt=" + this.createdAt
                + ", deadline=" + this.deadline
                + ", lastUpdated=" + this.lastUpdated
                + ", status=" + this.statusID
                + ", priority=" + this.priorityID
                + "]";
    }
}
