<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Loans - MyBankSystem</title>
  <link rel="stylesheet" href="css/loan.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
</head>
<body>
<jsp:include page="header.jsp" />
<div class="container">
    <div class="main-content">
        <h1>My Loans</h1>

        <div class="dashboard-actions">
            <a href="loan-application?userId=${user.userId}" class="btn btn-primary">Apply for a New Loan</a>
        </div>

        <c:if test="${not empty message}">
            <div class="alert alert-${messageType}">${message}</div>
        </c:if>

        <div class="loan-summary">
            <h2>Loan Summary</h2>
            <c:choose>
                <c:when test="${empty loans}">
                    <p>You don't have any loans at the moment.</p>
                </c:when>
                <c:otherwise>
                    <table class="loans-table">
                        <thead>
                        <tr>
                            <th>Loan ID</th>
                            <th>Type</th>
                            <th>Amount</th>
                            <th>Interest Rate</th>
                            <th>Monthly Payment</th>
                            <th>Term</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="loan" items="${loans}">
                            <tr class="loan-${loan.status.toLowerCase()}">
                                <td>${loan.loanId}</td>
                                <td>${loan.loanType}</td>
                                <td>₹${loan.amount}</td>
                                <td>${loan.interestRate}%</td>
                                <td>₹${loan.monthlyPayment}</td>
                                <td>${loan.termMonths} months</td>
                                <td><span class="status-badge status-${loan.status.toLowerCase()}">${loan.status}</span></td>
                                <td><a href="loan-details?loanId=${loan.loanId}" class="btn btn-small">Details</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="loan-info-box">
            <h3>Loan Information</h3>
            <p>At MyBankSystem, we offer various loan products to meet your financial needs:</p>
            <ul>
                <li><strong>Personal Loans:</strong> Flexible funding for various personal expenses</li>
                <li><strong>Home Loans:</strong> Competitive rates for buying or refinancing your home</li>
                <li><strong>Auto Loans:</strong> Affordable financing for your vehicle purchase</li>
                <li><strong>Education Loans:</strong> Support for your educational pursuits</li>
                <li><strong>Business Loans:</strong> Capital for business growth and expansion</li>
            </ul>
          </div>
    </div>

</div>
<jsp:include page="footer.jsp" />
</body>
</html>