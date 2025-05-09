package com.bank.controller;

import com.bank.dao.AccountDAO;
import com.bank.dao.LoanDAO;
import com.bank.model.Account;
import com.bank.model.Loan;
import com.bank.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//@WebServlet(name = "LoanServlet", urlPatterns = {"/loans", "/loan-application",  "/loan-details"})
@WebServlet("/loan-application")

public class LoanApplicationServlet extends HttpServlet {
    private LoanDAO loanDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getServletPath();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        AccountDAO accountDAO = new AccountDAO();
        List<Account> accounts = accountDAO.getAccountsByUserId(user.getUserId());
        request.setAttribute("accounts", accounts);

            request.getRequestDispatcher("/loan-application.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user"); // Make sure this is set during login

        if (user == null) {
            response.sendRedirect("l login.jsp");
            return;
        }

        try {
            // Extract form fields
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            BigDecimal amount = new BigDecimal(request.getParameter("amount"));
            String loanType = request.getParameter("loanType");
            String purpose = request.getParameter("purpose");
            int termMonths = Integer.parseInt(request.getParameter("termMonths"));

            // Determine interest rate based on loan type
            BigDecimal interestRate;
            switch (loanType.toUpperCase()) {
                case "HOME":interestRate = new BigDecimal("4.5");break;
                case "AUTO":interestRate = new BigDecimal("5.0");break;
                case "EDUCATION":interestRate = new BigDecimal("3.5");break;
                case "BUSINESS":interestRate = new BigDecimal("6.0");break;
                default:interestRate = new BigDecimal("7.5"); // PERSONAL or fallback
            }

            // Calculate monthly payment
            BigDecimal monthlyPayment = LoanDAO.calculateMonthlyPayment(amount, interestRate, termMonths);

            // Create and populate Loan object
            Loan loan = new Loan();
            loan.setUserId(user.getUserId());
            loan.setAccountId(accountId);
            loan.setAmount(amount);
            loan.setInterestRate(interestRate);
            loan.setTermMonths(termMonths);
            loan.setMonthlyPayment(monthlyPayment);
            loan.setStartDate(LocalDate.now());
            loan.setDueDate(LocalDate.now().plusMonths(termMonths));
            loan.setStatus("PENDING");
            loan.setLoanType(loanType);
            loan.setPurpose(purpose);

            // Save loan via DAO
            LoanDAO loanDAO = new LoanDAO();
            boolean success = loanDAO.createLoanApplication(loan);

            if (success) {
                request.setAttribute("successMessage", "Loan application submitted successfully");
            } else {
                 request.setAttribute("errorMessage", "Invalid input or system error.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid input or system error.");
        }
        doGet(request, response);
    }

}