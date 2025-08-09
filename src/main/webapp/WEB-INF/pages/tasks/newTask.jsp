<%-- 
    Document   : new
    Created on : 20 Apr 2020, 21:43:27
    Author     : Faisal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ToDo App - Users - Tasks - New Task</title>
    </head>
    <body>
        <h1>Create New Task</h1>
        <p>Hello! ${sessionScope.account.firstName}</p>
        
        <!-- Navigation -->
        <div style="border: 1px solid #4CAF50; padding: 10px; margin-bottom: 10px;">
            <h3>Navigation</h3>
            <a href="<c:url value="/tasks/new"/>">New Task</a> |
            <a href="<c:url value="/tasks/dashboard"/>">Dashboard</a> |
            <a href="<c:url value="/users/profile"/>">My Profile</a> |
            <a href="<c:url value="/logout"/>">Logout</a>
        </div>
        
        <br>
        
        <!-- Create Task Form -->
        <div style="border: 1px solid #4CAF50; padding: 10px; margin-bottom: 10px;">
            <h3>Create New Task</h3>
            <form method="POST" action="${pageContext.servletContext.contextPath}/tasks/create">
                <fieldset>
                    <legend>Task Details</legend>
                    <input type="hidden" name="accountID" value="${sessionScope.account.accountID}">
                    <label for="details2">What do you want to accomplish?</label>
                    <br><br>
                    <textarea name="details" id="details2" rows="4" columns="64" placeholder="256 characters max." maxlength="256" required></textarea>
                    <br><br> 
                    <label for="date2">By what date and time?</label>            
                    <jsp:useBean id="tomorrow2" class="java.util.Date" scope="page" /> 
                    <jsp:setProperty name="tomorrow2" property="time" value="${tomorrow2.time + 86400000}"/> 
                    <input name="date" id="date2" type="date" 
                           value="<fmt:formatDate value="${tomorrow2}" pattern="yyyy-MM-dd"/>" 
                           min="<fmt:formatDate value="${tomorrow2}" pattern="yyyy-MM-dd"/>"/>
                    <input name="time" id="time2" type="time" 
                           value="<fmt:formatDate value="${tomorrow2}" pattern="HH:mm:ss"/>"
                           min="<fmt:formatDate value="${tomorrow2}" pattern="HH:mm:ss"/>"/>
                    <br><br>
                    <label for="priority2">Choose priority:</label>
                    <select id="priority2" name="priority" required>                
                        <option value="1">Important & Urgent</option>
                        <option value="2">Important but Not Urgent</option>
                        <option value="3">Not Important but Urgent</option>
                        <option value="4">Not Important & Not Urgent</option>
                    </select>
                    <br><br>            
                    <input type="submit" value="Create Task">
                </fieldset>
            </form>
        </div>
    </body>
</html>
