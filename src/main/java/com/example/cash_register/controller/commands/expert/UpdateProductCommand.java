package com.example.cash_register.controller.commands.expert;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.entity.Product;
import com.example.cash_register.model.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class UpdateProductCommand implements Command {
    private final ProductService productService;

    public UpdateProductCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        Optional<Product> product = productService.findById(id);

        if (!product.isPresent()) {
            throw new IllegalArgumentException("invalid product ID. perhaps it is already deleted");
        }

        request.setAttribute("product", product.get());
        addPageParams(request);

        if (request.getParameter("amount") == null) {
            return String.format("redirect:/app/product/all?page=%s&sort=%s", request.getParameter("page"), request.getParameter("sort"));
        }
        productService.updateAmount(product.get().isByWeight(), request.getParameter("amount"), id);
        product = productService.findById(id);
        request.setAttribute("product", product.get());
        return String.format("redirect:/app/product/all?page=%s&sort=%s", request.getParameter("page"), request.getParameter("sort"));

    }

    private void addPageParams(HttpServletRequest request) {
        request.setAttribute("sort", request.getParameter("sort"));
        request.setAttribute("page", Integer.parseInt(request.getParameter("page")));
        request.setAttribute("pagesCount", Integer.parseInt(request.getParameter("pagesCount")));
    }

}
