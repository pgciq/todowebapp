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
