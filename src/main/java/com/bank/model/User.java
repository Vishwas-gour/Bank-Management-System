package com.bank.model;

import java.sql.Timestamp;

public class User {
    private int userId;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String role;
    private Timestamp createdAt;
    private Timestamp lastLogin;

    // Constructors, getters, and setters

    public User(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public User(int userId, String username, String fullName, String email, String phone, String address, String role, Timestamp lastLogin) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.lastLogin = lastLogin;
    }

    public User() {

    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
    this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRole(String customer) {
        this.role = customer;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }


    public String getAddress() {
        return address;
    }

    public String getRole() {
        return role;
    }

}