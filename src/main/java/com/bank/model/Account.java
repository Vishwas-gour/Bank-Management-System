package com.bank.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Account {
    private int accountId;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private BigDecimal interestRate;
    private String status;
    private Timestamp createdAt;
    private  int userId;

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }


    public Account() {
    }

    public Account(int accountId, int userId, String accountNumber, String accountType, BigDecimal balance, BigDecimal interestRate, String status) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.interestRate = interestRate;
        this.status = status;
    }

    // Getters and setters
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public String getStatus() {
        return status;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

}