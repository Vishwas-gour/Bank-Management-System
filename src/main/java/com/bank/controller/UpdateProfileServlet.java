package com.bank.controller;

import com.bank.dao.UserDAO;
import com.bank.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/update-profile")
public class UpdateProfileServlet extends ProfileServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        if (password != null && !password.isEmpty()) {
            if (!password.equals(confirmPassword)) {
                req.setAttribute("errorMessage", "Passwords not matching");
            }
        }

        UserDAO userDAO = new UserDAO();
        User user = new User();
        user.setUserId(Integer.parseInt(id));
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        boolean success = userDAO.updateUser(user);
        if (success) {
            req.setAttribute("successMessage", "Profile successfully updated.");
        }
        else {
            req.setAttribute("errorMessage" , "Profile update failed. Please try again.");
        }
        req.getRequestDispatcher("/profile?id=" + id).forward(req, resp);
    }
}
