package com.bank.controller;

import com.bank.dao.LoanDAO;
import com.bank.model.Loan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/loan-details")
public class LoanDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      int loanId = Integer.parseInt(req.getParameter("loanId"));
      req.setAttribute("loanId", loanId);
      LoanDAO loanDAO = new LoanDAO();
      Loan loan = loanDAO.getLoanById(loanId);

      req.setAttribute("loan", loan);
      req.getRequestDispatcher("/loan-details.jsp").forward(req, resp);
    }
}
