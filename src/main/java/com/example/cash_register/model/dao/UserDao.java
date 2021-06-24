package com.example.cash_register.model.dao;

import com.example.cash_register.model.entity.User;

import java.util.Optional;

public interface UserDao extends AutoCloseable {

    Optional<User> findByLogin(String login);

    Optional<User> createUserIfNotExist(String login, String password, String name, String surname);

    void close();
}
