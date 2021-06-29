package com.example.cash_register.controller.commands.manager;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.entity.Receipt;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ProductService;
import com.example.cash_register.model.service.ReceiptService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.cash_register.utils.Validator.isAllowed;
import static com.example.cash_register.utils.Validator.isPageValid;

public class GetAllReceiptsCommand implements Command {

    private final Logger log = Logger.getLogger(GetAllReceiptsCommand.class);
    private final ReceiptService receiptService;

    public GetAllReceiptsCommand(ReceiptService receiptService) {

        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        log.debug("Command starts");

        if (!isAllowed(request, Roles.CASHIER_MANAGER)) {
            log.error("Attempt to access forbidden area");
            return "redirect:/login.jsp";
        }

        String pageParam = request.getParameter("page");
        log.trace(String.format("Obtained parameters: page='%s'", pageParam));

        if (!isPageValid(pageParam)) {
            request.setAttribute("exception", "Wrong page format");
            log.error("Invalid parameters entered");
            return "/WEB-INF/exception.jsp";
        }

        Integer page = pageParam == null ? 1 : Integer.parseInt(pageParam);

        List<Receipt> receiptList = receiptService.findAll(page);
        request.setAttribute("pagesCount", receiptService.getPagesCount("receipts"));
        request.setAttribute("receipts", receiptList);
        request.setAttribute("page", page);

        log.debug("Command finished");
        return "/cashier_manager/receipts.jsp";
    }
}
