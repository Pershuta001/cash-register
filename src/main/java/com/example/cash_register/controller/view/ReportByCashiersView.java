package com.example.cash_register.controller.view;

public class ReportByCashiersView {

    private Long id;
    private String name;
    private String surname;
    private Integer numberOfReceipts;
    private Double totalPrice;

    public ReportByCashiersView(Long id, String name, String surname, Integer numberOfReceipts, Double totalPrice) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.numberOfReceipts = numberOfReceipts;
        this.totalPrice = totalPrice;
    }

    public ReportByCashiersView() {

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getNumberOfReceipts() {
        return numberOfReceipts;
    }

    public void setNumberOfReceipts(Integer numberOfReceipts) {
        this.numberOfReceipts = numberOfReceipts;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public static class Builder{

        private ReportByCashiersView report;

        public Builder(){
            report = new ReportByCashiersView();
        }

        public Builder id(Long id){
            report.id = id;
            return this;
        }

        public Builder name(String name){
            report.name = name;
            return this;
        }

        public Builder surname(String surname){
            report.surname = surname;
            return this;
        }
        public Builder numberOfReceipts(Integer numberOfReceipts){
            report.numberOfReceipts = numberOfReceipts;
            return this;
        }

        public Builder totalPrice(Double price){
            report.totalPrice = price;
            return this;
        }

        public ReportByCashiersView build(){
            return report;
        }
    }
}
