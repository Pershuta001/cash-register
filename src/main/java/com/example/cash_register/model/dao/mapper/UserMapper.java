package com.example.cash_register.model.dao.mapper;

import com.example.cash_register.model.entity.User;
import com.example.cash_register.model.enums.Roles;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User> {

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setRole(Roles.valueOf(rs.getString("role")));
        return user;
    }
}
