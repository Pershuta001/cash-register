package com.example.cash_register.model.dao.impl;

import com.example.cash_register.model.dao.UserDao;
import com.example.cash_register.model.dao.mapper.UserMapper;
import com.example.cash_register.model.entity.User;
import com.example.cash_register.utils.PasswordEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JDBCUserDao implements UserDao {

    private final Connection connection;

    private final static String FIND_BY_LOGIN =
            "SELECT * FROM users u WHERE u.login = ?";

    private final static String CREATE_USER =
            "INSERT INTO users(login, password, name, surname, role) VALUES (?,?,?,?,?)";

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        PreparedStatement stmt;
        ResultSet rs;
        User user = null;
        try {
            stmt = connection.prepareStatement(FIND_BY_LOGIN);
            stmt.setString(1, login);
            rs = stmt.executeQuery();
            UserMapper mapper = new UserMapper();
            if (rs.next())
                user = mapper.extractFromResultSet(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> createUserIfNotExist
            (String login, String password, String name, String surname) {
        PreparedStatement stmt;
        Optional<User> user = Optional.empty();
        try {
            stmt = connection.prepareStatement(FIND_BY_LOGIN);
            connection.setAutoCommit(false);
            stmt.setString(1, login);
            if (stmt.executeQuery().next()) {
                return Optional.empty();
            }

            PreparedStatement stmt1 = connection.prepareStatement(CREATE_USER);
            stmt1.setString(1, login);
            stmt1.setString(2, PasswordEncoder.encode(password));
            stmt1.setString(3, name);
            stmt1.setString(4, surname);
            stmt1.setInt(5, 1);
            stmt1.executeUpdate();
            user = findByLogin(login);
        } catch (SQLException ex) {
            DBUtils.rollback(connection);
        } finally {
            DBUtils.commit(connection);
        }
        return user;

    }
}
