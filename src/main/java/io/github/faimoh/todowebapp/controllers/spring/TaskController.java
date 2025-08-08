package io.github.faimoh.todowebapp.controllers.spring;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.faimoh.todowebapp.service.TaskService;
import io.github.faimoh.todowebapp.model.Account;
import io.github.faimoh.todowebapp.model.Task;
import io.github.faimoh.todowebapp.util.Utilities;
import jakarta.servlet.http.HttpSession;

/**
 * Spring MVC Controller for Task Management
 * This controller handles all task-related operations using Spring WebMVC
 * Updated to use Spring Data JPA Service layer instead of DAO pattern
 */
@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * Display tasks dashboard
     * Equivalent to UserTasksDashboardAction
     * Updated to use TaskService instead of DAO pattern
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        try {
            Account sessionUser = (Account) session.getAttribute("account");
            if (sessionUser == null) {
                return "redirect:/login";
            }

            List<Task> tasksList = taskService.getAllTasks(sessionUser);

            model.addAttribute("tasksList", tasksList);
            return "tasks/dashboard";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error loading tasks: " + e.getMessage());
            return "tasks/dashboard";
        }
    }

    /**
     * Show new task form
     * Equivalent to UserNewTaskFormAction
     */
    @GetMapping("/new")
    public String newTaskForm(Model model, HttpSession session) {
        Account sessionUser = (Account) session.getAttribute("account");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        return "tasks/newTask";
    }

    /**
     * Create a new task
     * Equivalent to UserCreateTaskAction
     */
    @PostMapping("/create")
    public String createTask(@RequestParam String details,
                           @RequestParam String date,
                           @RequestParam String time,
                           @RequestParam int priority,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        try {
            Account sessionUser = (Account) session.getAttribute("account");
            if (sessionUser == null) {
                return "redirect:/login";
            }

            // Parse date and time using Utilities helper
            Timestamp deadline = Utilities.parseDateAndTime(date, time);
            if (deadline == null) {
                redirectAttributes.addFlashAttribute("message", "Invalid date/time format.");
                return "redirect:/tasks/new";
            }

            // Create task
            Task task = new Task();
            task.setAccountID(sessionUser.getAccountID());
            task.setDetails(details);
            task.setDeadline(deadline);
            task.setPriorityID(priority);
            task.setStatusID(1); // Default status: Pending

            boolean isTaskCreated = taskService.insertTask(task);

            if (isTaskCreated) {
                redirectAttributes.addFlashAttribute("message", "Task created successfully!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Technical error. Please try again later.");
            }
            
            return "redirect:/tasks/dashboard";
        } catch (Exception e) {
            System.err.println("Error creating task: " + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Error creating task: " + e.getMessage());
            return "redirect:/tasks/new";
        }
    }

    /**
     * Show task details
     * Equivalent to UserReadTaskDetailsAction
     */
    @GetMapping("/details")
    public String taskDetails(@RequestParam int id, Model model, HttpSession session) {
        try {
            Account sessionUser = (Account) session.getAttribute("account");
            if (sessionUser == null) {
                return "redirect:/login";
            }

            Task task = taskService.findTask(id);

            if (task == null) {
                model.addAttribute("message", "No such task exists.");
                return "tasks/dashboard";
            } else if (sessionUser.getAccountID().intValue() != task.getAccountID().intValue()) {
                model.addAttribute("message", "Forbidden. You are not allowed to see others' task.");
                return "tasks/dashboard";
            } else {
                model.addAttribute("task", task);
                return "tasks/taskDetails";
            }
        } catch (Exception e) {
            System.err.println("Error loading task details: " + e.getMessage());
            model.addAttribute("message", "Error loading task details: " + e.getMessage());
            return "tasks/dashboard";
        }
    }

    /**
     * Update a task
     * Equivalent to UserUpdateTaskAction
     */
    @PostMapping("/update")
    public String updateTask(@RequestParam int taskID,
                           @RequestParam String details,
                           @RequestParam String date,
                           @RequestParam String time,
                           @RequestParam int priorityID,
                           @RequestParam(required = false) Integer statusID,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        try {
            Account sessionUser = (Account) session.getAttribute("account");
            if (sessionUser == null) {
                return "redirect:/login";
            }

            // Parse date and time using Utilities helper
            Timestamp deadline = Utilities.parseDateAndTime(date, time);
            if (deadline == null) {
                redirectAttributes.addFlashAttribute("message", "Invalid date/time format.");
                return "redirect:/tasks/details?id=" + taskID;
            }

            // Get and update task
            Task task = taskService.findTask(taskID);
            
            if (task == null) {
                redirectAttributes.addFlashAttribute("message", "Task not found.");
                return "redirect:/tasks/dashboard";
            } else if (sessionUser.getAccountID().intValue() != task.getAccountID().intValue()) {
                redirectAttributes.addFlashAttribute("message", "Forbidden. You are not allowed to modify others' task.");
                return "redirect:/tasks/dashboard";
            } else {
                task.setDetails(details);
                task.setDeadline(deadline);
                task.setPriorityID(priorityID);
                if (statusID != null) {
                    task.setStatusID(statusID);
                }
                
                boolean isTaskUpdated = taskService.updateTask(task);
                if (isTaskUpdated) {
                    redirectAttributes.addFlashAttribute("message", "Task updated successfully!");
                } else {
                    redirectAttributes.addFlashAttribute("message", "Technical error. Please try again later.");
                }
            }

            return "redirect:/tasks/dashboard";
        } catch (Exception e) {
            System.err.println("Error updating task: " + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Error updating task: " + e.getMessage());
            return "redirect:/tasks/details?id=" + taskID;
        }
    }
}
