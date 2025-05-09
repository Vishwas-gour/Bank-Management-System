<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bank Management System - Dashboard</title>
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/dashboard.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="dashboard">
    <div class="container">
    <div class="myname">Welcome, ${user.fullName}</div>

        <div class="account-summary">

            <h3>Your Accounts</h3>

            <c:if test="${empty accounts}">
                <div class="no-accounts">
                    <h1>You don't have any accounts yet.</h1>
                </div>
            </c:if>
            <c:if test="${not empty accounts}">
                <div class="accounts-list">
                    <c:forEach items="${accounts}" var="account">
                        <div class="account-card">
                            <div class="account-details">
                                <h4>${account.accountType} Account</h4>
                                <p class="account-number">Account #: ${account.accountNumber}</p>
                                <p class="account-balance">Balance: ${account.balance}</p>
                            </div>
                            <div class="account-actions">
                                <a href="transaction?accountId=${account.accountId}" class="btn">Transactions</a>
                                <a href="account/details?id=${account.accountId}" class="btn">Details</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <div class="create-account">
                <a href="account/create" class="btn">Create New Account</a>
            </div>


        </div>

        <div class="quick-actions">
            <h3>Quick Actions</h3>

            <div class="action-buttons">
                <a href="transaction" class="action-btn">
                    <span class="icon"><img src="https://cdn-icons-png.flaticon.com/128/6014/6014579.png"></span>
                    <h5 class="label">Transfer Money</h5>
                </a>
                <a href="transaction?type=deposit" class="action-btn">
                    <span class="icon"><img src="https://cdn-icons-png.flaticon.com/128/2770/2770647.png"></span>
                    <h5 class="label">Deposit</h5>
                </a>
                <a href="transaction?type=withdraw" class="action-btn">
                    <span class="icon"><img src="https://cdn-icons-png.flaticon.com/128/8211/8211181.png"></span>
                    <h5 class="label">Withdraw</h5>
                </a>
                <a href="loan" class="action-btn">
                    <span class="icon"><img src="https://cdn-icons-png.flaticon.com/128/7791/7791034.png"></span>
                    <h5 class="label">Apply for Loan</h5>
                </a>


                <%--           FOR ADMIN                    --%>
                <c:if test="${user.role == 'ADMIN'}">
                    <a href="customer-loan-detail" class="action-btn">
                        <span class="icon"><img src="https://cdn-icons-png.flaticon.com/128/1150/1150592.png"></span>
                        <h5 class="label">Customer Loan Details</h5>
                    </a>
                </c:if>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>