package com.example.cash_register.controller.commands.cashier;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ReceiptService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.example.cash_register.utils.Validator.isAllowed;
import static com.example.cash_register.utils.Validator.isIdValid;

public class ConfirmReceiptCommand implements Command {

    private final ReceiptService receiptService;
    private final Logger log = Logger.getLogger(ConfirmReceiptCommand.class);

    public ConfirmReceiptCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        log.debug("ConfirmReceiptCommand started");

        if (!isAllowed(request, Roles.CASHIER)) {
            log.error("Attempt to access forbidden area");
            return "redirect:/login.jsp";
        }

        String recId = request.getParameter("receiptId");
        log.debug(String.format("Obtained parameter receiptId:'%s'", recId));

        if (!isIdValid(recId)) {
            request.setAttribute("exception", String.format("Wrong receipt id:'%s'", recId));
            log.error(String.format("Wrong receipt id:'%s'", recId));
            return "/WEB-INF/exception.jsp";
        }

        receiptService.confirmReceipt(Long.parseLong(recId));

        log.info(String.format("Receipt with id:'%s' was confirmed", recId));
        log.debug("ConfirmReceiptCommand finished");
        return "redirect:/cashier/create_receipt.jsp";
    }
}
