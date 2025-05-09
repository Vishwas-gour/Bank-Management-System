<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page isELIgnored="false" %>

<header class="header">
    <div class="logo">
        <h1>Bank Management System</h1>
    </div>
    <nav class="nav">
      <ul>
            <li><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/transaction">Transactions</a></li>
            <li><a href="${pageContext.request.contextPath}/profile?id=${user.userId}">My Profile</a></li>
            <li><a href="${pageContext.request.contextPath}/settings">Settings</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
        </ul>
    </nav>
</header>