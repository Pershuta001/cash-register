package com.example.cash_register.controller.commands.manager;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ReceiptService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.example.cash_register.utils.Validator.isAllowed;
import static com.example.cash_register.utils.Validator.isIdValid;

public class DeleteReceiptCommand implements Command {

    private final Logger log = Logger.getLogger(DeleteReceiptCommand.class);
    private final ReceiptService receiptService;

    public DeleteReceiptCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        log.debug("Command started");

        if (!isAllowed(request, Roles.CASHIER_MANAGER)) {
            log.error("Attempt to access forbidden area");
            return "redirect:/login.jsp";
        }

        String idStr = request.getParameter("id");
        log.trace(String.format("Obtained parameter: id='%s'", idStr));

        if (!isIdValid(idStr)) {
            request.setAttribute("exception", "Wrong id format");
            log.error("Invalid parameter format");
            return "/WEB-INF/exception.jsp";
        }
        Long id = Long.parseLong(idStr);
        receiptService.deleteReceipt(id);
        log.info(String.format("Receipt with id='%s' was successfully deleted", id));
        //  Integer page = Integer.parseInt(request.getParameter("page"));
        //String sort = request.getParameter("sort");
        //       request.setAttribute("sort", sort);
//        request.setAttribute("pagesCount", receiptService.getPagesCount());
//        request.setAttribute("page", page);
//        request.setAttribute("products", receiptService.findAll(page, sort));

        log.debug("Command finished");
        return "redirect:/app/manager/receipts";
    }
}
