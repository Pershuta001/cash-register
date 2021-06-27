package com.example.cash_register.controller.commands.manager;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.entity.Receipt;
import com.example.cash_register.model.service.ReceiptService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ViewReceiptDetailsCommand implements Command {

    private final ReceiptService receiptService;

    public ViewReceiptDetailsCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        Long id = Long.parseLong(request.getParameter("id"));

        Optional<Receipt> receiptOptional = receiptService.findById(id);

        if(!receiptOptional.isPresent()){
            request.setAttribute("receiptException", "No such receipt. Please reload the page");
            return "/cashier_manager/receipts.jsp";
        }

        request.setAttribute("receipt", receiptOptional.get());

        return "/cashier_manager/edit_receipt.jsp";
    }
}
