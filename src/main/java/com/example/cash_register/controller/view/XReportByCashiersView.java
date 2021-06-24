package com.example.cash_register.controller.view;

import com.example.cash_register.model.entity.User;

public class XReportByCashiersView {

    private User user;
    private Integer numberOfReceipts;
    private Double totalSum;

    public XReportByCashiersView(User user, Integer numberOfReceipts, Double totalSum) {
        this.user = user;
        this.numberOfReceipts = numberOfReceipts;
        this.totalSum = totalSum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getNumberOfReceipts() {
        return numberOfReceipts;
    }

    public void setNumberOfReceipts(Integer numberOfReceipts) {
        this.numberOfReceipts = numberOfReceipts;
    }

    public Double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Double totalSum) {
        this.totalSum = totalSum;
    }
}
