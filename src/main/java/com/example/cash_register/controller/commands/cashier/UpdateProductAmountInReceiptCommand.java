package com.example.cash_register.controller.commands.cashier;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.service.ReceiptService;

import javax.servlet.http.HttpServletRequest;

public class UpdateProductAmountInReceiptCommand implements Command {

    private final ReceiptService receiptService;

    public UpdateProductAmountInReceiptCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        String receiptId = request.getParameter("receiptId");
        String productId = request.getParameter("productId");
        String amountS = request.getParameter("amount");
        String oldAmountS = request.getParameter("oldAmount");

        //todo validation

        long recId = Long.parseLong(receiptId);
        long prodId = Long.parseLong(productId);
        double amount = Double.parseDouble(amountS);
        double oldAmount = Double.parseDouble(oldAmountS);

        try {
            if (amount < oldAmount) {
                receiptService.updateReduceAmountInReceipt(recId, prodId, oldAmount - amount);
            } else {
                receiptService.updateIncreaseAmountInReceipt(recId, prodId, amount - oldAmount);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return "redirect:/app/receipt/create";
    }
}
