package com.example.service;

import com.example.model.User;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AuthService {
    public static boolean register(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Ошибка регистрации: " + e.getMessage());
            return false;
        }
    }

    public static User getUser(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, login);
        } catch (Exception e) {
            System.err.println("Ошибка поиска пользователя: " + e.getMessage());
            return null;
        }
    }
}