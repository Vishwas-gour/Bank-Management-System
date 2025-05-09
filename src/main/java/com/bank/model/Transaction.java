package com.bank.model;

import java.math.BigDecimal;

public class Transaction {
    private int transactionId;
    private String transactionType;
    private BigDecimal amount;
    private Integer fromAccount;
    private Integer toAccount;
    private String description;
    private String status;
    private String transactionTime;

    // Constructors, getters, and setters

    public Transaction() {
    }
    public Transaction(int transactionId, String transactionType, BigDecimal amount, Integer fromAccount, Integer toAccount, String description, String status) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.description = description;
        this.status = status;
    }


    public Integer getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Integer fromAccount) {
        this.fromAccount = fromAccount;
    }


    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }


    // Getters and setters
    public int getTransactionId() {
        return transactionId;
    }


    public Integer getToAccount() {
        return toAccount;
    }

    public void setToAccount(Integer toAccount) {
        this.toAccount = toAccount;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setFromAccount(int fromAccountId) {
        this.fromAccount = fromAccountId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String completed) {
        this.status = completed;
    }

    public void setToAccount(int toAccount) {
        this.toAccount = toAccount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }
    public String getTransactionTime() {
        return transactionTime;
    }

    // Add remaining getters and setters
}