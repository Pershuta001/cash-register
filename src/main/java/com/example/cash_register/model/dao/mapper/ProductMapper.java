package com.example.cash_register.model.dao.mapper;

import com.example.cash_register.model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductMapper implements ObjectMapper<Product> {

    public List<Product> extractListFromResultSet(ResultSet rs) throws SQLException {
        List<Product> res = new ArrayList<>();
        while (rs.next()) {
            res.add(extractFromResultSet(rs));
        }
        return res;
    }

    @Override
    public Product extractFromResultSet(ResultSet rs) throws SQLException {
        return new Product.Builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .price(rs.getDouble("price"))
                .quantity(rs.getInt("quantity"))
                .byWeight(rs.getBoolean("byweight"))
                .weight(rs.getDouble("weight"))
                .build();
    }

    public static Product convertFromParameters(String name,
                                                double price,
                                                String amount,
                                                boolean byWeight) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setByWeight(byWeight);
        if (byWeight) {
            product.setAvailableWeight(Double.parseDouble(amount));
        } else {
            product.setAvailableQuantity(Integer.parseInt(amount));
        }
        return product;
    }
}
