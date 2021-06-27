package com.example.cash_register.controller.commands.cashier;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ReceiptService;

import javax.servlet.http.HttpServletRequest;

import static com.example.cash_register.utils.Validator.isAllowed;
import static com.example.cash_register.utils.Validator.isIdValid;

public class ConfirmReceiptCommand implements Command {

    private final ReceiptService receiptService;

    public ConfirmReceiptCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        if (!isAllowed(request, Roles.CASHIER)) {
            return "redirect:/login.jsp";
        }

        String recId = request.getParameter("receiptId");
        if(!isIdValid(recId)){
            request.setAttribute("exception", "Wrong receipt id: '" + recId + '\'');
            return "/WEB-INF/exception.jsp";
        }

        receiptService.confirmReceipt(Long.parseLong(recId));
        return "redirect:/cashier/create_receipt.jsp";
    }
}
