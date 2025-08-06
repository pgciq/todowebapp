<%-- 
    Document   : new
    Created on : 20 Apr 2020, 21:43:27
    Author     : Faisal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ToDoApp - Create New Account</title>
    </head>
    <body>
        <h1>Create New Account</h1>
        <p>Hello! ${sessionScope.account.firstName}</p>
        
        <!-- Navigation - Traditional Servlet System -->
        <div style="border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;">
            <h3>Traditional Servlet Navigation</h3>
            <a href="<c:url value="/app/admin/accounts/dashboard"/>">Dashboard</a> |
            <a href="<c:url value="/app/admin/accounts/new"/>">New Account</a> |
            <a href="<c:url value="/app/users/profile"/>">My Profile</a> |
            <a href="<c:url value="/app/logout"/>">Logout</a>
        </div>
        
        <!-- Navigation - Spring WebMVC System -->
        <div style="border: 1px solid #4CAF50; padding: 10px; margin-bottom: 10px;">
            <h3>Spring WebMVC Navigation</h3>
            <a href="<c:url value="/spring/admin/accounts/dashboard"/>">Dashboard (Spring)</a> |
            <a href="<c:url value="/spring/admin/accounts/new"/>">New Account (Spring)</a> |
            <a href="<c:url value="/spring/users/profile"/>">My Profile (Spring)</a> |
            <a href="<c:url value="/spring/logout"/>">Logout (Spring)</a>
        </div>

        <br><br>
        
        <!-- Traditional Servlet Form -->
        <div style="border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;">
            <h3>Create via Traditional Servlet</h3>
            <form method="POST" action="<c:url value="/app/admin/accounts/create"/>">
                <fieldset>
                    <legend>New account details</legend>
                    All form fields are required.<br><br>
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required><br><br>
                    <label for="firstName">First name:</label>
                    <input type="text" id="firstName" name="firstName" required><br><br>
                    <label for="lastName">Last name:</label>
                    <input type="text" id="lastName" name="lastName" required><br><br>
                    <input type="submit" value="Create (Servlet)">
                </fieldset>
            </form>
        </div>
        
        <!-- Spring WebMVC Form -->
        <div style="border: 1px solid #4CAF50; padding: 10px; margin-bottom: 10px;">
            <h3>Create via Spring WebMVC</h3>
            <form method="POST" action="<c:url value="/spring/admin/accounts/create"/>">
                <fieldset>
                    <legend>New account details</legend>
                    All form fields are required.<br><br>
                    <label for="username2">Username:</label>
                    <input type="text" id="username2" name="username" required><br><br>
                    <label for="firstName2">First name:</label>
                    <input type="text" id="firstName2" name="firstName" required><br><br>
                    <label for="lastName2">Last name:</label>
                    <input type="text" id="lastName2" name="lastName" required><br><br>
                    <input type="submit" value="Create (Spring)">
                </fieldset>
            </form>
        </div>
    </body>
</html>
