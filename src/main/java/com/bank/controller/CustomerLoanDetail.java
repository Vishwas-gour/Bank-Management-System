package com.bank.controller;

import com.bank.dao.LoanDAO;
import com.bank.dao.UserDAO;
import com.bank.model.Loan;
import com.bank.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/customer-loan-detail")
public class CustomerLoanDetail extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoanDAO loanDAO = new LoanDAO();
        List<Loan> loans = loanDAO.getAllLoans();
        req.setAttribute("loans", loans);
        req.getRequestDispatcher("/customer-loan-detail.jsp").forward(req, resp);
    }

}
