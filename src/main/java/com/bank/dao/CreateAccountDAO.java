package com.bank.dao;

import com.bank.util.DatabaseUtil;

import java.sql.*;

public class CreateAccountDAO {

    public boolean doesAccountExist(int userId, String accountType) {
        String sql = "select * from accounts where user_id = ? and account_type = ?";
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, accountType);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
