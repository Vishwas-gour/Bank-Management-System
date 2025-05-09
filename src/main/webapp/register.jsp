<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bank Management System - Register</title>
    <link rel="stylesheet" href="css/form.css">
</head>
<body>
<div class="container">
    <div class="register-form">
        <h2>Create New Account</h2>
        <% if(request.getAttribute("errorMessage") != null) { %>
        <div class="error-message"><%= request.getAttribute("errorMessage") %></div>
        <% } %>
        <form action="register" method="post">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="confirmPassword">Confirm Password:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>
            <div class="form-group">
                <label for="fullName">Full Name:</label>
                <input type="text" id="fullName" name="fullName" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="phone">Phone:</label>
                <input type="tel" id="phone" name="phone">
            </div>
            <div class="form-group">
                <label for="role">Role:</label>
                <select id="role" name="role" required>
                    <option value="">Select</option>
                    <option value="ADMIN">Admin</option>
                    <option value="CUSTOMER">Customer</option>
                </select>
            </div>
            <div class="form-group">
                <label for="address">Address:</label>
                <textarea id="address" name="address" rows="3"></textarea>
            </div>
            <div class="form-group">
                <button type="submit">Register</button>
            </div>

        </form>
        <div class="login-link">
            Already have an account? <a href="login">Login here</a>
        </div>
    </div>
</div>
</body>
</html>