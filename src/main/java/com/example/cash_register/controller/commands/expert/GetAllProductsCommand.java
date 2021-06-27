package com.example.cash_register.controller.commands.expert;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ProductService;

import javax.servlet.http.HttpServletRequest;

import static com.example.cash_register.utils.Validator.isAllowed;
import static com.example.cash_register.utils.Validator.isPageValid;

public class GetAllProductsCommand implements Command {

    private final ProductService productService;

    public GetAllProductsCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        if (!isAllowed(request, Roles.COMMODITY_EXPERT)) {
            return "redirect:/login.jsp";
        }

        String pageParam = request.getParameter("page");
        String sortParam = request.getParameter("sort");

        if(!isPageValid(pageParam)){
            request.setAttribute("exception","Wrong page param format");
            return "/WEB-INF/exception.jsp";
        }

        Integer page = pageParam == null ? 1 : Integer.parseInt(pageParam);
        sortParam = sortParam == null ? "byId" : sortParam;

        request.setAttribute("sort", sortParam);
        request.setAttribute("pagesCount", productService.getPagesCount());
        request.setAttribute("products", productService.findAll(page, sortParam));
        request.setAttribute("page", page);
        return "/commodity_expert/products.jsp";
    }
}
