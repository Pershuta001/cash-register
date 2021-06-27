package com.example.cash_register.model.dao.mapper;

import com.example.cash_register.controller.view.ReportByCashiersView;
import com.example.cash_register.controller.view.ReportByProductsView;
import com.example.cash_register.model.entity.Product;
import com.example.cash_register.model.entity.Receipt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ReceiptMapper {

    public Optional<Receipt> extractFromResultSet(ResultSet resultSet) throws SQLException {
        Receipt receipt = new Receipt();
        while (resultSet.next()) {
            if (receipt.getId() == null) {
                receipt.setId(resultSet.getLong("id"));
                receipt.setCashierId(resultSet.getLong("cashier_id"));
                receipt.setDate(resultSet.getDate("date"));
            }
            Product p = new Product.Builder()
                    .id(resultSet.getLong("product_id"))
                    .name(resultSet.getString("name"))
                    .price(resultSet.getDouble("price"))
                    .byWeight(resultSet.getBoolean("byweight"))
                    .build();
            if (resultSet.getLong("product_id") != 0) {
                receipt.getProductsInReceipt().put(p, p.isByWeight() ?
                        resultSet.getDouble("weight") : resultSet.getDouble("amount"));
            }
        }
        if (receipt.getId() == null) {
            return Optional.empty();
        }
        return Optional.of(receipt);
    }

    public List<ReportByCashiersView> extractListReportFromResultSet(ResultSet rs) throws SQLException {
        List<ReportByCashiersView> cashiersViews = new ArrayList<>();
        while (rs.next()) {
            cashiersViews.add(extractCashierReportFromResultSet(rs));
        }
        return cashiersViews;
    }

    public ReportByCashiersView extractCashierReportFromResultSet(ResultSet rs) throws SQLException {
        return new ReportByCashiersView.Builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .numberOfReceipts(rs.getInt("completed_receipts"))
                .totalPrice(rs.getDouble("cost"))
                .build();
    }

    public List<Receipt> extractListFromResultSet(ResultSet rs) throws SQLException {
        List<Receipt> res = new ArrayList<>();
        while (rs.next()) {
            res.add(extractEmptyReceipt(rs));
        }
        return res;
    }

    private Receipt extractEmptyReceipt(ResultSet rs) throws SQLException {
        return new Receipt.Builder()
                .id(rs.getLong("id"))
                .cashierId(rs.getLong("cashier_id"))
                .date(rs.getDate("date"))
                .build();
    }

    public List<ReportByProductsView> extractListReportProductsFromResultSet(ResultSet rs) throws SQLException {

        List<ReportByProductsView> res = new ArrayList<>();
        while (rs.next()) {
            res.add(extractProductReport(rs));
        }
        return res;
    }

    public ReportByProductsView extractProductReport(ResultSet rs) throws SQLException {
        return new ReportByProductsView.Builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .price(rs.getDouble("price"))
                .soldAmount(rs.getDouble("sold_amount"))
                .totalPrice(rs.getDouble("total_price"))
                .build();
    }

    public Map<Product, Double> extractMapOfProducts(ResultSet rs) throws SQLException {
        Map<Product, Double> result = new HashMap<>();
        while (rs.next()) {
            result.put(new Product.Builder()
                    .id(rs.getLong("id"))
                    .byWeight(rs.getBoolean("byweight"))
                    .name(rs.getString("name"))
                    .price(rs.getDouble("price"))
                    .weight(rs.getDouble("weight"))
                    .quantity(rs.getInt("amount"))
                    .build(), rs.getDouble("total"));
        }
        return result;
    }
}
