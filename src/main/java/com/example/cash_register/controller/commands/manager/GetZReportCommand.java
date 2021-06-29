package com.example.cash_register.controller.commands.manager;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.ReceiptService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

import static com.example.cash_register.utils.Validator.*;

public class GetZReportCommand implements Command {

    private final Logger log = Logger.getLogger(GetZReportCommand.class);
    private final ReceiptService receiptService;

    public GetZReportCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        log.debug("Command started");

        if (!isAllowed(request, Roles.CASHIER_MANAGER)) {
            log.error("Attempt to access forbidden area");
            return "redirect:/login.jsp";
        }

        String date = request.getParameter("date");
        String sort = request.getParameter("sort");
        String pageS = request.getParameter("page");

        log.trace(String.format("Obtained parameter: date='%s, sort='%s', page='%s'",
                date, sort, pageS));


        if (nullOrEmpty(sort) || nullOrEmpty(date) || !isPageValid(pageS)) {
            log.error("Invalid parameters entered");
            return "/cashier_manager/z_report.jsp";
        }
        int page = pageS == null ? 1 : Integer.parseInt(pageS);

        if (sort.equals("products")) {
            request.setAttribute("items", receiptService.getZReportByProducts(Date.valueOf(date), page));
        } else {
            request.setAttribute("items", receiptService.getZReportByCashiers(Date.valueOf(date), page));
        }

        log.info("Executed with parameter: " + sort);
        request.setAttribute("sort", sort);
        request.setAttribute("page", page);
        request.setAttribute("date", date);
        request.setAttribute("pagesCount", receiptService.getZPagesCount('z' + sort, Date.valueOf(date)));

        log.debug("Command finished");
        return "/cashier_manager/z_report.jsp";
    }


}
