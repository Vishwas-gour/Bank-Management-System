<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bank Management System - Create Account</title>
    <link rel="stylesheet" href="css/create-account.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/header.css">
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container">
    <h2>Create New Bank Account</h2>

    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="error-message"><%= request.getAttribute("errorMessage") %>
    </div>
    <% } %>

    <div class="create-account-form">
        <form action="account-create" method="post">
            <div class="form-group">
                <label for="accountType">Account Type:</label>
                <select id="accountType" name="accountType" required>
                    <option value="">Select Account Type</option>
                    <option value="SAVINGS">Savings Account</option>
                    <option value="CHECKING">Checking Account</option>
                    <option value="FIXED_DEPOSIT">Fixed Deposit</option>
                </select>
            </div>

            <div id="initialDepositGroup" class="form-group">
                <label for="initialDeposit">Initial Deposit ($):</label>
                <input type="number" id="initialDeposit" name="initialDeposit" step="0.01" min="0">
            </div>

            <div id="fdTermGroup" class="form-group" style="display: none;">
                <label for="term">Term (months):</label>
                <select id="term" name="term">
                    <option value="3">3 months</option>
                    <option value="6">6 months</option>
                    <option value="12">12 months</option>
                    <option value="24">24 months</option>
                    <option value="36">36 months</option>
                </select>
            </div>

            <div class="form-group">
                <button type="submit">Create Account</button>
                <a href="../dashboard" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
        lkj
    </div>
</div>

<jsp:include page="footer.jsp"/>


<script>
    function toggleFDTermGroup() {
        var accountType = document.getElementById("accountType").value;
        var fdTermGroup = document.getElementById("fdTermGroup");

        if (accountType === "FIXED_DEPOSIT") {
            fdTermGroup.style.display = "block";
        } else {
            fdTermGroup.style.display = "none";
        }
    }

    // Add event listener
    document.getElementById("accountType").addEventListener("change", toggleFDTermGroup);

    // Initialize on page load
    window.onload = function () {
        toggleFDTermGroup();
    };
</script>
</body>
</html>