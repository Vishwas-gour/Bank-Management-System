<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - Bank Management System</title>
    <link rel="stylesheet" href="css/profile.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
</head>
<body>
<jsp:include page="header.jsp" />
<div class="container">
    <h2>My Profile</h2>
    <div>
        <div>
            <div>
                <h3>Personal Information</h3>
                <c:choose>
                    <c:when test="${not empty profile}">
                        <p><strong>Customer ID:</strong> ${profile.userId}</p>
                        <p><strong>Customer ID:</strong> ${profile.username}</p>
                        <p><strong>Full Name:</strong> ${profile.fullName}</p>
                        <p><strong>Email:</strong> ${profile.email}</p>
                        <p><strong>Phone:</strong> ${profile.phone}</p>
                        <p><strong>Address:</strong> ${profile.address}</p>
                        <p><strong>Role:</strong> ${profile.role}</p>

                    </c:when>
                    <c:otherwise>
                        <p class="error-message">Unable to load customer information.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <c:if test="${not empty successMessage}">
            <div class="success-message">${successMessage}</div>
        </c:if>
        <% if(request.getAttribute("errorMessage") != null) { %>
        <div class="error-message"><%= request.getAttribute("errorMessage") %></div>
        <% } %>
        <!-- Security Settings -->
        <div>
            <h3>Update Profile</h3>
            <form action="update-profile" method="get">
                <input type="hidden" name="id" value="${profile.userId}" />
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Confirm Password:</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
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
                    <label for="address">Address:</label>
                    <textarea id="address" name="address" rows="3"></textarea>
                </div>
                <div class="form-group">
                    <button type="submit">Update-Info</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />

<script>

</script>
</body>
</html>
