package com.example.cash_register.controller.commands.manager;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ReceiptService;

import javax.servlet.http.HttpServletRequest;

import static com.example.cash_register.utils.Validator.isAllowed;
import static com.example.cash_register.utils.Validator.isIdValid;

public class DeleteReceiptCommand implements Command {
    private final ReceiptService receiptService;

    public DeleteReceiptCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        if (!isAllowed(request, Roles.CASHIER_MANAGER)) {
            return "redirect:/login.jsp";
        }

        String idStr = request.getParameter("id");

        if (!isIdValid(idStr)) {
            request.setAttribute("exception", "Wrong id format");
            return "/WEB-INF/exception.jsp";
        }
        Long id = Long.parseLong(idStr);
        receiptService.deleteReceipt(id);

        //  Integer page = Integer.parseInt(request.getParameter("page"));
        //String sort = request.getParameter("sort");
        //       request.setAttribute("sort", sort);
//        request.setAttribute("pagesCount", receiptService.getPagesCount());
//        request.setAttribute("page", page);
//        request.setAttribute("products", receiptService.findAll(page, sort));

        return "redirect:/app/manager/receipts";
    }
}
