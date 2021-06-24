package com.example.cash_register.model.entity;

import com.example.cash_register.model.enums.Roles;

public class User extends Entity {

    private String login;
    private String password;
    private String name;
    private String surname;
    private Roles role;

    public User() {
    }

    public User(String login, String password, String name, String surname, Roles role) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public User(Long id, String login, String password, String name, String surname, Roles role) {
        setId(id);
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public static class Builder {

        private final User user;

        public Builder() {
            this.user = new User();
        }

        public User.Builder id(Long id) {
            user.setId(id);
            return this;
        }

        public User.Builder login(String login) {
            user.login = login;
            return this;
        }

        public User.Builder password(String password) {
            user.password = password;
            return this;
        }

        public User.Builder name(String name) {
            user.name = name;
            return this;
        }

        public User.Builder surname(String surname) {
            user.surname = surname;
            return this;
        }

        public User.Builder role(Roles role) {
            user.role = role;
            return this;
        }
        public User build() {
            return user;
        }
    }
}
