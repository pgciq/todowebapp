<%-- 
    Document   : home
    Created on : Spring WebMVC Demo
    Author     : Faisal Ahmed Pasha Mohammed
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Todo Web App - Spring WebMVC Demo</title>
        <style>
            body { font-family: Arial, sans-serif; margin: 40px; }
            .container { max-width: 800px; margin: 0 auto; }
            .section { margin: 20px 0; padding: 20px; border: 1px solid #ddd; border-radius: 5px; }
            .old-system { background-color: #fff3cd; }
            .new-system { background-color: #d1ecf1; }
            .nav-links a { display: inline-block; margin: 10px; padding: 10px 20px; 
                          background-color: #007bff; color: white; text-decoration: none; 
                          border-radius: 3px; }
            .nav-links a:hover { background-color: #0056b3; }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Todo Web Application</h1>
            <h2>Spring WebMVC Application</h2>
            
            <p><strong>${message}</strong></p>
            
            <div class="section new-system">
                <h3>Spring WebMVC System</h3>
                <p>Modern web application using Spring WebMVC controllers with annotation-based configuration.</p>
                <div class="nav-links">
                    <a href="<c:url value='/spring/login'/>">Application Login</a>
                    <a href="<c:url value='/spring/demo'/>">Technical Demo</a>
                </div>
                <p><em>Note: Admin users (accountID=1) will be redirected to the admin dashboard after login.</em></p>
            </div>
            
            <div class="section">
                <h3>Application Features</h3>
                <ul>
                    <li><strong>Modern Architecture:</strong> Built with Spring WebMVC framework</li>
                    <li><strong>Annotation-Based:</strong> Uses @Controller, @RequestMapping annotations</li>
                    <li><strong>Dependency Injection:</strong> Spring manages components and dependencies</li>
                    <li><strong>Java Configuration:</strong> Type-safe configuration without XML</li>
                    <li><strong>Task Management:</strong> Complete CRUD operations for tasks</li>
                    <li><strong>User Management:</strong> Admin interface for user account management</li>
                    <li><strong>Authentication:</strong> Session-based security</li>
                    <li><strong>Database Flexibility:</strong> H2 (development) and MySQL (production) support</li>
                </ul>
            </div>
        </div>
    </body>
</html>
