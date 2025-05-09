<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Loan Application - MyBankSystem</title>
     <link rel="stylesheet" href="css/header.css">
     <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/loan-application.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container">

    <div class="main-content">
        <h1>Loan Application</h1>

        <div class="breadcrumb">
            <a href="loan.jsp">Loan</a> &gt;
            <a href="loan-application.jsp">Application</a>
        </div>

        <%---------------------After transation-------------------%>
        <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="error-message"><%= request.getAttribute("errorMessage") %>
        </div>
        <% } %>

        <% if (request.getAttribute("successMessage") != null) { %>
        <div class="success-message"><%= request.getAttribute("successMessage") %>
        </div>
        <% } %>

        <div class="loan-application-form">
            <form action="loan-application" method="post" onsubmit="return validateForm()">
                <div class="form-group">
                    <label for="accountId">Select Account:</label>
                    <select id="accountId" name="accountId" required>
                        <option value="">-- Select Account --</option>
                        <c:forEach items="${accounts}" var="account">
                            <option value="${account.accountId}" ${param.accountId == account.accountId ? 'selected' : ''}>
                                    ${account.accountType} - ${account.accountNumber} (Balance: ${account.balance})
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="loanType">Loan Type:</label>
                    <select id="loanType" name="loanType" onchange="updateInterestRate()" required>
                        <option value="PERSONAL">Personal Loan</option>
                        <option value="HOME">Home Loan</option>
                        <option value="AUTO">Auto Loan</option>
                        <option value="EDUCATION">Education Loan</option>
                        <option value="BUSINESS">Business Loan</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="amount">Loan Amount ($):</label>
                    <input type="number" id="amount" name="amount" min="1000" step="1000"
                           oninput="calculateMonthlyPayment()" required>
                </div>

                <div class="form-group">
                    <label for="termMonths">Loan Term (Months):</label>
                    <select id="termMonths" name="termMonths" onchange="calculateMonthlyPayment()" required>
                        <option value="12">12 months (1 year)</option>
                        <option value="24">24 months (2 years)</option>
                        <option value="36">36 months (3 years)</option>
                        <option value="48">48 months (4 years)</option>
                        <option value="60" selected>60 months (5 years)</option>
                        <option value="120">120 months (10 years)</option>
                        <option value="180">180 months (15 years)</option>
                        <option value="240">240 months (20 years)</option>
                        <option value="360">360 months (30 years)</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="purpose">Loan Purpose:</label>
                    <textarea id="purpose" name="purpose" rows="3" maxlength="500" required
                              placeholder="Please describe the purpose of this loan..."></textarea>
                </div>

                <div class="loan-summary-box">
                    <h3>Loan Summary</h3>
                    <div class="summary-row">
                        <span>Interest Rate:</span>
                        <span id="interestRateDisplay">7.50%</span>
                    </div>
                    <div class="summary-row">
                        <span>Estimated Monthly Payment:</span>
                        <span id="monthlyPaymentDisplay">$0.00</span>
                    </div>
                    <p class="summary-note">Note: Final approval and terms are subject to credit check and bank
                        policies.</p>
                </div>

                <div class="legal-consent">
                    <input type="checkbox" id="consent" name="consent" required>
                    <label for="consent">I agree to the terms and conditions, and authorize MyBankSystem to perform a
                        credit check.</label>
                </div>

                <div class="form-buttons">
                    <button type="submit" class="btn btn-primary">Submit Application</button>
                    <a href="loan" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>

        <div class="loan-info-box">
            <h3>Loan Application Information</h3>
            <p>Please note the following important information about your loan application:</p>
            <ul>
                <li>All loan applications are subject to credit approval</li>
                <li>Processing typically takes 1-3 business days</li>
                <li>You'll be notified via email when your application status changes</li>
                <li>For home loans over $250,000, additional documentation may be required</li>
                <li>Early repayment options are available with no penalty fees</li>
            </ul>
        </div>
    </div>

</div>
<jsp:include page="footer.jsp"/>
<script>
    function updateInterestRate() {
        const loanType = document.getElementById('loanType').value;
        let rate = 0;
        switch (loanType) {
            case 'HOME':rate = 4.5;
                break;
            case 'AUTO':rate = 5.0;
                break;
            case 'EDUCATION':rate = 3.5;
                break;
            case 'BUSINESS':rate = 6.0;
                break;
            default:rate = 7.5; // PERSONAL
        }

        document.getElementById('interestRateDisplay').textContent = rate.toFixed(2) + '%';
        // Recalculate monthly payment
        calculateMonthlyPayment();
    }

    function validateForm() {
        const amount = document.getElementById('amount').value;
        const accountId = document.getElementById('accountId').value;
        const purpose = document.getElementById('purpose').value;

        if (!amount || amount <= 0) {
            alert('Please enter a valid loan amount.');
            return false;
        }

        if (!accountId) {
            alert('Please select an account.');
            return false;
        }

        if (!purpose.trim()) {
            alert('Please enter the purpose of the loan.');
            return false;
        }
        return true;
    }


    function calculateMonthlyPayment() {
        const amount = parseFloat(document.getElementById('amount').value) || 0;
        const termMonths = parseInt(document.getElementById('termMonths').value) || 0;
        const loanType = document.getElementById('loanType').value;

        let interestRate = 0;
        switch (loanType) {
            case 'EDUCATION':
                interestRate = 3.5;
                break;
            case 'HOME':
                interestRate = 4.5;
                break;
            case 'AUTO':
                interestRate = 5.0;
                break;
            case 'BUSINESS':
                interestRate = 6.0;
                break;
            default:
                interestRate = 7.5; // PERSONAL
        }

        // Monthly interest rate
        const monthlyRate = interestRate / 100 / 12;

        // Monthly payment calculation
        let monthlyPayment = 0;
        if (amount > 0 && termMonths > 0 && monthlyRate > 0) {
            monthlyPayment = amount * monthlyRate * Math.pow(1 + monthlyRate, termMonths) /
                (Math.pow(1 + monthlyRate, termMonths) - 1);
        }

        document.getElementById('monthlyPaymentDisplay').textContent =
            '$' + monthlyPayment.toFixed(2);
    }

    document.addEventListener('DOMContentLoaded', function () {
        updateInterestRate();
    });
</script>
</body>
</html>