package com.example.cash_register.controller.commands.expert;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.entity.Product;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.example.cash_register.utils.Validator.*;

public class UpdateProductCommand implements Command {
    private final ProductService productService;

    public UpdateProductCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        if (!isAllowed(request, Roles.COMMODITY_EXPERT)) {
            return "redirect:/login.jsp";
        }

        String idStr = request.getParameter("id");
        String amountStr = request.getParameter("amount");
        String pageStr = request.getParameter("page");
        String sortStr = request.getParameter("sort");

        if (!isIdValid(idStr) || !isWeightValid(amountStr) || !isIdValid(pageStr) || nullOrEmpty(sortStr)) {
            request.setAttribute("error", "Wrong data entered");
            return "/WEB-INF/error.jsp";
        }

        Long id = Long.parseLong(idStr);
        Optional<Product> product = productService.findById(id);

        if (!product.isPresent()) {
            request.setAttribute("exception", "invalid product ID. perhaps it is already deleted");
            return "/WEB-INF/exception.jsp";
        }
        productService.updateAmount(product.get().isByWeight(), amountStr, id);
        product = productService.findById(id);

        request.setAttribute("product", product.get());
        addPageParams(request);

        return String.format("redirect:/app/product/all?page=%s&sort=%s", pageStr, sortStr);

    }

    private void addPageParams(HttpServletRequest request) {
        request.setAttribute("sort", request.getParameter("sort"));
        request.setAttribute("page", Integer.parseInt(request.getParameter("page")));
        request.setAttribute("pagesCount", Integer.parseInt(request.getParameter("pagesCount")));
    }

}
