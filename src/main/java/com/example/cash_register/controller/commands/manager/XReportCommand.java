package com.example.cash_register.controller.commands.manager;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ReceiptService;

import javax.servlet.http.HttpServletRequest;

import static com.example.cash_register.utils.Validator.isAllowed;
import static com.example.cash_register.utils.Validator.isPageValid;

public class XReportCommand implements Command {
    private final ReceiptService receiptService;

    public XReportCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        if (!isAllowed(request, Roles.CASHIER_MANAGER)) {
            return "redirect:/login.jsp";
        }

        String sort = request.getParameter("sort");
        String pageParam = request.getParameter("page");

        if (!isPageValid(pageParam)) {
            request.setAttribute("exception", "Wrong page format");
            return "/WEB-INF/exception.jsp";
        }

        sort = sort == null ? "products" : sort;
        Integer page = pageParam == null ? 1 : Integer.parseInt(pageParam);

        request.setAttribute("pagesCount", 1);
        request.setAttribute("sort", sort);
        if (sort.equals("cashiers"))
            request.setAttribute("items", receiptService.getXReportByCashiers(page));
        else {
            request.setAttribute("items", receiptService.getXReportByProducts(page));
        }
        request.setAttribute("page", page);
        return "/cashier_manager/x_report.jsp";
    }
}
