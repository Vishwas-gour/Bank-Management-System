package com.bank.dao;

import com.bank.model.Transaction;
import com.bank.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public boolean createTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (transaction_type, amount, from_account, to_account, description, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, transaction.getTransactionType());
            stmt.setBigDecimal(2, transaction.getAmount());

            if (transaction.getFromAccount() == null) {
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(3, transaction.getFromAccount());
            }

            if (transaction.getToAccount() == null) {
                stmt.setNull(4, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(4, transaction.getToAccount());
            }

            stmt.setString(5, transaction.getDescription());
            stmt.setString(6, transaction.getStatus());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transaction> getTransactionsByAccountId(int accountId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE from_account = ? OR to_account = ? ORDER BY created_at DESC" ;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            stmt.setInt(2, accountId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getInt("transaction_id"));
                transaction.setTransactionType(rs.getString("transaction_type"));
                transaction.setAmount(rs.getBigDecimal("amount"));
                transaction.setFromAccount(rs.getInt("from_account"));
                transaction.setToAccount(rs.getInt("to_account"));
                transaction.setDescription(rs.getString("description"));
                transaction.setStatus(rs.getString("status"));
                transaction.setTransactionTime(rs.getTimestamp("created_at").toString());
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }


    public boolean isAccountExists(int accountId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        }
    }
}