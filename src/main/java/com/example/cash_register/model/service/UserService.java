package com.example.cash_register.model.service;

import com.example.cash_register.model.dao.DaoFactory;
import com.example.cash_register.model.dao.ProductDao;
import com.example.cash_register.model.dao.UserDao;
import com.example.cash_register.model.entity.User;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.utils.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

public class UserService {

    private final DaoFactory repository = DaoFactory.getInstance();

    public Optional<User> getUserByLoginAndPassword(String login, String password) {
        try (UserDao userDao = repository.createUserDao()) {
            Optional<User> user = userDao.findByLogin(login);
            if (user.isPresent() && PasswordEncoder.passwordMatches(password, user.get().getPassword())) {
                return user;
            }
            return Optional.empty();
        }
    }

}
