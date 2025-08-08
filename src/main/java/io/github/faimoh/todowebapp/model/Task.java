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
package io.github.faimoh.todowebapp.model;

import java.sql.Timestamp;

import jakarta.persistence.*;

/**
 * Task entity representing todo tasks in the application
 * @author Faisal Ahmed Pasha Mohammed https://github.com/faimoh
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
