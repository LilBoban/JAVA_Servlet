package com.example.service;

import com.example.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuthService {


    private static final String DB_URL = "jdbc:postgresql://localhost:5432/file_explorer";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "89541";


    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver успешно загружен.");
        } catch (ClassNotFoundException e) {
            System.err.println("Драйвер PostgreSQL JDBC не найден! Убедитесь, что зависимость добавлена в pom.xml.");

            throw new RuntimeException("Драйвер PostgreSQL JDBC не найден.", e);
        }
    }


    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }


    public static boolean register(User user) {

        String sql = "INSERT INTO users (login, password, email) VALUES (?, ?, ?)";


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getLogin());

            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());

            // Выполняем запрос INSERT
            int affectedRows = pstmt.executeUpdate();

            // Возвращаем true, если была добавлена хотя бы одна строка (должна быть 1)
            return affectedRows > 0;

        } catch (SQLException e) {

            System.err.println("Ошибка при регистрации пользователя " + user.getLogin() + ": " + e.getMessage());


            return false;
        }
    }

    public static User getUser(String login) {
        String sql = "SELECT login, password, email FROM users WHERE login = ?";
        User user = null;


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, login); // Устанавливаем значение для плейсхолдера (?)

            // Выполняем запрос SELECT
            try (ResultSet rs = pstmt.executeQuery()) {
                // Проверяем, найден ли пользователь
                if (rs.next()) {
                    user = new User();
                    user.setLogin(rs.getString("login"));
                    user.setPassword(rs.getString("password")); // Получаем пароль (открытый текст)
                    user.setEmail(rs.getString("email"));
                }
            } // ResultSet автоматически закроется здесь

        } catch (SQLException e) {
            System.err.println("Ошибка при получении пользователя " + login + ": " + e.getMessage());


        }

        return user; // Возвращаем найденного пользователя или null
    }
}