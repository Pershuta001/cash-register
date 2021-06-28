package com.example.cash_register.controller.commands.expert;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.entity.Product;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.example.cash_register.utils.Validator.*;

public class UpdateProductCommand implements Command {

    private final Logger log = Logger.getLogger(UpdateProductCommand.class);
    private final ProductService productService;

    public UpdateProductCommand(ProductService productService) {
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
        String amountStr = request.getParameter("amount");
        String pageStr = request.getParameter("page");
        String sortStr = request.getParameter("sort");


        log.trace(String.format("Obtained parameters: id='%s', amount='%s', page='%s', sort='%s'",
                idStr, amountStr, pageStr, sortStr));

        if (!isIdValid(idStr) || !isWeightValid(amountStr) || !isIdValid(pageStr) || nullOrEmpty(sortStr)) {
            request.setAttribute("error", "Wrong data entered");
            log.error("Invalid data entered");
            return "/WEB-INF/error.jsp";
        }

        Long id = Long.parseLong(idStr);
        Optional<Product> product = productService.findById(id);

        if (!product.isPresent()) {
            request.setAttribute("exception", "invalid product ID. perhaps it is already deleted");
            log.error("No product with id='" + id + "'");
            return "/WEB-INF/exception.jsp";
        }
        productService.updateAmount(product.get().isByWeight(), amountStr, id);
        log.info(String.format("Amount of product with id='%s' was updated", id));
        product = productService.findById(id);

        request.setAttribute("product", product.get());
        addPageParams(request);

        log.debug("Command finished");
        return String.format("redirect:/app/product/all?page=%s&sort=%s", pageStr, sortStr);

    }

    private void addPageParams(HttpServletRequest request) {
        request.setAttribute("sort", request.getParameter("sort"));
        request.setAttribute("page", Integer.parseInt(request.getParameter("page")));
        request.setAttribute("pagesCount", Integer.parseInt(request.getParameter("pagesCount")));
    }

}
