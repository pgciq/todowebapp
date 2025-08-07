<%-- 
    Document   : updateTaskResult
    Created on : 5 May 2020, 06:31:48
    Author     : Faisal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ToDoApp - Update Task</title>
    </head>
    <body>
        <h1>Task Details</h1>
        <p>Hello! ${sessionScope.account.firstName}</p>
        
        <!-- Navigation -->
        <div style="border: 1px solid #4CAF50; padding: 10px; margin-bottom: 10px;">
            <a href="<c:url value="/spring/tasks/new"/>">New Task</a> |
            <a href="<c:url value="/spring/tasks/dashboard"/>">Dashboard</a> |
            <a href="<c:url value="/spring/users/profile"/>">My Profile</a> |
            <a href="<c:url value="/spring/logout"/>">Logout</a>
        </div>
        
        <br>
        <b>${requestScope.message}</b>
        
        <!-- Update Task Form -->
        <div style="border: 1px solid #4CAF50; padding: 10px; margin-bottom: 10px;">
            <h3>Update Task</h3>
            <form method="POST" action="<c:url value="/spring/tasks/update"/>">
                <fieldset>
                    <legend>Details</legend>
                    <input type="hidden" name="accountID" value="${requestScope.task.accountID}">
                    <input type="hidden" name="taskID" value="${requestScope.task.taskID}">
                    <input type="hidden" name="createdAt" value="${requestScope.task.createdAt}">            
                    <br>
                    <label>Task ID: </label>${requestScope.task.taskID}
                    <br><br>
                    <label>Created At: </label>
                    <br>
                    ${requestScope.task.createdAt}
                    <br><br> 
                    <label for="details">What do you want to accomplish?</label>
                    <br>
                    <textarea name="details" id="details" rows="4" columns="16">${requestScope.task.details}</textarea>            
                    <br><br>  
                    <label for="date">By what date and time?</label>            
                    <c:choose>
                        <c:when test="${requestScope.task.deadline != null}">                                     
                            <input name="date" id="date" type="date" 
                                   value="<fmt:formatDate value="${requestScope.task.deadline}" pattern="yyyy-MM-dd"/>" 
                                   min="<fmt:formatDate value="${requestScope.task.deadline}" pattern="yyyy-MM-dd"/>"/>
                            <input name="time" id="time" type="time" 
                                   value="<fmt:formatDate value="${requestScope.task.deadline}" pattern="HH:mm:ss"/>"
                                   min="<fmt:formatDate value="${requestScope.task.deadline}" pattern="HH:mm:ss"/>"/>
                        </c:when>
                        <c:otherwise>
                            <jsp:useBean id="today" class="java.util.Date" scope="page" />            
                            <input name="date" id="date2" type="date" 
                                   value="<fmt:formatDate value="${today}" pattern="yyyy-MM-dd"/>" 
                                   min="<fmt:formatDate value="${today}" pattern="yyyy-MM-dd"/>"/>
                            <input name="time" id="time2" type="time" 
                                   value="<fmt:formatDate value="${today}" pattern="HH:mm:ss"/>"
                                   min="<fmt:formatDate value="${today}" pattern="HH:mm:ss"/>"/>
                        </c:otherwise>
                    </c:choose>
                    <br><br>
                    <label for="priorityID">Task priority:</label>
                    <select id="priorityID" name="priorityID" required>                
                        <option value="1" ${requestScope.task.priorityID==1?'selected':''}>Important & Urgent</option>
                        <option value="2" ${requestScope.task.priorityID==2?'selected':''}>Important but Not Urgent</option>
                        <option value="3" ${requestScope.task.priorityID==3?'selected':''}>Not Important but Urgent</option>
                        <option value="4" ${requestScope.task.priorityID==4?'selected':''}>Not Important & Not Urgent</option>
                    </select>
                    <br><br>
                    <label for="status">Task status:</label>
                    <input type="radio" id="todo" name="statusID" value="1" ${requestScope.task.statusID==1?'checked':''}>
                    <label for="todo">To Do</label>
                    <input type="radio" id="progress" name="statusID" value="2" ${requestScope.task.statusID==2?'checked':''}>
                    <label for="progress">In Progress</label>
                    <input type="radio" id="done" name="statusID" value="3" ${requestScope.task.statusID==3?'checked':''}>
                    <label for="done">Done</label>
                    <br><br>
                    <input type="submit" value="Update Task">
                </fieldset>
            </form>
        </div>
    </body>
</html>
