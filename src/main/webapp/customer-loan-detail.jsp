<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bank Management System - Customer Loans</title>
    <link rel="stylesheet" href="css/customer-loan-detail.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
</head>
<body>
<jsp:include page="header.jsp"/>


<div class="container">
    <h2>Customer Loan Management</h2>

    <div class="admin-panel">

        <c:if test="${empty loans}">
            <div class="no-loans">
                <p>There are no loan applications in the system.</p>
            </div>
        </c:if>

        <c:if test="${not empty loans}">
            <table class="loan-table">
                <thead>
                <tr>
                    <th>Loan ID</th>
                    <th>Customer</th>
                    <th>Loan Type</th>
                    <th>Amount</th>
                    <th>Interest Rate</th>
                    <th>Term (months)</th>
                    <th>Application Date</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${loans}" var="loan">
                    <tr>
                        <td>${loan.loanId}</td>
                        <td>
                            <a href="profile?id=${loan.userId}">(User ID: ${loan.userId})</a>
                        </td>
                        <td>${loan.loanType}</td>
                        <td>$ ${loan.amount}</td>
                        <td>${loan.interestRate}%</td>
                        <td>${loan.termMonths}</td>
                        <td>${loan.startDate}</td>
                        <td>
                            <span class="status-${loan.status.toLowerCase()}">${loan.status}</span>
                        </td>
                        <td class="action-buttons">
                            <c:if test="${loan.status == 'PENDING'}">
                                <a href="loan-process?id=${loan.loanId}&action=approved" class="action-btn approve-btn">Approve</a>
                                <a href="loan-process?id=${loan.loanId}&action=rejected" class="action-btn reject-btn">Reject</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp"/>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const customerSearch = document.getElementById('customerSearch');
        const statusFilter = document.getElementById('statusFilter');
        const loanTypeFilter = document.getElementById('loanTypeFilter');
        const tableRows = document.querySelectorAll('.loan-table tbody tr');

        function filterTable() {
            const searchTerm = customerSearch.value.toLowerCase();
            const statusValue = statusFilter.value;
            const loanTypeValue = loanTypeFilter.value;

            tableRows.forEach(row => {
                const customerInfo = row.cells[1].textContent.toLowerCase();
                const status = row.cells[7].textContent.trim().toLowerCase();
                const loanType = row.cells[2].textContent.toLowerCase();

                const matchesSearch = customerInfo.includes(searchTerm);
                const matchesStatus = statusValue === 'all' || status.includes(statusValue);
                const matchesLoanType = loanTypeValue === 'all' || loanType === loanTypeValue;

                if (matchesSearch && matchesStatus && matchesLoanType) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        }

        customerSearch.addEventListener('input', filterTable);
        statusFilter.addEventListener('change', filterTable);
        loanTypeFilter.addEventListener('change', filterTable);
    });
</script>
</body>
</html>