package com.example.cash_register.controller.commands;

import com.example.cash_register.model.entity.Product;
import com.example.cash_register.model.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class UpdateProductAmountCommand implements Command {

    private final ProductService productService;

    public UpdateProductAmountCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        Product product3 = (Product) request.getAttribute("product");

        Long id = Long.parseLong(request.getParameter("id"));
        Optional<Product> product = productService.findById(id);

        if (!product.isPresent()) {
            throw new IllegalArgumentException("invalid product ID . perhaps it is already deleted");
        }
        request.setAttribute("product", product.get());

        if (request.getParameter("amount") == null) {
            return "/commodity_expert/updateAmount.jsp";
        }
        productService.updateAmount(product.get().isByWeight(), request.getParameter("amount"), id);
        product = productService.findById(id);
        request.setAttribute("product", product.get());

        return "/commodity_expert/updateAmount.jsp";
    }
}
