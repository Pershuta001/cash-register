package com.example.cash_register.model.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {

    public static void rollback(Connection connection){
        try {
            connection.rollback();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void commit(Connection connection){
        try {
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
