package com.example.cash_register.controller.commands.manager;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.service.ReceiptService;

import javax.servlet.http.HttpServletRequest;

public class MakeZReportCommand implements Command {

    private final ReceiptService receiptService;

    public MakeZReportCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        receiptService.makeZReport();
        return "redirect:/app/manager/z-report";
    }
}
