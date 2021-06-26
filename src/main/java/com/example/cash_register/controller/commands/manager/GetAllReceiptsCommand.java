package com.example.cash_register.controller.commands.manager;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.entity.Receipt;
import com.example.cash_register.model.service.ProductService;
import com.example.cash_register.model.service.ReceiptService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GetAllReceiptsCommand implements Command {
    private final ReceiptService receiptService;

    public GetAllReceiptsCommand(ReceiptService receiptService) {

        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String pageParam = request.getParameter("page");
         Integer page = pageParam == null ? 1 : Integer.parseInt(pageParam);


        //request.setAttribute("pagesCount", productService.getPagesCount());
        List<Receipt> receiptList = receiptService.findAll(page);
        request.setAttribute("receipts",receiptList );
        request.setAttribute("page", page);
        return "/cashier_manager/receipts.jsp";
    }
}
