<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bank Management System - Account Details</title>
    <link rel="stylesheet" href="css/account-details.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/header.css">
</head>
<body>
<div class="dashboard">
    <jsp:include page="header.jsp" />

    <div class="container">
        <h2>Account Details</h2>

        <div class="account-info">
            <div class="account-header">
                <h3>${account.accountType} Account</h3>
                <span class="status ${account.status.toLowerCase()}">${account.status}</span>
            </div>

            <table class="info-table">
                <tr>
                    <td>Account Number:</td>
                    <td>${account.accountNumber}</td>
                </tr>
                <tr>
                    <td>Current Balance:</td>
                    <td>${account.balance}</td>
                </tr>
                <tr>
                    <td>Interest Rate:</td>
                    <td>${account.interestRate}%</td>
                </tr>
                <tr>
                    <td>Account Created:</td>
                    <td><fmt:formatDate value="${account.createdAt}" pattern="dd MMM yyyy"/></td>
                </tr>
            </table>
        </div>

        <div class="transaction-history">
            <h3>Recent Transactions</h3>

            <c:if test="${empty transactions}">
                <p>No transactions found for this account.</p>
            </c:if>

            <c:if test="${not empty transactions}">
                <table class="transaction-table" border="2">
                    <thead >
                    <tr>
                        <th>Date</th>
                        <th>Desc..</th>
                        <th>Type</th>
                        <th>From Acc..</th>
                        <th>To Acc..</th>
                        <th>Amt..</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                        <%-- FOR EACH ON USER TRANSACTIONS --%>
                    <c:forEach items="${transactions}" var="transaction">
                        <tr>
                            <td>${transaction.transactionTime}</td>
                            <td>${transaction.description}</td>
                            <td>${transaction.transactionType}</td>
                            <td class = "fromAccount">${transaction.fromAccount == '0' ? 'Null': transaction.fromAccount}</td>
                            <td class = "fromAccount">${transaction.toAccount == '0' ? 'Null': transaction.toAccount}</td>

                            <td class="${transaction.fromAccount == account.accountId ? 'debit' : 'credit'}">
                                    ${transaction.fromAccount == account.accountId ? '-' : '+'}${transaction.amount}
                            </td>
                            <td class="status ${transaction.status.toLowerCase()}">${transaction.status}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>

        <div class="account-actions">
            <a href="../transaction?accountId=${account.accountId}" class="btn">New Transaction</a>
            <a href="../dashboard" class="btn btn-secondary">Back to Dashboard</a>
        </div>
    </div>

    <jsp:include page="footer.jsp" />
</div>
</body>
</html>