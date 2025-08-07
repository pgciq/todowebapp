<%-- 
    Document   : details
    Created on : 20 Apr 2020, 21:43:38
    Author     : Faisal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ToDoApp - Account Details</title>        
    </head>
    <body>
        <h1>Account Information</h1>
        <p>Hello! ${sessionScope.account.firstName}</p>
        
        <!-- Navigation - Spring WebMVC System -->
        <div style="border: 1px solid #4CAF50; padding: 10px; margin-bottom: 10px;">
            <h3>Navigation</h3>
            <a href="<c:url value="/spring/admin/accounts/dashboard"/>">Dashboard</a> |
            <a href="<c:url value="/spring/admin/accounts/new"/>">New Account</a> |
            <a href="<c:url value="/spring/users/profile"/>">My Profile</a> |
            <a href="<c:url value="/spring/logout"/>">Logout</a>
        </div>
        
        <br>
        <b>${requestScope.message}</b>
        <br><br>
        
        <!-- Account Details Form -->
                    <br><br>            
                    <label for="username">Username: </label>${requestScope.account.username}
        <!-- Account Details Form -->
        <div style="border: 1px solid #4CAF50; padding: 10px; margin-bottom: 10px;">
            <h3>Update Account Details</h3>
            <form method="POST" action="<c:url value="/spring/admin/accounts/update"/>">
                <fieldset>
                    <legend>Details</legend>
                    <input type="hidden" name="accountID" value="${requestScope.account.accountID}">
                    <input type="hidden" name="username" value="${requestScope.account.username}">
                    <label for="accountID2">Account ID: </label>${requestScope.account.accountID}
                    <br><br>            
                    <label for="username2">Username: </label>${requestScope.account.username}
                    <br><br>            
                    <label for="firstName2">First name:</label>
                    <input type="text" id="firstName2" name="firstName" value="${requestScope.account.firstName}" required><br><br>
                    <label for="lastName2">Last name:</label>
                    <input type="text" id="lastName2" name="lastName" value="${requestScope.account.lastName}" required><br><br>
                    <label for="resetPassword2">Reset password to default:</label>
                    <input type="checkbox" id="resetPassword2" name="resetPassword" value="true"><br><br>
                    <label for="status2">Account status:</label>
                    <input type="radio" id="enabled2" name="status" value="1" ${requestScope.account.statusID==1?'checked':''}>
                    <label for="enabled2">Enabled</label>
                    <input type="radio" ${requestScope.account.accountID==1?'disabled':''} id="disabled2" name="status" value="2" ${requestScope.account.statusID==2?'checked':''}>
                    <label for="disabled2">Disabled</label><br><br>
                    <input type="submit" value="Update Account">
                </fieldset>
            </form>
        </div>
    </body>
</html>
