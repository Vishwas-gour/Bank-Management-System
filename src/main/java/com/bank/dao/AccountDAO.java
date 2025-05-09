package com.bank.dao;

import com.bank.model.Account;
import com.bank.util.DatabaseUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public boolean createAccount(Account account) {
        String sql = "INSERT INTO accounts (user_id, account_number, account_type, balance, interest_rate, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, account.getUserId());
            stmt.setString(2, account.getAccountNumber());
            stmt.setString(3, account.getAccountType());
            stmt.setBigDecimal(4, account.getBalance());
            stmt.setBigDecimal(5, account.getInterestRate());
            stmt.setString(6, account.getStatus());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Account> getAccountsByUserId(int userId) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE user_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getInt("account_id"));
                account.setUserId(rs.getInt("user_id"));
                account.setAccountNumber(rs.getString("account_number"));
                account.setAccountType(rs.getString("account_type"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setInterestRate(rs.getBigDecimal("interest_rate"));
                account.setStatus(rs.getString("status"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public Account getAccountById(int accountId) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getInt("account_id"));
                account.setUserId(rs.getInt("user_id"));
                account.setAccountNumber(rs.getString("account_number"));
                account.setAccountType(rs.getString("account_type"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setInterestRate(rs.getBigDecimal("interest_rate"));
                account.setStatus(rs.getString("status"));
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateBalance(int accountId, BigDecimal newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, newBalance);
            stmt.setInt(2, accountId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add other methods for handling account operations
}