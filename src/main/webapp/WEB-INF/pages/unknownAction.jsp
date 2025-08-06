<%-- 
    Document   : UnknownAction
    Created on : 28 Apr 2020, 09:23:25
    Author     : Faisal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Unknown request! :(</h1>
        <p>Hello! ${sessionScope.account.firstName}</p>
        
        <c:choose>
            <c:when test="${sessionScope.account.accountID==1}">                
                <!-- Admin Navigation - Traditional Servlet System -->
                <div style="border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;">
                    <h3>Traditional Servlet Navigation</h3>
                    <a href="<c:url value="/app/admin/accounts/dashboard"/>">Dashboard</a> |
                    <a href="<c:url value="/app/admin/accounts/new"/>">New Account</a> |
                    <a href="<c:url value="/app/users/profile"/>">My Profile</a> |
                    <a href="<c:url value="/app/logout"/>">Logout</a>
                </div>
                
                <!-- Admin Navigation - Spring WebMVC System -->
                <div style="border: 1px solid #4CAF50; padding: 10px; margin-bottom: 10px;">
                    <h3>Spring WebMVC Navigation</h3>
                    <a href="<c:url value="/spring/admin/accounts/dashboard"/>">Dashboard (Spring)</a> |
                    <a href="<c:url value="/spring/admin/accounts/new"/>">New Account (Spring)</a> |
                    <a href="<c:url value="/spring/users/profile"/>">My Profile (Spring)</a> |
                    <a href="<c:url value="/spring/logout"/>">Logout (Spring)</a>
                </div>
            </c:when>
            <c:otherwise>
                <!-- User Navigation - Traditional Servlet System -->
                <div style="border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;">
                    <h3>Traditional Servlet Navigation</h3>
                    <a href="<c:url value="/app/tasks/dashboard"/>">Dashboard</a> |
                    <a href="<c:url value="/app/tasks/new"/>">New Task</a> |
                    <a href="<c:url value="/app/users/profile"/>">My Profile</a> |
                    <a href="<c:url value="/app/logout"/>">Logout</a>
                </div>
                
                <!-- User Navigation - Spring WebMVC System -->
                <div style="border: 1px solid #4CAF50; padding: 10px; margin-bottom: 10px;">
                    <h3>Spring WebMVC Navigation</h3>
                    <a href="<c:url value="/spring/tasks/dashboard"/>">Dashboard (Spring)</a> |
                    <a href="<c:url value="/spring/tasks/new"/>">New Task (Spring)</a> |
                    <a href="<c:url value="/spring/users/profile"/>">My Profile (Spring)</a> |
                    <a href="<c:url value="/spring/logout"/>">Logout (Spring)</a>
                </div>
            </c:otherwise>
        </c:choose>        
        <p>${requestScope.message}</p>
    </body>
</html>
