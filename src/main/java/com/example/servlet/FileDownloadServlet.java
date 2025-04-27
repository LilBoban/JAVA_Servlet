package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login");
            return;
        }

        String userLogin = (String) session.getAttribute("user");
        Path userHome = Paths.get("/Users/Shared/filemanager/", userLogin)
                .normalize()
                .toAbsolutePath();

        String filePathParam = req.getParameter("path");
        if (filePathParam == null || filePathParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "File path not specified");
            return;
        }

        Path requestedPath = Paths.get(filePathParam).normalize().toAbsolutePath();

        // Проверка, что файл находится внутри userHome
        if (!requestedPath.startsWith(userHome)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        File file = requestedPath.toFile();
        if (!file.exists() || file.isDirectory()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }

        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = resp.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}