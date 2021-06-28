package com.example.cash_register.controller.commands.expert;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ProductService;

import javax.servlet.http.HttpServletRequest;

import static com.example.cash_register.utils.Validator.*;

public class DeleteProductCommand implements Command {

    private final ProductService productService;

    public DeleteProductCommand(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public String execute(HttpServletRequest request) {

        if (!isAllowed(request, Roles.COMMODITY_EXPERT)) {
            return "redirect:/login.jsp";
        }

        String idStr = request.getParameter("id");
        String pageStr = request.getParameter("page");
        String sort = request.getParameter("sort");

        if(!isIdValid(idStr)||!isIdValid(pageStr)||nullOrEmpty(sort)){
            request.setAttribute("exception", "Wrong data entered");
            return "/WEB-INF/exception.jsp";
        }

        Long id = Long.parseLong(idStr);
        productService.deleteProduct(id);

        Integer page = Integer.parseInt(pageStr);

        request.setAttribute("sort", sort);
        request.setAttribute("pagesCount", productService.getPagesCount());
        request.setAttribute("page", page);
        request.setAttribute("products", productService.findAll(page, sort));

        return "redirect:/app/product/all";
    }
}
