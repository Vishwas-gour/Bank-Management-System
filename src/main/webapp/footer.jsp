<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<footer class="footer">
  <div class="footer-content">
    <p>&copy; <%= new java.util.Date().getYear() + 1900 %> Bank Management System. All rights reserved.</p>
    <div class="footer-links">
      <a href="${pageContext.request.contextPath}/about">About Us</a>
      <a href="${pageContext.request.contextPath}/contact">Contact</a>
      <a href="${pageContext.request.contextPath}/privacy">Privacy Policy</a>
      <a href="${pageContext.request.contextPath}/terms">Terms of Service</a>
    </div>
  </div>
</footer>