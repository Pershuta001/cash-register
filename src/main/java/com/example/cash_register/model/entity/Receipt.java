package com.example.cash_register.model.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Receipt extends Entity {
    private Long cashierId;
    private Date date;

    private Map<Product, Double> productsInReceipt;

    public Receipt() {
        productsInReceipt = new HashMap<>();
    }

    public Receipt(Long receiptId, Long cashierId, Date date) {
        setId(receiptId);
        this.cashierId = cashierId;
        this.date = date;
        productsInReceipt = new HashMap<>();
    }

    public Map<Product, Double> getProductsInReceipt() {
        return productsInReceipt;
    }

    public void setProductsInReceipt(Map<Product, Double> productsInReceipt) {
        this.productsInReceipt = productsInReceipt;
    }

    public void setQuantity(Product product, Double quantity){
        productsInReceipt.put(product, quantity);
    }

    public Long getCashierId() {
        return cashierId;
    }

    public void setCashierId(Long cashierId) {
        this.cashierId = cashierId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
