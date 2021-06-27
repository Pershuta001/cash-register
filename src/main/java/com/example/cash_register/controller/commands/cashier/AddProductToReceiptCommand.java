package com.example.cash_register.controller.commands.cashier;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.entity.Receipt;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ReceiptService;
import com.example.cash_register.utils.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.example.cash_register.utils.Validator.*;

public class AddProductToReceiptCommand implements Command {

    private final ReceiptService receiptService;

    public AddProductToReceiptCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        if (!isAllowed(request, Roles.CASHIER)) {
            return "redirect:/login.jsp";
        }

        String receiptId = request.getParameter("receiptId");
        String productNameOrId = request.getParameter("name");
        String amount = request.getParameter("amount");


        if(!isIdValid(receiptId)||!isNameValid(productNameOrId)||!isWeightValid(amount)){
            request.setAttribute("exception", "Wrong data entered. Please correct it.");
            return "/cashier/create_receipt.jsp";
        }

        Long id = Long.parseLong(receiptId);
        Optional<Receipt> currentReceipt = receiptService.getReceiptById(id);
        try {
            receiptService.addProductInReceipt(
                    id,
                    productNameOrId,
                    Double.parseDouble(amount));
            currentReceipt = receiptService.getReceiptById(id);
        } catch (Exception e) {
            switch (e.getMessage()) {
                case "name ex":
                    request.setAttribute("nameError", "No product with such name or id");
                    break;
                case "amount ex":
                    request.setAttribute("amountError", "Not enough products in store");
                    break;
                default:
                    request.setAttribute("unknown error", e.getMessage());
            }
        }

        request.setAttribute("receipt", currentReceipt.get());
        return "/cashier/create_receipt.jsp";
    }
}
