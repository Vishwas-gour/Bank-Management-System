
package com.bank.controller;

import com.bank.dao.AccountDAO;
import com.bank.dao.TransactionDAO;
import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/account/details")
public class AccountDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String accountIdStr = request.getParameter("id");

        if (accountIdStr == null || accountIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        try {
            int accountId = Integer.parseInt(accountIdStr);

            AccountDAO accountDAO = new AccountDAO();
            Account account = accountDAO.getAccountById(accountId);

            // Get transaction history
            TransactionDAO transactionDAO = new TransactionDAO();
            List<Transaction> transactions = transactionDAO.getTransactionsByAccountId(accountId);
            request.setAttribute("account", account);
            request.setAttribute("transactions", transactions);
            request.getRequestDispatcher("/account-details.jsp").include(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }
}
