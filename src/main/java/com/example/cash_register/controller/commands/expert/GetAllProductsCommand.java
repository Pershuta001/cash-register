package com.example.cash_register.controller.commands.expert;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.service.ProductService;

import javax.servlet.http.HttpServletRequest;

public class GetAllProductsCommand implements Command {

    private final ProductService productService;

    public GetAllProductsCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String pageParam = request.getParameter("page");
        String sortParam = request.getParameter("sort");
        Integer page = pageParam == null ? 1 : Integer.parseInt(pageParam);

        if (sortParam == null)
            sortParam = "byId";

        request.setAttribute("sort", sortParam);
        request.setAttribute("pagesCount", productService.getPagesCount());
        request.setAttribute("products", productService.findAll(page, sortParam));
        request.setAttribute("page", page);
        return "/commodity_expert/products.jsp";
    }
}
