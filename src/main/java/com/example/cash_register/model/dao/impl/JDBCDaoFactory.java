package com.example.cash_register.model.dao.impl;

import com.example.cash_register.model.dao.DaoFactory;
import com.example.cash_register.model.dao.ProductDao;
import com.example.cash_register.model.dao.ReceiptDao;
import com.example.cash_register.model.dao.UserDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public ProductDao createProductDao() {
        return new JDBCProductDao(getConnection());
    }

    @Override
    public ReceiptDao createReceiptDao() {
        return new JDBCReceiptDao(getConnection());
    }

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
