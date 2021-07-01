package com.example.cash_register.model.dao.impl;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class ConnectionPoolHolder {

    private ConnectionPoolHolder(){}
    private static volatile DataSource dataSource;

    public static DataSource getDataSource() {

        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    try {
                        Class.forName("org.postgresql.Driver");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    BasicDataSource ds = new BasicDataSource();
                    ds.setUrl("jdbc:postgresql://localhost:5432/registerBD");
                   // ds.setUrl("jdbc:postgresql://localhost:5432/cash-register");
                    ds.setUsername("postgres");
                    ds.setPassword("oeuhr[erfoierj");
                   // ds.setPassword("postgres");
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }
        return dataSource;

    }
}
