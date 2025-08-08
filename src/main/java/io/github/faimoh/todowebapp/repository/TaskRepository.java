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

import io.github.faimoh.todowebapp.model.Task;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Task entity
 * Replaces the TaskDAO interface with Spring Data JPA functionality
 * 
 * @author Faisal Ahmed Pasha Mohammed https://github.com/faimoh
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    
    /**
     * Find task by ID
     * @param taskID the task ID to search for
     * @return Optional<Task> the task if found
     */
    Optional<Task> findByTaskID(Integer taskID);
    
    /**
     * Find all tasks for a specific account
     * @param accountID the account ID
     * @return List<Task> list of tasks for the account
     */
    List<Task> findByAccountIDOrderByCreatedAtDesc(Integer accountID);
    
    /**
     * Find tasks by account ID and status
     * @param accountID the account ID
     * @param statusID the status ID
     * @return List<Task> list of tasks matching criteria
     */
    List<Task> findByAccountIDAndStatusIDOrderByCreatedAtDesc(Integer accountID, Integer statusID);
    
    /**
     * Find tasks by account ID and priority
     * @param accountID the account ID
     * @param priorityID the priority ID
     * @return List<Task> list of tasks matching criteria
     */
    List<Task> findByAccountIDAndPriorityIDOrderByCreatedAtDesc(Integer accountID, Integer priorityID);
    
    /**
     * Update task details
     * @param taskID the task ID
     * @param details the new details
     * @param statusID the status ID
     * @param priorityID the priority ID
     * @return int number of rows affected
     */
    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.details = :details, t.statusID = :statusID, t.priorityID = :priorityID WHERE t.taskID = :taskID")
    int updateTaskDetails(@Param("taskID") Integer taskID,
                         @Param("details") String details,
                         @Param("statusID") Integer statusID,
                         @Param("priorityID") Integer priorityID);
}
