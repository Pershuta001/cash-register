package com.example.cash_register.controller.commands.manager;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.service.ReceiptService;

import javax.servlet.http.HttpServletRequest;

public class DeleteReceiptCommand implements Command {
    private final ReceiptService receiptService;

    public DeleteReceiptCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
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
