package com.example.cash_register.controller.commands.manager;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.service.ReceiptService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

public class GetZReportCommand implements Command {

    private final ReceiptService receiptService;

    public GetZReportCommand(ReceiptService receiptService) {

        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        // receiptService.makeZReport();

        String date = request.getParameter("date");
        String sort = request.getParameter("sort");
        String page = request.getParameter("page");

        if(sort == null)
            return "/cashier_manager/z_report.jsp";
        if (sort.equals("products")) {
            request.setAttribute("items", receiptService.getZReportByProducts(Date.valueOf(date), 1));
        } else {
            request.setAttribute("items", receiptService.getZReportByCashiers(Date.valueOf(date), 1));
        }
        request.setAttribute("sort", sort);
        return "/cashier_manager/z_report.jsp";
    }


}
