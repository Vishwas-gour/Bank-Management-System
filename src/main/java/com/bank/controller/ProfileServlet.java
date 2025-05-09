package com.bank.controller;

import com.bank.dao.UserDAO;
import com.bank.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        int userId;
        try {
            userId = Integer.parseInt(idParam);
        } catch (NumberFormatException | NullPointerException e) {
            req.setAttribute("errorMessage", "Invalid or missing user ID.");
            req.getRequestDispatcher("/profile.jsp").forward(req, resp);
            return;
        }


        UserDAO userDAO = new UserDAO();
        User profile = userDAO.getUserById(userId);

        if (profile != null) {
            req.setAttribute("profile", profile);
        } else {
            req.setAttribute("errorMessage", "User not found.");
        }
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }
}
