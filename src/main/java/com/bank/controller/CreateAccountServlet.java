package com.bank.controller;

import com.bank.dao.AccountDAO;
import com.bank.dao.CreateAccountDAO;
import com.bank.model.Account;
import com.bank.model.User;
import com.bank.util.AccountNumberGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;


@WebServlet("/account-create")
public class CreateAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/create-account.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String accountType = request.getParameter("accountType");
        String initialDepositStr = request.getParameter("initialDeposit");

        // Generate account number
        String accountNumber = AccountNumberGenerator.generate();

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Validate input
        if (accountType == null || accountType.isEmpty()) {
            request.setAttribute("errorMessage", "Please select an account type");
            request.getRequestDispatcher("/create-account.jsp").forward(request, response);
            return;
        }

        // Create an account object
        Account account = new Account();
        account.setUserId(user.getUserId());
        account.setAccountNumber(accountNumber);
        account.setAccountType(accountType);

        // Validate account Exists
        CreateAccountDAO createAccountDAO = new CreateAccountDAO();

        boolean exitOrNot = createAccountDAO.doesAccountExist(account.getUserId(), account.getAccountType());
        if(exitOrNot){
            request.setAttribute("errorMessage", "Account already exist. Please try again.");
            request.getRequestDispatcher("/create-account.jsp").forward(request, response);
            return;
        }

        // Set the initial deposit if provided
        if (initialDepositStr != null && !initialDepositStr.isEmpty()) {
            try {
                BigDecimal initialDeposit = new BigDecimal(initialDepositStr);
                account.setBalance(initialDeposit);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Failed to create account. Please try again.");
                request.getRequestDispatcher("/create-account.jsp").forward(request, response);
                return;
            }
        } else {
            account.setBalance(BigDecimal.ZERO);
        }

        // Set interest rate based on an account type
        if ("SAVINGS".equals(accountType)) {
            account.setInterestRate(new BigDecimal("2.50"));
        } else if ("CHECKING".equals(accountType)) {
            account.setInterestRate(new BigDecimal("0.50"));
        } else if ("FIXED_DEPOSIT".equals(accountType)) {
            String termStr = request.getParameter("term");
            int term = 12; // Default to 12 months

            if (termStr != null && !termStr.isEmpty()) {
                try {
                    term = Integer.parseInt(termStr);
                } catch (NumberFormatException e) {
                    // Use default
                }
            }

            // Set interest rate based on term
            if (term <= 3) {
                account.setInterestRate(new BigDecimal("4.50"));
            } else if (term <= 6) {
                account.setInterestRate(new BigDecimal("5.00"));
            } else if (term <= 12) {
                account.setInterestRate(new BigDecimal("5.50"));
            } else {
                account.setInterestRate(new BigDecimal("6.00"));
            }
        }

        account.setStatus("ACTIVE");
        // Save to a database
        AccountDAO accountDAO = new AccountDAO();
        boolean success = accountDAO.createAccount(account);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            request.setAttribute("errorMessage", "Failed to create account. Please try again.");
            request.getRequestDispatcher("/create-account.jsp").forward(request, response);
        }
    }
}

