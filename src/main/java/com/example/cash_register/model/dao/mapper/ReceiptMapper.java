package com.example.cash_register.model.dao.mapper;

import com.example.cash_register.model.entity.Product;
import com.example.cash_register.model.entity.Receipt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ReceiptMapper {

    public Optional<Receipt> extractFromResultSet(ResultSet resultSet) throws SQLException {
        Receipt receipt = new Receipt();
        while (resultSet.next()) {
            if (receipt.getId() == null)
                receipt.setId(resultSet.getLong("id"));
            Product p = new Product.Builder()
                    .id(resultSet.getLong("product_id"))
                    .name(resultSet.getString("name"))
                    .price(resultSet.getDouble("price"))
                    .byWeight(resultSet.getBoolean("byweight"))
                    .build();

            receipt.getProductsInReceipt().put(p, p.isByWeight() ?
                    resultSet.getDouble("weight") : resultSet.getDouble("amount"));
        }
        if (receipt.getId() == null) {
            return Optional.empty();
        }
        return Optional.of(receipt);
    }

    public Optional<Map<Product, Double>> extractXProductReport(ResultSet rs) throws SQLException {
        HashMap<Product, Double> report = new HashMap<>();
        while (rs.next()) {
            Product p = new Product.Builder()
                    .id(rs.getLong("product_id"))
                    .name(rs.getString("name"))
                    .price(rs.getDouble("price"))
                    .byWeight(rs.getBoolean("byweight"))
                    .build();

            if (p.isByWeight())
                report.put(p, rs.getDouble("weight"));
            else
                report.put(p, rs.getDouble("quantity"));
        }
        if (report.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(report);
    }

    public List<Receipt> extractListFromResultSet(ResultSet rs) throws SQLException {
        List<Receipt> res = new ArrayList<>();
        while(rs.next()){
            res.add(extractFromResultSet(rs).get());
        }
        return res;
    }
}
