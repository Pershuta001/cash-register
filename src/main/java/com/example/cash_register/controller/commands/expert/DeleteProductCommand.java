package com.example.cash_register.controller.commands.expert;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.service.ProductService;

import javax.servlet.http.HttpServletRequest;

public class DeleteProductCommand implements Command {

    private final ProductService productService;

    public DeleteProductCommand(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        productService.deleteProduct(id);

        Integer page = Integer.parseInt(request.getParameter("page"));
        String sort = request.getParameter("sort");
        request.setAttribute("sort", sort);
        request.setAttribute("pagesCount", productService.getPagesCount());
        request.setAttribute("page", page);
        request.setAttribute("products", productService.findAll(page, sort));

        return "/commodity_expert/products.jsp";
    }
}
