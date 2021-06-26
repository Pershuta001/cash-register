package com.example.cash_register.model.entity;

import java.util.Currency;

public class Product extends Entity {

    private String name;
    private Integer available_quantity;
    private boolean byWeight;
    private Double available_weight;
    private double price;

    public Product() {
    }

    public Product(Long id,
                   String name,
                   double price,
                   Integer available_quantity,
                   boolean byWeight,
                   Double available_weight
    ) {
        setId(id);
        this.name = name;
        this.available_quantity = available_quantity;
        this.byWeight = byWeight;
        this.available_weight = available_weight;
        this.price = price;
    }


    public Product(String name,
                   double price,
                   Integer available_quantity,
                   boolean byWeight,
                   Double available_weight) {
        this.name = name;
        this.available_quantity = available_quantity;
        this.byWeight = byWeight;
        this.available_weight = available_weight;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAvailableQuantity() {
        return available_quantity;
    }

    public void setAvailableQuantity(Integer available_quantity) {
        this.available_quantity = available_quantity;
    }

    public boolean isByWeight() {
        return byWeight;
    }

    public void setByWeight(boolean byWeight) {
        this.byWeight = byWeight;
    }

    public Double getAvailableWeight() {
        return available_weight;
    }

    public void setAvailableWeight(Double available_weight) {
        this.available_weight = available_weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public static class Builder {
        private Product product;

        public Builder() {
            product = new Product();
        }

        public Builder id(Long id) {
            product.setId(id);
            return this;
        }

        public Builder name(String name) {
            product.name = name;
            return this;
        }

        public Builder price(Double price) {
            product.price = price;
            return this;
        }

        public Builder quantity(Integer quantity) {
            product.available_quantity = quantity;
            return this;
        }

        public Builder byWeight(boolean byWeight) {
            product.byWeight = byWeight;
            return this;
        }

        public Builder weight(Double weight) {
            product.available_weight = weight;
            return this;
        }

        public Product build() {
            return product;
        }

    }
}
