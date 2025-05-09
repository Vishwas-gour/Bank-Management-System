<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bank Management System - Login</title>
    <link rel="stylesheet" href="css/form.css">
</head>
<body>
<div class="container">
    <div class="register-form">
        <h2>Login to Your Account</h2>
        <% if(request.getAttribute("errorMessage") != null) { %>
        <div class="error-message"><%= request.getAttribute("errorMessage") %></div>
        <% } %>
        <form action="login" method="post">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <button type="submit">Login</button>
            </div>
        </form>
        <div class="login-link">
            Don't have an account? <a href="register">Register here</a>
        </div>
    </div>
</div>
</body>
</html>