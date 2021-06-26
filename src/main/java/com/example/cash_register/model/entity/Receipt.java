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

    public Double getTotalPrice(){
        return productsInReceipt.entrySet()
                .stream()
                .map(p -> p.getKey().getPrice()*p.getValue())
                .reduce(0.,Double::sum);
    }

    public static class Builder{

        private final Receipt receipt;

        public Builder() {
            receipt = new Receipt();
        }

        public Receipt.Builder id(Long id) {
            receipt.setId(id);
            return this;
        }
        public Receipt.Builder cashierId(Long id) {
            receipt.cashierId = id;
            return this;
        }
        public Receipt.Builder date(Date date) {
            receipt.date = date;
            return this;
        }
        public Receipt.Builder productsInReceipt(Map<Product, Double> productsInReceipt) {
            receipt.productsInReceipt = productsInReceipt;
            return this;
        }

        public Receipt build(){
            return receipt;
        } 
    }
}
