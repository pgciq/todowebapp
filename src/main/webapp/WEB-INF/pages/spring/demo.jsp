<%-- 
    Document   : demo
    Created on : Spring WebMVC Demo
    Author     : Faisal Ahmed Pasha Mohammed
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Spring WebMVC Demo</title>
        <style>
            body { font-family: Arial, sans-serif; margin: 40px; }
            .container { max-width: 800px; margin: 0 auto; }
            .demo-section { margin: 20px 0; padding: 20px; background-color: #f8f9fa; 
                           border-left: 4px solid #007bff; }
            .code-block { background-color: #f8f8f8; padding: 15px; border: 1px solid #ddd; 
                         font-family: monospace; margin: 10px 0; }
            .nav-links a { display: inline-block; margin: 10px; padding: 10px 20px; 
                          background-color: #28a745; color: white; text-decoration: none; 
                          border-radius: 3px; }
            .nav-links a:hover { background-color: #218838; }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>${message}</h1>
            <p>${description}</p>
            
            <div class="demo-section">
                <h3>Spring WebMVC Features Demonstrated</h3>
                <ul>
                    <li><strong>@Controller</strong> - Annotation-based controllers</li>
                    <li><strong>@RequestMapping</strong> - URL mapping annotations</li>
                    <li><strong>@GetMapping/@PostMapping</strong> - HTTP method specific mappings</li>
                    <li><strong>@RequestParam</strong> - Automatic parameter binding</li>
                    <li><strong>Model</strong> - Model data binding</li>
                    <li><strong>RedirectAttributes</strong> - Flash attributes for redirects</li>
                    <li><strong>ViewResolver</strong> - JSP view resolution</li>
                </ul>
            </div>
            
            <div class="demo-section">
                <h3>Configuration</h3>
                <p>The Spring WebMVC system is configured using:</p>
                <div class="code-block">
@Configuration<br>
@EnableWebMvc<br>
@ComponentScan(basePackages = "io.github.faimoh.todowebapp.controllers.spring")<br>
public class WebConfig implements WebMvcConfigurer
                </div>
            </div>
            
            <div class="demo-section">
                <h3>Example Controller</h3>
                <div class="code-block">
@Controller<br>
@RequestMapping("/tasks")<br>
public class TaskController {<br>
&nbsp;&nbsp;&nbsp;&nbsp;@GetMapping("/dashboard")<br>
&nbsp;&nbsp;&nbsp;&nbsp;public String dashboard(Model model, HttpSession session) {<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;// Controller logic here<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return "tasks/dashboard";<br>
&nbsp;&nbsp;&nbsp;&nbsp;}<br>
}
                </div>
            </div>
            
            <div class="demo-section">
                <h3>Available Spring Endpoints</h3>
                <ul>
                    <li><code>GET /spring/login</code> - Login form</li>
                    <li><code>POST /spring/login</code> - Process login</li>
                    <li><code>GET /spring/logout</code> - Logout</li>
                    <li><code>GET /spring/tasks/dashboard</code> - Tasks dashboard</li>
                    <li><code>GET /spring/tasks/new</code> - New task form</li>
                    <li><code>POST /spring/tasks/create</code> - Create task</li>
                    <li><code>GET /spring/tasks/details?id=N</code> - Task details</li>
                    <li><code>POST /spring/tasks/update</code> - Update task</li>
                    <li><code>GET /spring/users/profile</code> - User profile</li>
                    <li><code>POST /spring/users/update</code> - Update profile</li>
                    <li><strong>Admin Endpoints:</strong></li>
                    <li><code>GET /spring/admin/accounts/dashboard</code> - Admin accounts dashboard</li>
                    <li><code>GET /spring/admin/accounts/new</code> - New account form</li>
                    <li><code>POST /spring/admin/accounts/create</code> - Create account</li>
                    <li><code>GET /spring/admin/accounts/details?id=N</code> - Account details</li>
                    <li><code>POST /spring/admin/accounts/update</code> - Update account</li>
                </ul>
            </div>
            
            <div class="nav-links">
                <a href="<c:url value='/spring/'/>">← Back to Home</a>
                <a href="<c:url value='/spring/login'/>">Try Spring Login</a>
            </div>
        </div>
    </body>
</html>
