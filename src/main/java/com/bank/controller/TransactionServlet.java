package com.bank.controller;

import com.bank.dao.AccountDAO;
import com.bank.dao.TransactionDAO;
import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.model.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.bank.util.DatabaseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {

    private void setTransaction(String transactionType, int fromAccountId, int toAccountId, BigDecimal amount, String description) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionType);
        transaction.setAmount(amount);
        transaction.setFromAccount(fromAccountId);
        if(toAccountId != -1) {
            transaction.setToAccount(toAccountId);
        }
        transaction.setDescription(description);
        transaction.setStatus("COMPLETED");
        TransactionDAO transactionDAO = new TransactionDAO();
        transactionDAO.createTransaction(transaction);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        AccountDAO accountDAO = new AccountDAO();
        List<Account> accounts = accountDAO.getAccountsByUserId(user.getUserId());

        request.setAttribute("accounts", accounts);
        request.getRequestDispatcher("/transaction.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String transactionType = request.getParameter("transactionType");
        int fromAccountId = Integer.parseInt(request.getParameter("fromAccount"));
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));
        String description = request.getParameter("description");

        AccountDAO accountDAO = new AccountDAO();
        Account fromAccount = accountDAO.getAccountById(fromAccountId);


        // Validate amount
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            request.setAttribute("errorMessage", "Amount must be greater than zero");
            doGet(request, response);
            return;
        }

        // Handle different transaction types
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false); // prevent form auto-commit

            if ("WITHDRAWAL".equals(transactionType)) {
                // Check if there are enough balances
                if (fromAccount.getBalance().compareTo(amount) < 0) {
                    request.setAttribute("errorMessage", "Insufficient balance");
                    doGet(request, response);
                    return;
                }

                // Update account balance
                BigDecimal newBalance = fromAccount.getBalance().subtract(amount);
                accountDAO.updateBalance(fromAccountId, newBalance);

                // Create a transaction record
                setTransaction("WITHDRAWAL", fromAccountId, -1, amount, description);

            } else if ("DEPOSIT".equals(transactionType)) {
                // Update account balance
                BigDecimal newBalance = fromAccount.getBalance().add(amount);
                accountDAO.updateBalance(fromAccountId, newBalance);

                // Create a transaction record
                setTransaction("DEPOSIT", fromAccountId, -1,amount, description);

            } else if ("TRANSFER".equals(transactionType)) {
                int toAccountId = Integer.parseInt(request.getParameter("toAccount"));
                int otherAccountId = (request.getParameter("otherAccountNumber").isEmpty()
                                    || request.getParameter("otherAccountNumber").equals("")) ?
                                    -1 : Integer.parseInt(request.getParameter("otherAccountNumber"));

                // means you select transfer to other's account
                if(otherAccountId != -1 ) {
                    toAccountId = otherAccountId;
                    TransactionDAO transactionDAO = new TransactionDAO();
                    if(!transactionDAO.isAccountExists(toAccountId)){
                        request.setAttribute("errorMessage", "Account not Exists. Please try again.");
                        doGet(request, response);
                        return;
                    }
                }

                // if both account same
                if(fromAccountId == toAccountId){
                    request.setAttribute("errorMessage", "You can't transfer to same account.");
                    doGet(request, response);
                    return;
                }

                Account toAccount = accountDAO.getAccountById(toAccountId);

                // Check if there are enough balances
                if (fromAccount.getBalance().compareTo(amount) < 0) {
                    request.setAttribute("errorMessage", "Insufficient balance");
                    doGet(request, response);
                    return;
                }

                // Update account balances
                BigDecimal newFromBalance = fromAccount.getBalance().subtract(amount);
                BigDecimal newToBalance = toAccount.getBalance().add(amount);

                accountDAO.updateBalance(fromAccountId, newFromBalance); // balance subtracted
                accountDAO.updateBalance(toAccountId, newToBalance); // balance added

                // Create a transaction record
                setTransaction("TRANSFER", fromAccountId, toAccountId, amount, description);
            }

            conn.commit();
            request.setAttribute("successMessage", "Transaction completed successfully");
            doGet(request, response);

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            request.setAttribute("errorMessage", "Transaction failed. Please try again.");
            doGet(request, response);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}