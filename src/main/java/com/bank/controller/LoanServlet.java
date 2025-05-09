package com.bank.controller;

import com.bank.dao.LoanDAO;
import com.bank.model.Loan;
import com.bank.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/loan")
public class LoanServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        LoanDAO loanDAO = new LoanDAO();
        List<Loan> loans = loanDAO.getLoansByUserId(user.getUserId());

        req.setAttribute("loans", loans);
        req.getRequestDispatcher("/loan.jsp").forward(req, resp);
    }
}
