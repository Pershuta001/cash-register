package com.example.cash_register.controller.commands.expert;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.controller.commands.cashier.AddProductToReceiptCommand;
import com.example.cash_register.controller.exceptions.ExistingProductNameException;
import com.example.cash_register.model.dao.mapper.ProductMapper;
import com.example.cash_register.model.entity.Product;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ProductService;
import com.example.cash_register.utils.Validator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.cash_register.utils.Validator.*;

public class CreateProductCommand implements Command {

    private final Logger log = Logger.getLogger(CreateProductCommand.class);
    private final ProductService productService;

    public CreateProductCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        log.debug("Command started");

        if (!isAllowed(request, Roles.COMMODITY_EXPERT)) {
            log.error("Attempt to access forbidden area");
            return "redirect:/login.jsp";
        }

        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String amount = request.getParameter("amount");
        String byWeight = request.getParameter("byWeight");

        log.trace(String.format("Obtained parameters: name='%s', price='%s', amount='%s', byWeight='%s'",
                name, price, amount, byWeight));

        if (nullOrEmpty(name) || nullOrEmpty(price) || nullOrEmpty(amount) || nullOrEmpty(byWeight)) {
            log.debug("Entered parameters are empty");
            return "/commodity_expert/create_product.jsp";
        }

        if (!isNameValid(name) || !isPriceValid(price) || !isWeightValid(amount) || nullOrEmpty(byWeight)) {
            request.setAttribute("exception", "Wrong data entered");
            log.error("Invalid parameters entered");
            return "/WEB-INF/exception.jsp";
        }

        try {
            Product product = productService
                    .createProduct(ProductMapper.convertFromParameters(name, Double.parseDouble(price), amount, byWeight.equals("/kg")));
            request.setAttribute("crProductId", product);
            request.setAttribute("success", "Product " + name + " was successfully created");
            log.debug("Product was successfully created id='" + product.getId() + '\'');
        } catch (ExistingProductNameException e) {
            setAttributes(request);
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
        }
        log.debug("Command finished");
        return "redirect:/app/product/create";
    }

    private void setAttributes(HttpServletRequest request) {
        request.setAttribute("name", request.getParameter("name"));
        request.setAttribute("price", request.getParameter("price"));
        request.setAttribute("amount", request.getParameter("amount"));
        request.setAttribute("byWeight", request.getParameter("byWeight"));
    }
}
