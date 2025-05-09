package com.bank.controller;

import com.bank.dao.LoanDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/loan-process")
public class LoanProcess extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int loanId = Integer.parseInt(req.getParameter("id"));
        LoanDAO loanDAO = new LoanDAO();
        loanDAO.updateLoanStatus(loanId, action.toUpperCase());
        req.getRequestDispatcher("/customer-loan-detail").forward(req, resp);
    }
}
