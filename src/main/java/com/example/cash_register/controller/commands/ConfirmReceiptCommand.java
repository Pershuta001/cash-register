package com.example.cash_register.controller.commands;

import com.example.cash_register.model.service.ReceiptService;

import javax.servlet.http.HttpServletRequest;

public class ConfirmReceiptCommand implements Command {

    private final ReceiptService receiptService;

    public ConfirmReceiptCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        receiptService.confirmReceipt(Long.parseLong(request.getParameter("receiptId")));
        return "redirect:/cashier/create_receipt.jsp";
    }
}
