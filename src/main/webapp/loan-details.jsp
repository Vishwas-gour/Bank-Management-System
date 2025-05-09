<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Loan Details - MyBankSystem</title>
    <link rel="stylesheet" type="text/css" href="css/header.css">
    <link rel="stylesheet" type="text/css" href="css/loan.css">
    <link rel="stylesheet" type="text/css" href="css/loan-detail.css">

    <link rel="stylesheet" type="text/css" href="css/footer.css">

</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container">

    <div class="main-content">
        <h1>Loan Details</h1>

        <div class="breadcrumb">
            <a href="dashboard">Dashboard</a> &gt;
            <a href="loan">Loan</a> &gt;
            <a href="loan-details.jsp">Loan-Details</a>
        </div>

        <c:if test="${not empty message}">
            <div class="alert alert-${messageType}">${message}</div>
        </c:if>

        <div class="loan-details-container">
            <div class="loan-details-header">
                <h2>Loan #${loan.loanId} Details</h2>
                <div class="loan-actions">
                    <button class="btn btn-small" onclick="printLoanDetails()">Print</button>
                    <a href="loan" class="btn btn-small">Back to Loans</a>
                </div>
            </div>

            <div class="loan-status-bar status-${loan.status.toLowerCase()}">
                <span class="status-icon"></span>
                <span class="status-text">${loan.status}</span>
                <c:if test="${loan.status eq 'PENDING'}">
                    <span class="status-message">Your application is being reviewed</span>
                </c:if>
                <c:if test="${loan.status eq 'APPROVED'}">
                    <span class="status-message">Your loan has been approved!</span>
                </c:if>
                <c:if test="${loan.status eq 'REJECTED'}">
                    <span class="status-message">Unfortunately, your loan application was not approved</span>
                </c:if>
                <c:if test="${loan.status eq 'ACTIVE'}">
                    <span class="status-message">Your loan is currently active</span>
                </c:if>
                <c:if test="${loan.status eq 'PAID'}">
                    <span class="status-message">Your loan has been fully paid</span>
                </c:if>
            </div>

            <div class="loan-detail-sections">
                <div class="detail-section">
                    <h3>Loan Information</h3>
                    <div class="detail-row">
                        <span class="detail-label">Loan Type:</span>
                        <span class="detail-value">${loan.loanType}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Purpose:</span>
                        <span class="detail-value">${loan.purpose}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Account:</span>
                        <span class="detail-value">Account #${loan.accountId}</span>
                    </div>
                </div>

                <div class="detail-section">
                    <h3>Financial Details</h3>
                    <div class="detail-row">
                        <span class="detail-label">Principal Amount:</span>
                        <span class="detail-value">₹ <fmt:formatNumber value="${loan.amount}"
                                                                       pattern="#,##0.00"/></span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Interest Rate:</span>
                        <span class="detail-value"><fmt:formatNumber value="${loan.interestRate}"
                                                                     pattern="#,##0.00"/>%</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Term:</span>
                        <span class="detail-value">${loan.termMonths} months</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Monthly Payment:</span>
                        <span class="detail-value">₹ <fmt:formatNumber value="${loan.monthlyPayment}"
                                                                       pattern="#,##0.00"/></span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Total Payment:</span>
                        <span class="detail-value">₹ <fmt:formatNumber value="${loan.monthlyPayment * loan.termMonths}"
                                                                       pattern="#,##0.00"/></span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Total Interest:</span>
                        <span class="detail-value">₹ <fmt:formatNumber
                                value="${(loan.monthlyPayment * loan.termMonths) - loan.amount}"
                                pattern="#,##0.00"/></span>
                    </div>
                </div>

                <div class="detail-section">
                    <h3>Schedule Information</h3>
                    <div class="detail-row">
                        <span class="detail-label">Application Date:</span>
                        <span class="detail-value">
                            <c:choose>
                                <c:when test="${not empty loan.startDate}">
                                    <h1>${loan.startDate} </h1>
                                </c:when>
                                <c:otherwise>Pending</c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Final Payment Date:</span>
                        <span class="detail-value">
                            <c:choose>
                                <c:when test="${not empty loan.dueDate}">
                                    <h1>${loan.dueDate} </h1>
                                </c:when>
                                <c:otherwise>To be determined</c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                </div>
            </div>

            <c:if test="${loan.status eq 'ACTIVE'}">
                <div class="payment-options">
                    <h3>Payment Options</h3>
                    <div class="payment-buttons">
                        <a href="#" class="btn btn-primary">Make Payment</a>
                        <a href="#" class="btn btn-secondary">Set Up Auto-Pay</a>
                    </div>
                    <p class="payment-note">Note: Payments are due on the same day each month as your loan start
                        date.</p>
                </div>
            </c:if>

            <c:if test="${loan.status eq 'PENDING'}">
                <div class="application-status">
                    <h3>Application Status</h3>
                    <div class="status-timeline">
                        <div class="timeline-item active">
                            <div class="timeline-marker"></div>
                            <div class="timeline-content">
                                <h4>Application Submitted</h4>
                                <p>Your loan application has been received.</p>
                            </div>
                        </div>
                        <div class="timeline-item">
                            <div class="timeline-marker"></div>
                            <div class="timeline-content">
                                <h4>Under Review</h4>
                                <p>Our team is reviewing your application.</p>
                            </div>
                        </div>
                        <div class="timeline-item">
                            <div class="timeline-marker"></div>
                            <div class="timeline-content">
                                <h4>Decision</h4>
                                <p>You'll be notified of the decision.</p>
                            </div>
                        </div>
                        <div class="timeline-item">
                            <div class="timeline-marker"></div>
                            <div class="timeline-content">
                                <h4>Loan Disbursement</h4>
                                <p>Funds will be transferred to your account.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>


            <div class="help-contact">
                <h3>Need Help?</h3>
                <p>If you have any questions about your loan, please contact our customer service:</p>
                <p><strong>Phone:</strong> 1-800-123-4567</p>
                <p><strong>Email:</strong> loans@mybanksystem.com</p>
                <p><strong>Hours:</strong> Monday-Friday, 9:00 AM - 5:00 PM</p>
            </div>
        </div>
    </div>


</div>
<jsp:include page="footer.jsp"/>
<script>
    function printLoanDetails() {
        window.print();
    }
</script>
</body>
</html>