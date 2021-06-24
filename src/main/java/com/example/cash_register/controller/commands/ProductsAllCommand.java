package com.example.cash_register.controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductsAllCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/commodity_expert/commodity_expert.jsp";
    }
}
