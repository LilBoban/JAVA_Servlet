package com.example.service;

import com.example.model.User;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private static final Map<String, User> users = new HashMap<>();

    public static void register(User user) {
        users.put(user.getLogin(), user);
    }

    public static User getUser(String login) {
        return users.get(login);
    }
}