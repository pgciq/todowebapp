<%-- 
    Document   : profile
    Created on : 3 May 2020, 22:01:53
    Author     : Faisal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ToDoApp - User Profile</title>
    </head>
    <body>
        <h1>Your profile</h1>
        <p>Hello! ${sessionScope.account.firstName}</p> 
        
        <!-- Navigation -->
        <div style="border: 1px solid #4CAF50; padding: 10px; margin-bottom: 10px;">
            <h3>Navigation</h3>
            <a href="<c:url value="${sessionScope.account.accountID==1?'/admin/accounts/dashboard':'/tasks/dashboard'}"/>">Dashboard</a> |
            <a href="<c:url value="${sessionScope.account.accountID==1?'/admin/accounts/new':'/tasks/new'}"/>">
                ${sessionScope.account.accountID==1?'New Account':'New Task'}</a> |               
            <a href="<c:url value="/users/profile"/>">My Profile</a> |
            <a href="<c:url value="/logout"/>">Logout</a>
        </div>
        
        <br>
        <b>${requestScope.message}</b><br>
        
        <!-- Profile Update Form -->
        <div style="border: 1px solid #4CAF50; padding: 10px; margin-bottom: 10px;">
            <h3>Update Profile</h3>
            <form method="POST" action="<c:url value="/users/update"/>">
                <fieldset>
                    <legend>Profile details</legend>
                    <label for="accountID2">Account ID: </label>${sessionScope.account.accountID}<br><br>
                    <label for="username2">Username: </label>${sessionScope.account.username}<br><br>
                    <label>Created at: </label>${sessionScope.account.createdAt}<br><br>
                    <label>Last login at: </label>${sessionScope.accountPreviousSession.sessionCreated}<br><br>
                    <label for="firstName2">First name: </label>
                    <input type="text" id="firstName2" name="firstName" value="${sessionScope.account.firstName}" required><br><br>
                    <label for="lastName2">Last name: </label>
                    <input type="text" id="lastName2" name="lastName" value="${sessionScope.account.lastName}" required><br><br>
                    <label for="password2">New password: </label>
                    <input type="password" id="password2" name="password" value=""><br><br>
                    <input type="hidden" name="accountID" value="${requestScope.account.accountID}">
                    <input type="hidden" name="username" value="${requestScope.account.username}">
                    <input type="submit" value="Update Profile">
                </fieldset>
            </form>
        </div>
        <br><br>
        <p>If you face trouble signing in to your account after changing your password, ask administrator for help.<br>
            Your administrator can help reset your password.
        </p>

    </body>
</html>
