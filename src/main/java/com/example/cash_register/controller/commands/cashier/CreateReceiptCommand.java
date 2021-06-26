package com.example.cash_register.controller.commands.cashier;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.entity.Receipt;
import com.example.cash_register.model.service.ReceiptService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class CreateReceiptCommand implements Command {

    private final ReceiptService receiptService;

    public CreateReceiptCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        Long userId = (Long) request.getSession().getAttribute("id");

        Optional<Receipt> unconfirmed = receiptService.getUnconfirmedCheck(userId);
        if (unconfirmed.isPresent()) {
            request.setAttribute("receipt", unconfirmed.get());
            return "/cashier/create_receipt.jsp";
        }

        Optional<Receipt> newReceipt = receiptService.createReceipt(userId);
        newReceipt.ifPresent(receipt -> request.setAttribute("receipt", receipt));
        
        return "/cashier/create_receipt.jsp";
    }
}
