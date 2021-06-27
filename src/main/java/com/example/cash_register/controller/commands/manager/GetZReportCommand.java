package com.example.cash_register.controller.commands.manager;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ReceiptService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

import static com.example.cash_register.utils.Validator.*;

public class GetZReportCommand implements Command {

    private final ReceiptService receiptService;

    public GetZReportCommand(ReceiptService receiptService) {

        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (!isAllowed(request, Roles.CASHIER_MANAGER)) {
            return "redirect:/login.jsp";
        }

        String date = request.getParameter("date");
        String sort = request.getParameter("sort");
        String pageS = request.getParameter("page");

        if (nullOrEmpty(sort) || nullOrEmpty(date) || !isPageValid(pageS)) {
            return "/cashier_manager/z_report.jsp";
        }
        int page = pageS == null ? 1 : Integer.parseInt(pageS);

        if (sort.equals("products")) {
            request.setAttribute("items", receiptService.getZReportByProducts(Date.valueOf(date), page));
        } else {
            request.setAttribute("items", receiptService.getZReportByCashiers(Date.valueOf(date), page));
        }
        request.setAttribute("sort", sort);
        return "/cashier_manager/z_report.jsp";
    }


}
