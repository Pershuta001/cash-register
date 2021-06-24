package com.example.cash_register.model.dao.mapper;

import com.example.cash_register.model.entity.User;
import com.example.cash_register.model.enums.Roles;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User> {

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {

        return new User.Builder()
                .id(rs.getLong("id"))
                .login(rs.getString("login"))
                .password(rs.getString("password"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .role(Roles.valueOf(rs.getString("role")))
                .build();

    }
}
