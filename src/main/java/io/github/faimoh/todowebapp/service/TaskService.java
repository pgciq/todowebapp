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

import io.github.faimoh.todowebapp.model.Account;
import io.github.faimoh.todowebapp.model.Task;
import io.github.faimoh.todowebapp.repository.TaskRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for Task operations
 * Replaces direct DAO usage in controllers with service layer pattern
 * 
 * @author Faisal Ahmed Pasha Mohammed https://github.com/faimoh
 */
@Service
@Transactional
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    /**
     * Find task by ID
     * @param taskID the task ID to search for
     * @return Task the task if found, null otherwise
     */
    public Task findTask(Integer taskID) {
        Optional<Task> task = taskRepository.findByTaskID(taskID);
        return task.orElse(null);
    }
    
    /**
     * Get all tasks for a specific account
     * @param account the account to get tasks for
     * @return List<Task> list of tasks for the account
     */
    public List<Task> getAllTasks(Account account) {
        return taskRepository.findByAccountIDOrderByCreatedAtDesc(account.getAccountID());
    }
    
    /**
     * Get tasks by account and status
     * @param account the account
     * @param statusID the status ID
     * @return List<Task> list of tasks matching criteria
     */
    public List<Task> getTasksByStatus(Account account, Integer statusID) {
        return taskRepository.findByAccountIDAndStatusIDOrderByCreatedAtDesc(account.getAccountID(), statusID);
    }
    
    /**
     * Get tasks by account and priority
     * @param account the account
     * @param priorityID the priority ID
     * @return List<Task> list of tasks matching criteria
     */
    public List<Task> getTasksByPriority(Account account, Integer priorityID) {
        return taskRepository.findByAccountIDAndPriorityIDOrderByCreatedAtDesc(account.getAccountID(), priorityID);
    }
    
    /**
     * Create a new task
     * @param task the task to create
     * @return Boolean true if successful, false otherwise
     */
    public Boolean insertTask(Task task) {
        try {
            // Set created timestamp
            task.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            task.setLastUpdated(new Timestamp(System.currentTimeMillis()));
            
            // Set default status and priority if not provided
            if (task.getStatusID() == null) {
                task.setStatusID(1); // Default status: pending
            }
            if (task.getPriorityID() == null) {
                task.setPriorityID(2); // Default priority: normal
            }
            
            taskRepository.save(task);
            return true;
        } catch (Exception e) {
            System.err.println("Error creating task: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update an existing task
     * @param task the task to update
     * @return Boolean true if successful, false otherwise
     */
    public Boolean updateTask(Task task) {
        try {
            if (task.getTaskID() == null) {
                return false;
            }
            
            // Update last modified timestamp
            task.setLastUpdated(new Timestamp(System.currentTimeMillis()));
            
            int rowsAffected = taskRepository.updateTaskDetails(
                task.getTaskID(),
                task.getDetails(),
                task.getStatusID(),
                task.getPriorityID()
            );
            
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Error updating task: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete a task
     * @param taskID the task ID to delete
     * @return Boolean true if successful, false otherwise
     */
    public Boolean deleteTask(Integer taskID) {
        try {
            if (taskID == null) {
                return false;
            }
            
            taskRepository.deleteById(taskID);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting task: " + e.getMessage());
            return false;
        }
    }
}
