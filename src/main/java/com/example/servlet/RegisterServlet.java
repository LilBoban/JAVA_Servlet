package com.example.servlet;

import com.example.model.User;
import com.example.service.AuthService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.setEmail(req.getParameter("email"));

        AuthService.register(user);

        // Создаём папку пользователя
        String userHome = "/Users/Shared/filemanager/" + user.getLogin();
        File dir = new File(userHome);
        if (!dir.exists()) {
            dir.mkdirs(); // Создаст все недостающие родительские директории
        }

        resp.sendRedirect("login");
    }
}