package com.example.model;

import java.io.Serializable;

public class User  implements Serializable{
    private static final long serialVersionUID = 1L;

    private String login;
    private String password;
    private String email;

    // Геттеры и сеттеры
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}