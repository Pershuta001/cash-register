package com.example.cash_register.controller.commands.expert;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.example.cash_register.utils.Validator.*;

public class DeleteProductCommand implements Command {

    private final Logger log = Logger.getLogger(DeleteProductCommand.class);

    private final ProductService productService;

    public DeleteProductCommand(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public String execute(HttpServletRequest request) {

        log.debug("Command started");

        if (!isAllowed(request, Roles.COMMODITY_EXPERT)) {
            log.error("Attempt to access forbidden area");
            return "redirect:/login.jsp";
        }

        String idStr = request.getParameter("id");
        String pageStr = request.getParameter("page");
        String sort = request.getParameter("sort");

        log.trace(String.format("Obtained parameters: id='%s', page='%s', sort='%s'",
                idStr, pageStr, sort));

        if (!isIdValid(idStr) || !isIdValid(pageStr) || nullOrEmpty(sort)) {
            request.setAttribute("exception", "Wrong data entered");
            log.error("Invalid parameters entered");
            return "/WEB-INF/exception.jsp";
        }

        Long id = Long.parseLong(idStr);
        productService.deleteProduct(id);
        log.info("Product with id='" + id + "' was successfully deleted");

        Integer page = Integer.parseInt(pageStr);

        request.setAttribute("sort", sort);
        request.setAttribute("pagesCount", productService.getPagesCount());
        request.setAttribute("page", page);
        request.setAttribute("products", productService.findAll(page, sort));

        log.debug("Command finished");

        return "redirect:/app/product/all";
    }
}
