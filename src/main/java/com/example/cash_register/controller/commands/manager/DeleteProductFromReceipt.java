package com.example.cash_register.controller.commands.manager;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.controller.exceptions.NoSuchProductException;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ReceiptService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.example.cash_register.utils.Validator.isAllowed;
import static com.example.cash_register.utils.Validator.isIdValid;

public class DeleteProductFromReceipt implements Command {

    private final Logger log = Logger.getLogger(DeleteProductFromReceipt.class);
    private final ReceiptService receiptService;

    public DeleteProductFromReceipt(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        log.debug("Command started");

        if (!isAllowed(request, Roles.CASHIER_MANAGER)) {
            log.error("Attempt to access forbidden area");
            return "redirect:/login.jsp";
        }

        String prodIdStr = request.getParameter("productId");
        String recIdStr = request.getParameter("receiptId");

        log.trace(String.format("Obtained parameters: productId='%s', receiptId='%s'",
                prodIdStr, recIdStr));

        if (!isIdValid(prodIdStr) || !isIdValid(recIdStr)) {
            request.setAttribute("exception", "Wrong id format while deleting");
            log.error("Wrong parameters entered");
            return "/WEB-INF/exception.jsp";
        }

        Long productId = Long.parseLong(prodIdStr);
        Long receiptId = Long.parseLong(recIdStr);

        receiptService.deleteProductFromReceipt(receiptId, productId);
        log.info(String.format("Products with id='%s' was removed from receipt with id='%s'", productId, receiptId));

        log.debug("Command finished");
        return "redirect:/app/receipt/view?id=" + receiptId;

    }
}
