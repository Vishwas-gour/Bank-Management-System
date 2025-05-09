<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bank Management System - Transactions</title>
    <link rel="stylesheet" href="css/loan-application.css">
    <link rel="stylesheet" href="css/transaction.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
</head>
<body>
<div class="dashboard">
    <jsp:include page="header.jsp"/>
    <div class="container">
        <%---------------------After transation-------------------%>
        <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="error-message"><%= request.getAttribute("errorMessage") %>
        </div>
        <% } %>

        <% if (request.getAttribute("successMessage") != null) { %>
        <div class="success-message"><%= request.getAttribute("successMessage") %>
        </div>
        <% } %>

        <%---------------------------------------------------------%>
        <div class="register-form">
            <form action="transaction" method="post">
                <div class="form-group">
                    <label for="transactionType">Transaction Type:</label>
                    <select id="transactionType" name="transactionType" required onchange="showHideFields()">
                        <option value="">Select Type</option>
                        <option value="DEPOSIT" ${param.type == 'deposit' ? 'selected' : ''}>Deposit</option>
                        <option value="WITHDRAWAL" ${param.type == 'withdraw' ? 'selected' : ''}>Withdrawal</option>
                        <option value="TRANSFER">Transfer</option>
                    </select>
                </div>

                <%-- Your Accounts List For transaction --%>
                <div class="form-group">
                    <label for="fromAccount">From Account:</label>
                    <select id="fromAccount" name="fromAccount" required>
                        <option value="">Select Account</option>
                        <c:forEach items="${accounts}" var="account">
                            <option value="${account.accountId}" ${param.accountId == account.accountId ? 'selected' : ''}>
                                    ${account.accountType} - ${account.accountNumber} (Balance: ${account.balance})
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <%-- Again Your Accounts List  and other addcunt id field For transfer Money  --%>
                <div id="toAccountGroup" class="form-group" style="display: none;">
                    <label for="toAccount">To Account:</label>
                    <select id="toAccount" name="toAccount">
                        <option value="">Select Account</option>

                        <%-- FOR EACH ON USER ACCOUT --%>
                        <c:forEach items="${accounts}" var="account">
                            <option value="${account.accountId}">
                                    ${account.accountType} - ${account.accountNumber} (Balance: ${account.balance})
                            </option>
                        </c:forEach>
                        <option value="-1">Other Account</option>
                    </select>
                    <input name="otherAccountNumber" id="otherAccountNumber" type="text"
                           placeholder="Other Account Number" >
                </div>
                <%----------------------------------------------------------------%>
                <div class="form-group">
                    <label for="amount">Amount ($):</label>
                    <input type="number" id="amount" name="amount"  min="0" required>
                </div>

                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea id="description" name="description" rows="3"></textarea>
                </div>

                <div class="form-group">
                    <button class = "btn btn-primary" type="submit">Submit Transaction</button>
                </div>
            </form>
        </div>
    </div>

    <jsp:include page="footer.jsp"/>
</div>

<script>
    function showHideFields() {
        var transactionType = document.getElementById("transactionType").value;
        var toAccountGroup = document.getElementById("toAccountGroup");

        if (transactionType === "TRANSFER") toAccountGroup.style.display = "block";
        else toAccountGroup.style.display = "none";

        var toAccount = document.getElementById("toAccount");
        var otherAccountNumber = document.getElementById("otherAccountNumber");
        otherAccountNumber.style.display = "none";
        toAccount.addEventListener("change", function () {

            if (this.value === "-1") otherAccountNumber.style.display = "block";
            else otherAccountNumber.style.display = "none";
        });

    }

    // Initialize on page load
    window.onload = function () {
        showHideFields();
    };
</script>
</body>
</html>