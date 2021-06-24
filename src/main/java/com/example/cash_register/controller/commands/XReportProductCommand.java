package com.example.cash_register.controller.commands;

import com.example.cash_register.model.service.ReceiptService;

import javax.servlet.http.HttpServletRequest;

public class XReportProductCommand implements Command {
    private final ReceiptService receiptService;

    public XReportProductCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String sort = request.getParameter("sort");
        sort = sort == null ? "products" : sort;
        String pageParam = request.getParameter("page");
        Integer page = pageParam == null ? 1 : Integer.parseInt(pageParam);

        request.setAttribute("pagesCount", 1);
        request.setAttribute("sort", sort);
        request.setAttribute("items", receiptService.getXReport(page, sort).get());
        request.setAttribute("page", page);
        return "/cashier_manager/x_report.jsp";
    }
}
