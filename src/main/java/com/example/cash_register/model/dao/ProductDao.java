package com.example.cash_register.model.dao;

import com.example.cash_register.model.entity.Product;

import java.util.Optional;

public interface ProductDao extends BaseDao<Product>{
    int getPagesCount();

    void updateWeight(double parseDouble, Long id);

    void updateQuantity(int parseInt, Long id);

    Optional<Product> findByNameOrId(String name);
}
