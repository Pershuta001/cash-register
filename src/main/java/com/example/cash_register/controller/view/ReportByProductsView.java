package com.example.cash_register.controller.view;

public class ReportByProductsView {

    private Long id;
    private String name;
    private Double price;
    private Double soldAmount;
    private Double totalPrice;

    public ReportByProductsView() {
    }

    public ReportByProductsView(Long id, String name, Double price, Double soldAmount, Double totalPrice) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.soldAmount = soldAmount;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSoldAmount() {
        return soldAmount;
    }

    public void setSoldAmount(Double soldAmount) {
        this.soldAmount = soldAmount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public static class Builder {
        private final ReportByProductsView report;

        public Builder() {
            report = new ReportByProductsView();
        }

        public Builder id(Long id) {
            report.id = id;
            return this;
        }

        public Builder name(String name) {
            report.name = name;
            return this;
        }

        public Builder price(Double price) {
            report.price = price;
            return this;
        }

        public Builder soldAmount(Double amount) {
            report.soldAmount = amount;
            return this;
        }

        public Builder totalPrice(Double totalPrice) {
            report.totalPrice = totalPrice;
            return this;
        }

        public ReportByProductsView build() {
            return report;
        }
    }
}