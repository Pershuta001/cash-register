package com.example.cash_register.controller.commands.manager;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.controller.exceptions.NoSuchProductException;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ReceiptService;

import javax.servlet.http.HttpServletRequest;

import static com.example.cash_register.utils.Validator.isAllowed;
import static com.example.cash_register.utils.Validator.isIdValid;

public class DeleteProductFromReceipt implements Command {

    private final ReceiptService receiptService;

    public DeleteProductFromReceipt(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (!isAllowed(request, Roles.CASHIER_MANAGER)) {
            return "redirect:/login.jsp";
        }

        String prodIdStr = request.getParameter("productId");
        String recIdStr = request.getParameter("receiptId");

        if (!isIdValid(prodIdStr) || !isIdValid(recIdStr)) {
            request.setAttribute("exception", "Wrong id format while deleting");
            return "/WEB-INF/exception.jsp";
        }

        Long productId = Long.parseLong(prodIdStr);
        Long receiptId = Long.parseLong(recIdStr);

        receiptService.deleteProductFromReceipt(receiptId, productId);

        return "redirect:/app/receipt/view?id=" + receiptId;

    }
}
