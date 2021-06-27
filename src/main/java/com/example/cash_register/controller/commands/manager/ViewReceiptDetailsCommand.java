package com.example.cash_register.controller.commands.manager;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.entity.Receipt;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ReceiptService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.example.cash_register.utils.Validator.isAllowed;
import static com.example.cash_register.utils.Validator.isIdValid;

public class ViewReceiptDetailsCommand implements Command {

    private final ReceiptService receiptService;

    public ViewReceiptDetailsCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (!isAllowed(request, Roles.CASHIER_MANAGER)) {
            return "redirect:/login.jsp";
        }
        String idStr = request.getParameter("id");

        if (!isIdValid(idStr)) {
            request.setAttribute("exception", "Wrong id format");
            return "/WEB-INF/exception.jsp";
        }

        Long id = Long.parseLong(idStr);

        Optional<Receipt> receiptOptional = receiptService.findById(id);

        if (!receiptOptional.isPresent())
            request.setAttribute("receiptException", "No such receipt. Please reload the page");
        else
            request.setAttribute("receipt", receiptOptional.get());

        return "/cashier_manager/edit_receipt.jsp";
    }
}
