package com.bank.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Loan {
    private int loanId;
    private int userId;
    private int accountId;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private int termMonths;
    private BigDecimal monthlyPayment;
    private String startDate;
    private String dueDate;
    private String status; // PENDING, APPROVED, REJECTED, ACTIVE, PAID
    private String loanType; // PERSONAL, HOME, AUTO, EDUCATION, BUSINESS
    private String purpose;


        // Default constructor
    public Loan() {
    }

    // Constructor with fields
    public Loan(int loanId, int userId, int accountId, BigDecimal amount, BigDecimal interestRate,
                int termMonths, BigDecimal monthlyPayment, LocalDate startDate, LocalDate dueDate,
                String status, String loanType, String purpose) {
        this.loanId = loanId;
        this.userId = userId;
        this.accountId = accountId;
        this.amount = amount;
        this.interestRate = interestRate;
        this.termMonths = termMonths;
        this.monthlyPayment = monthlyPayment;
        this.startDate = startDate.toString();
        this.dueDate = dueDate.toString();
        this.status = status;
        this.loanType = loanType;
        this.purpose = purpose;
    }

    // Getters and Setters


    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getLoanId() {
        return this.loanId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(int termMonths) {
        this.termMonths = termMonths;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate.toString();
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate.toString();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

}