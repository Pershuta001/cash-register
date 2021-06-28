package com.example.cash_register.controller.commands.expert;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.example.cash_register.utils.Validator.isAllowed;
import static com.example.cash_register.utils.Validator.isPageValid;

public class GetAllProductsCommand implements Command {

    private final Logger log = Logger.getLogger(GetAllProductsCommand.class);
    private final ProductService productService;

    public GetAllProductsCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        log.debug("Command started");

        if (!isAllowed(request, Roles.COMMODITY_EXPERT)) {
            log.error("Attempt to access forbidden area");
            return "redirect:/login.jsp";
        }

        String pageParam = request.getParameter("page");
        String sortParam = request.getParameter("sort");

        log.trace(String.format("Obtained parameters: page='%s', sort='%s'",
                pageParam, sortParam));

        if (!isPageValid(pageParam)) {
            request.setAttribute("exception", "Wrong page param format");
            log.error("Invalid parameter entered");
            return "/WEB-INF/exception.jsp";
        }

        Integer page = pageParam == null ? 1 : Integer.parseInt(pageParam);
        sortParam = sortParam == null ? "byId" : sortParam;

        log.trace(String.format("Formatted parameters: page='%s', sort='%s'",
                page, sortParam));

        request.setAttribute("sort", sortParam);
        request.setAttribute("pagesCount", productService.getPagesCount());
        request.setAttribute("products", productService.findAll(page, sortParam));
        request.setAttribute("page", page);

        log.debug("Command finished");

        return "/commodity_expert/products.jsp";
    }
}
