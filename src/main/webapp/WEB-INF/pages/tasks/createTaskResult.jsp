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
        <br><br>
        
        <!-- Traditional Servlet Form -->
        <div style="border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;">
            <h3>Create via Traditional Servlet</h3>
            <form method="POST" action="<c:url value="/app/tasks/create"/>">
                <fieldset>
                    <legend>Task Details</legend>
                    <input type="hidden" name="accountID" value="${sessionScope.account.accountID}">
                    <label for="details">What do you want to accomplish?</label>
                    <br><br>
                    <textarea name="details" rows="4" columns="64" placeholder="256 characters max." maxlength="256"></textarea>
                    <br><br>  
                    <label for="date">By what date and time?</label>            
                    <jsp:useBean id="tomorrow" class="java.util.Date" scope="page" /> 
                    <jsp:setProperty name="tomorrow" property="time" value="${tomorrow.time + 86400000}"/> 
                    <input name="date" type="date" 
                           value="<fmt:formatDate value="${tomorrow}" pattern="yyyy-MM-dd"/>" 
                    min="<fmt:formatDate value="${tomorrow}" pattern="yyyy-MM-dd"/>"/>
                    <input name="time" type="time" 
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
                    <input type="submit" value="Create (Servlet)">
                </fieldset>
            </form>
        </div>
        
        <!-- Spring WebMVC Form -->
        <div style="border: 1px solid #4CAF50; padding: 10px; margin-bottom: 10px;">
            <h3>Create via Spring WebMVC</h3>
            <form method="POST" action="<c:url value="/spring/tasks/create"/>">
                <fieldset>
                    <legend>Task Details</legend>
                    <input type="hidden" name="accountID" value="${sessionScope.account.accountID}">
                    <label for="details2">What do you want to accomplish?</label>
                    <br><br>
                    <textarea name="details" id="details2" rows="4" columns="64" placeholder="256 characters max." maxlength="256"></textarea>
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
                        <option value="">None</option>
                        <option value="1">Important & Urgent</option>
                        <option value="2">Important but Not Urgent</option>
                        <option value="3">Not Important but Urgent</option>
                        <option value="4">Not Important & Not Urgent</option>
                    </select>
                    <br><br>
                    <input type="submit" value="Create (Spring)">
                </fieldset>
            </form>
        </div>
    </body>
</html>
