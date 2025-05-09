package com.bank.dao;

import com.bank.model.Loan;
import com.bank.model.User;
import com.bank.util.DatabaseUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    // Create a new loan application
    public boolean createLoanApplication(Loan loan) {
        String sql = "INSERT INTO loans (user_id, account_id, amount, interest_rate, term_months, " +
                "monthly_payment, start_date, due_date, status, loan_type, purpose) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, loan.getUserId());
            pstmt.setInt(2, loan.getAccountId());
            pstmt.setBigDecimal(3, loan.getAmount());
            pstmt.setBigDecimal(4, loan.getInterestRate());
            pstmt.setInt(5, loan.getTermMonths());
            pstmt.setBigDecimal(6, loan.getMonthlyPayment());
            pstmt.setDate(7, Date.valueOf(loan.getStartDate()));
            pstmt.setDate(8, Date.valueOf(loan.getDueDate()));
            pstmt.setString(9, loan.getStatus());
            pstmt.setString(10, loan.getLoanType());
            pstmt.setString(11, loan.getPurpose());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    loan.setLoanId(generatedKeys.getInt(1));
                }
                return true;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve a loan by ID
    public Loan getLoanById(int loanId) {
        String sql = "SELECT * FROM loans WHERE loan_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, loanId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractLoanFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Loan> getAllLoans() {
        String sql = "SELECT * FROM loans";
        List<Loan> loans = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                loans.add(extractLoanFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    public List<Loan> getLoanByType(String lonaType) {
        String sql = "SELECT * FROM loans WHERE loan_type = '" + lonaType + "'";
        List<Loan> loans = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                loans.add(extractLoanFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }
    public List<Loan> getLoanByStatus(String status) {
        String sql = "SELECT * FROM loans WHERE loan_type = '" + status + "'";
        List<Loan> loans = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                loans.add(extractLoanFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }



    // Get all loans for a user
    public List<Loan> getLoansByUserId(int userId) {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans WHERE user_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                loans.add(extractLoanFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loans;
    }

    // Get all loans for an account
    public List<Loan> getLoansByAccountId(int accountId) {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans WHERE account_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                loans.add(extractLoanFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loans;
    }

    // Update loan status
    public boolean updateLoanStatus(int loanId, String status) {
        String sql = "UPDATE loans SET status = ? WHERE loan_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, loanId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Calculate monthly payment based on loan amount, interest rate, and term
    public static BigDecimal calculateMonthlyPayment(BigDecimal loanAmount, BigDecimal annualInterestRate, int termMonths) {
        // Convert annual interest rate to monthly decimal rate
        BigDecimal monthlyRate = annualInterestRate.divide(new BigDecimal("100"))
                .divide(new BigDecimal("12"), 10, BigDecimal.ROUND_HALF_UP);

        // Calculate using the loan payment formula: P * r * (1 + r)^n / ((1 + r)^n - 1)
        BigDecimal onePlusRate = BigDecimal.ONE.add(monthlyRate);

        // Calculate (1 + r)^n
        BigDecimal compoundFactor = onePlusRate.pow(termMonths);

        // Calculate numerator P * r * (1 + r)^n
        BigDecimal numerator = loanAmount.multiply(monthlyRate).multiply(compoundFactor);

        // Calculate denominator (1 + r)^n - 1
        BigDecimal denominator = compoundFactor.subtract(BigDecimal.ONE);

        // Return the monthly payment amount rounded to 2 decimal places
        return numerator.divide(denominator, 2, BigDecimal.ROUND_HALF_UP);
    }

    // Delete a loan (usually for admin purposes)
    public boolean deleteLoan(int loanId) {
        String sql = "DELETE FROM loans WHERE loan_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, loanId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to extract a Loan object from a ResultSet
    private Loan extractLoanFromResultSet(ResultSet rs) throws SQLException {
        Loan loan = new Loan();
        loan.setLoanId(rs.getInt("loan_id"));
        loan.setUserId(rs.getInt("user_id"));
        loan.setAccountId(rs.getInt("account_id"));
        loan.setAmount(rs.getBigDecimal("amount"));
        loan.setInterestRate(rs.getBigDecimal("interest_rate"));
        loan.setTermMonths(rs.getInt("term_months"));
        loan.setMonthlyPayment(rs.getBigDecimal("monthly_payment"));

        Date startDate = rs.getDate("start_date");
        if (startDate != null) {
            loan.setStartDate(startDate.toLocalDate());
        }

        Date dueDate = rs.getDate("due_date");
        if (dueDate != null) {
            loan.setDueDate(dueDate.toLocalDate());
        }

        loan.setStatus(rs.getString("status"));
        loan.setLoanType(rs.getString("loan_type"));
        loan.setPurpose(rs.getString("purpose"));

        return loan;
    }


}