package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class FileExplorerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login");
            return;
        }

        String userLogin = (String) session.getAttribute("user");
        Path userHome = Paths.get("/Users/Shared/filemanager/", userLogin)
                .normalize()
                .toAbsolutePath();

        String pathParam = req.getParameter("path");
        Path currentPath;

        if (pathParam == null || pathParam.isEmpty()) {
            currentPath = userHome;
        } else {
            currentPath = Paths.get(pathParam).normalize().toAbsolutePath();

            // Проверка, что путь находится внутри userHome
            if (!currentPath.startsWith(userHome)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }
        }

        File directory = currentPath.toFile();
        if (!directory.exists() || !directory.isDirectory()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Directory not found");
            return;
        }

        File[] files = directory.listFiles();
        req.setAttribute("files", files);
        req.setAttribute("currentPath", currentPath);
        req.setAttribute("parentPath", directory.getParent());
        req.setAttribute("generatedTime", new Date());

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}