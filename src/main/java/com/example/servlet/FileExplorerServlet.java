package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.stream.Collectors;

@WebServlet("/explorer")
public class FileExplorerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPath = req.getParameter("path");
        if (currentPath == null || currentPath.isEmpty()) {
            currentPath = System.getProperty("user.home"); // Начальная директория
        }

        File directory = new File(currentPath);
        if (!directory.exists() || !directory.isDirectory()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Directory not found");
            return;
        }

        // Получаем список файлов и папок
        File[] files = directory.listFiles();
        req.setAttribute("files", files);
        req.setAttribute("currentPath", currentPath);
        req.setAttribute("parentPath", directory.getParent());
        req.setAttribute("generatedTime", new Date());

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}