package com.example.cash_register.controller.commands.cashier;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ReceiptService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.example.cash_register.utils.Validator.*;

public class UpdateProductAmountInReceiptCommand implements Command {

    private final Logger log = Logger.getLogger(UpdateProductAmountInReceiptCommand.class);
    private final ReceiptService receiptService;


    public UpdateProductAmountInReceiptCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        log.debug("UpdateProductAmountInReceiptCommand started");
        if (!isAllowed(request, Roles.CASHIER)) {
            log.error("Attempt to access forbidden area");
            return "redirect:/login.jsp";
        }

        String receiptId = request.getParameter("receiptId");
        String productId = request.getParameter("productId");
        String amountS = request.getParameter("amount");
        String oldAmountS = request.getParameter("oldAmount");

        log.trace(String.format("Obtained parameters: receiptId='%s', productId='%s', amount='%s', oldAmount='%s'",
                receiptId, productId, amountS, oldAmountS));

        if (!isIdValid(receiptId) || !isIdValid(productId)
                || !isWeightValid(amountS) || !isWeightValid(oldAmountS)) {
            request.setAttribute("exception", "Wrong data entered. ");
            log.error("Wrong data entered.");
            return "/WEB-INF/exception.jsp";
        }

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
            log.debug("Amount was successfully updated");
        } catch (Exception e) {
            request.setAttribute("exception", e.getMessage());
            log.error(e.getMessage());
            return "/WEB-INF/exception.jsp";
        }

        log.debug("UpdateProductAmountInReceiptCommand finished");
        return "redirect:/app/receipt/create";
    }
}
