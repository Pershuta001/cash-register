package com.example.cash_register.controller.commands.manager;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.controller.exceptions.NoSuchProductException;
import com.example.cash_register.model.service.ReceiptService;

import javax.servlet.http.HttpServletRequest;

public class DeleteProductFromReceipt implements Command {

    private final ReceiptService receiptService;

    public DeleteProductFromReceipt(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Long productId = Long.parseLong(request.getParameter("productId"));
        Long receiptId = Long.parseLong(request.getParameter("receiptId"));


        receiptService.deleteProductFromReceipt(receiptId, productId);

        return "redirect:/app/receipt/view?id=" + receiptId;

    }
}
