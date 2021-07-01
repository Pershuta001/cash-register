package com.example.cash_register.model.dao;

import com.example.cash_register.model.entity.Product;

import java.sql.SQLException;
import java.util.Optional;

public interface ProductDao extends BaseDao<Product>{
    int getPagesCount() throws SQLException;

    void updateWeight(double parseDouble, Long id) throws SQLException;

    void updateQuantity(int parseInt, Long id) throws SQLException;

    Optional<Product> findByNameOrId(String name) throws SQLException;
}
