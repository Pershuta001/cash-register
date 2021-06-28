package com.example.cash_register.controller.commands.cashier;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.entity.Receipt;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ReceiptService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.example.cash_register.utils.Validator.isAllowed;

public class CreateReceiptCommand implements Command {

    private final Logger log = Logger.getLogger(CreateReceiptCommand.class);
    private final ReceiptService receiptService;

    public CreateReceiptCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        log.debug("CreateReceiptCommand started");
        if (!isAllowed(request, Roles.CASHIER)) {
            log.error("Attempt to access forbidden area");
            return "redirect:/login.jsp";
        }

        Long userId = (Long) request.getSession().getAttribute("id");
        log.trace(String.format("User id from session: '%s'", userId));

        Optional<Receipt> unconfirmed = receiptService.getUnconfirmedCheck(userId);

        if (unconfirmed.isPresent()) {
            request.setAttribute("receipt", unconfirmed.get());
            log.trace(String.format("Found unconfirmed receipt with id:'%s'  for user with id:'%s'",
                    unconfirmed.get().getId(), userId));
        } else {
            Optional<Receipt> newReceipt = receiptService.createReceipt(userId);
            newReceipt.ifPresent(receipt -> request.setAttribute("receipt", receipt));
            log.trace(String.format("Created new receipt with id:'%s'  for user with id:'%s'",
                    newReceipt.get().getId(), userId));
        }
        log.debug("CreateReceiptCommand finished");
        return "/cashier/create_receipt.jsp";
    }
}
