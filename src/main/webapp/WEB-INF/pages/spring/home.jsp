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
            <h2>Spring WebMVC Integration Demo</h2>
            
            <p><strong>${message}</strong></p>
            
            <div class="section old-system">
                <h3>Traditional Servlet-Based System (Original)</h3>
                <p>The original system uses custom servlets, actions, and a front controller pattern.</p>
                <div class="nav-links">
                    <a href="<c:url value='/WEB-INF/pages/login.jsp'/>">Traditional Login (Direct JSP)</a>
                    <a href="<c:url value='/app/login'/>">Traditional Login (via Servlet)</a>
                </div>
            </div>
            
            <div class="section new-system">
                <h3>Spring WebMVC System (New)</h3>
                <p>The new system uses Spring WebMVC controllers with annotation-based configuration.</p>
                <div class="nav-links">
                    <a href="<c:url value='/spring/login'/>">Spring WebMVC Login</a>
                    <a href="<c:url value='/spring/demo'/>">Spring WebMVC Demo</a>
                </div>
            </div>
            
            <div class="section">
                <h3>Key Differences</h3>
                <ul>
                    <li><strong>URL Patterns:</strong> Traditional system uses <code>/app/*</code>, Spring system uses <code>/spring/*</code></li>
                    <li><strong>Controllers:</strong> Traditional uses single servlet + action classes, Spring uses individual controller classes</li>
                    <li><strong>Routing:</strong> Traditional uses ActionFactory, Spring uses annotation-based mapping</li>
                    <li><strong>Configuration:</strong> Traditional uses web.xml, Spring uses Java configuration</li>
                </ul>
            </div>
            
            <div class="section">
                <h3>Architecture Benefits</h3>
                <ul>
                    <li><strong>Coexistence:</strong> Both systems run simultaneously in the same application</li>
                    <li><strong>Gradual Migration:</strong> You can migrate functionality piece by piece</li>
                    <li><strong>Modern Features:</strong> Spring provides dependency injection, AOP, and more</li>
                    <li><strong>Testing:</strong> Spring controllers are easier to unit test</li>
                </ul>
            </div>
        </div>
    </body>
</html>
