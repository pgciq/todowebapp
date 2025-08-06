<%-- 
    Document   : login
    Created on : 20 Apr 2020, 21:53:25
    Author     : Faisal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ToDoApp - Login</title>        
    </head>
    <body>
        <br><br>
        
        <!-- Traditional Servlet Login -->
        <div style="border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;">
            <h3>Login via Traditional Servlet</h3>
            <form method="POST" action="<c:url value="/app/login"/>">
                <fieldset>
                    <legend>Login</legend>
                    <c:choose>
                        <c:when test="${empty requestScope.message}">
                            ${""}
                        </c:when>
                        <c:otherwise>
                            <font color="red"><b>${requestScope.message}</b></font>
                            <br><br>
                        </c:otherwise>
                    </c:choose>
                    <label for="username">Username:</label>
                    <br>
                    <input name="username" type="text" required/>
                    <br><br>
                    <label for="password">Password:</label>
                    <br>
                    <input name="password" type="password" required/>
                    <br><br>
                    <input name="login" type="submit" value="Login (Servlet)"/>
                </fieldset>
            </form>
        </div>
        
        <!-- Spring WebMVC Login -->
        <div style="border: 1px solid #4CAF50; padding: 10px; margin-bottom: 10px;">
            <h3>Login via Spring WebMVC</h3>
            <form method="POST" action="<c:url value="/spring/login"/>">
                <fieldset>
                    <legend>Login</legend>
                    <c:choose>
                        <c:when test="${empty requestScope.message}">
                            ${""}
                        </c:when>
                        <c:otherwise>
                            <font color="red"><b>${requestScope.message}</b></font>
                            <br><br>
                        </c:otherwise>
                    </c:choose>
                    <label for="username2">Username:</label>
                    <br>
                    <input name="username" id="username2" type="text" required/>
                    <br><br>
                    <label for="password2">Password:</label>
                    <br>
                    <input name="password" id="password2" type="password" required/>
                    <br><br>
                    <input name="login" type="submit" value="Login (Spring)"/>
                </fieldset>
            </form>
        </div>
    </body>
</html>
