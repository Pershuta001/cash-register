package com.example.cash_register.controller.commands;

import com.example.cash_register.model.service.ProductService;
import com.example.cash_register.model.service.ReceiptService;

import javax.servlet.http.HttpServletRequest;

public class GetAllReceiptsCommand implements Command{
    private final ReceiptService receiptService;

    public GetAllReceiptsCommand(ReceiptService receiptService) {

        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String pageParam = request.getParameter("page");
         Integer page = pageParam == null ? 1 : Integer.parseInt(pageParam);


        //request.setAttribute("pagesCount", productService.getPagesCount());
        request.setAttribute("receipts", receiptService.findAll(page));
        request.setAttribute("page", page);
        return "/commodity_expert/products.jsp";
    }
}
