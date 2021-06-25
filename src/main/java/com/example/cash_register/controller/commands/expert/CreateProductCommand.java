package com.example.cash_register.controller.commands.expert;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.dao.mapper.ProductMapper;
import com.example.cash_register.model.entity.Product;
import com.example.cash_register.model.service.ProductService;
import com.example.cash_register.utils.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.cash_register.utils.Validator.nullOrEmpty;

public class CreateProductCommand implements Command {

    private final ProductService productService;

    public CreateProductCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String amount = request.getParameter("amount");
        String byWeight = request.getParameter("byWeight");

        //todo validation of the fields
        if (nullOrEmpty(name) || nullOrEmpty(price) || nullOrEmpty(amount) || nullOrEmpty(byWeight)) {
            return "/commodity_expert/create_product.jsp";
        }

        Product product = productService
                .createProduct(ProductMapper.convertFromParameters(name, Double.parseDouble(price), amount, byWeight.equals("/kg")));

        request.setAttribute("crProductId", product);
        return "/commodity_expert/create_product.jsp";
    }
}
