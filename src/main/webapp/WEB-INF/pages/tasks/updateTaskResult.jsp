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
        
        <!-- Navigation - Traditional Servlet System -->
        <div style="border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;">
            <h3>Traditional Servlet Navigation</h3>
            <a href="<c:url value="/app/tasks/new"/>">New Task</a> |
            <a href="<c:url value="/app/tasks/dashboard"/>">Dashboard</a> |
            <a href="<c:url value="/app/users/profile"/>">My Profile</a> |
            <a href="<c:url value="/app/logout"/>">Logout</a>
        </div>
        
        <!-- Navigation - Spring WebMVC System -->
        <div style="border: 1px solid #4CAF50; padding: 10px; margin-bottom: 10px;">
            <h3>Spring WebMVC Navigation</h3>
            <a href="<c:url value="/spring/tasks/new"/>">New Task (Spring)</a> |
            <a href="<c:url value="/spring/tasks/dashboard"/>">Dashboard (Spring)</a> |
            <a href="<c:url value="/spring/users/profile"/>">My Profile (Spring)</a> |
            <a href="<c:url value="/spring/logout"/>">Logout (Spring)</a>
        </div>
        
        <br>
        <b>${requestScope.message}</b>
        
        <!-- Traditional Servlet Form -->
        <div style="border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;">
            <h3>Update via Traditional Servlet</h3>
            <form method="POST" action="<c:url value="/app/tasks/update"/>">
                <fieldset>
                    <legend>Details</legend>
                    <input type="hidden" name="accountID" value="${requestScope.task.accountID}">
                    <input type="hidden" name="taskID" value="${requestScope.task.taskID}">
                    <input type="hidden" name="createdAt" value="${requestScope.task.createdAt}">            
                    <br>
                    <label for="username">Task ID: </label>${requestScope.task.taskID}
                    <br><br>
                    <label for="lastName">Created At: </label>
                    <br>
                    ${requestScope.task.createdAt}
                    <br><br> 
                    <label for="firstName">What do you want to accomplish?</label>
                    <br>
                    <textarea name="details" rows="4" columns="16">${requestScope.task.details}</textarea>            
                    <br><br>  
                    <label for="date">By what date and time?</label>            
                    <c:choose>
                        <c:when test="${requestScope.task.deadline != null}">                                     
                            <input name="date" type="date" 
                                   value="<fmt:formatDate value="${requestScope.task.deadline}" pattern="yyyy-MM-dd"/>" 
                                   min="<fmt:formatDate value="${requestScope.task.deadline}" pattern="yyyy-MM-dd"/>"/>
                            <input name="time" type="time" 
                                   value="<fmt:formatDate value="${requestScope.task.deadline}" pattern="HH:mm:ss"/>"
                                   min="<fmt:formatDate value="${requestScope.task.deadline}" pattern="HH:mm:ss"/>"/>
                        </c:when>
                        <c:otherwise>
                            <jsp:useBean id="today" class="java.util.Date" scope="page" />            
                            <input name="date" type="date" 
                                   value="<fmt:formatDate value="${today}" pattern="yyyy-MM-dd"/>" 
                                   min="<fmt:formatDate value="${today}" pattern="yyyy-MM-dd"/>"/>
                            <input name="time" type="time" 
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
                    <input type="radio" id="todo1" name="statusID" value="1" ${requestScope.task.statusID==1?'checked':''}>
                    <label for="todo1">To Do</label>
                    <input type="radio" id="progress1" name="statusID" value="2" ${requestScope.task.statusID==2?'checked':''}>
                    <label for="progress1">In Progress</label>
                    <input type="radio" id="done1" name="statusID" value="3" ${requestScope.task.statusID==3?'checked':''}>
                    <label for="done1">Done</label>
                    <br><br>
                    <input type="submit" value="Update (Servlet)">
                </fieldset>
            </form>
        </div>
        
        <!-- Spring WebMVC Form -->
        <div style="border: 1px solid #4CAF50; padding: 10px; margin-bottom: 10px;">
            <h3>Update via Spring WebMVC</h3>
            <form method="POST" action="<c:url value="/spring/tasks/update"/>">
                <fieldset>
                    <legend>Details</legend>
                    <input type="hidden" name="accountID" value="${requestScope.task.accountID}">
                    <input type="hidden" name="taskID" value="${requestScope.task.taskID}">
                    <input type="hidden" name="createdAt" value="${requestScope.task.createdAt}">            
                    <br>
                    <label for="username2">Task ID: </label>${requestScope.task.taskID}
                    <br><br>
                    <label for="lastName2">Created At: </label>
                    <br>
                    ${requestScope.task.createdAt}
                    <br><br> 
                    <label for="firstName2">What do you want to accomplish?</label>
                    <br>
                    <textarea name="details" id="details2" rows="4" columns="16">${requestScope.task.details}</textarea>            
                    <br><br>  
                    <label for="date2">By what date and time?</label>            
                    <c:choose>
                        <c:when test="${requestScope.task.deadline != null}">                                     
                            <input name="date" id="date2" type="date" 
                                   value="<fmt:formatDate value="${requestScope.task.deadline}" pattern="yyyy-MM-dd"/>" 
                                   min="<fmt:formatDate value="${requestScope.task.deadline}" pattern="yyyy-MM-dd"/>"/>
                            <input name="time" id="time2" type="time" 
                                   value="<fmt:formatDate value="${requestScope.task.deadline}" pattern="HH:mm:ss"/>"
                                   min="<fmt:formatDate value="${requestScope.task.deadline}" pattern="HH:mm:ss"/>"/>
                        </c:when>
                        <c:otherwise>
                            <jsp:useBean id="today2" class="java.util.Date" scope="page" />            
                            <input name="date" id="date3" type="date" 
                                   value="<fmt:formatDate value="${today2}" pattern="yyyy-MM-dd"/>" 
                                   min="<fmt:formatDate value="${today2}" pattern="yyyy-MM-dd"/>"/>
                            <input name="time" id="time3" type="time" 
                                   value="<fmt:formatDate value="${today2}" pattern="HH:mm:ss"/>"
                                   min="<fmt:formatDate value="${today2}" pattern="HH:mm:ss"/>"/>
                        </c:otherwise>
                    </c:choose>
                    <br><br>
                    <label for="priorityID2">Task priority:</label>
                    <select id="priorityID2" name="priorityID" required>                
                        <option value="1" ${requestScope.task.priorityID==1?'selected':''}>Important & Urgent</option>
                        <option value="2" ${requestScope.task.priorityID==2?'selected':''}>Important but Not Urgent</option>
                        <option value="3" ${requestScope.task.priorityID==3?'selected':''}>Not Important but Urgent</option>
                        <option value="4" ${requestScope.task.priorityID==4?'selected':''}>Not Important & Not Urgent</option>
                    </select>
                    <br><br>
                    <label for="status2">Task status:</label>
                    <input type="radio" id="todo2" name="statusID" value="1" ${requestScope.task.statusID==1?'checked':''}>
                    <label for="todo2">To Do</label>
                    <input type="radio" id="progress2" name="statusID" value="2" ${requestScope.task.statusID==2?'checked':''}>
                    <label for="progress2">In Progress</label>
                    <input type="radio" id="done2" name="statusID" value="3" ${requestScope.task.statusID==3?'checked':''}>
                    <label for="done2">Done</label>
                    <br><br>
                    <input type="submit" value="Update (Spring)">
                </fieldset>
            </form>
        </div>
    </body>
</html>
