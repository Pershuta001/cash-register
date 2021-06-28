package com.example.cash_register.controller.commands.manager;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ReceiptService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.example.cash_register.utils.Validator.isAllowed;

public class MakeZReportCommand implements Command {

    private final Logger log = Logger.getLogger(MakeZReportCommand.class);
    private final ReceiptService receiptService;

    public MakeZReportCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        log.debug("Command started");

        if (!isAllowed(request, Roles.CASHIER_MANAGER)) {
            log.error("Attempt to access forbidden data");
            return "redirect:/login.jsp";
        }

        receiptService.makeZReport();
        log.debug("Command finished");
        return "redirect:/app/manager/z-report";
    }
}
