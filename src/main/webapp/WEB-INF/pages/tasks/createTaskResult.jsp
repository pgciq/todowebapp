<%-- 
    Document   : newResult
    Created on : 4 May 2020, 11:51:44
    Author     : Faisal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Create New Task</h1>
        <p>Hello! ${sessionScope.account.firstName}</p>
        
        <!-- Navigation -->
        <div style="border: 1px solid #4CAF50; padding: 10px; margin-bottom: 10px;">
            <a href="<c:url value="/tasks/new"/>">New Task</a> |
            <a href="<c:url value="/tasks/dashboard"/>">Dashboard</a> |
            <a href="<c:url value="/users/profile"/>">My Profile</a> |
            <a href="<c:url value="/logout"/>">Logout</a>
        </div>
        
        <br>
        <b>${requestScope.message}</b>
        <br><br>
        
        <!-- Create Task Form -->
        <div style="border: 1px solid #4CAF50; padding: 10px; margin-bottom: 10px;">
            <h3>Create New Task</h3>
            <form method="POST" action="<c:url value="/tasks/create"/>">
                <fieldset>
                    <legend>Task Details</legend>
                    <input type="hidden" name="accountID" value="${sessionScope.account.accountID}">
                    <label for="details">What do you want to accomplish?</label>
                    <br><br>
                    <textarea name="details" id="details" rows="4" columns="64" placeholder="256 characters max." maxlength="256"></textarea>
                    <br><br>  
                    <label for="date">By what date and time?</label>            
                    <jsp:useBean id="tomorrow" class="java.util.Date" scope="page" /> 
                    <jsp:setProperty name="tomorrow" property="time" value="${tomorrow.time + 86400000}"/> 
                    <input name="date" id="date" type="date" 
                           value="<fmt:formatDate value="${tomorrow}" pattern="yyyy-MM-dd"/>" 
                    min="<fmt:formatDate value="${tomorrow}" pattern="yyyy-MM-dd"/>"/>
                    <input name="time" id="time" type="time" 
                           value="<fmt:formatDate value="${tomorrow}" pattern="HH:mm:ss"/>"
                    min="<fmt:formatDate value="${tomorrow}" pattern="HH:mm:ss"/>"/>
                    <br><br>
                    <label for="priority">Choose priority:</label>
                    <select id="priority" name="priority" required>
                        <option value="">None</option>
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
