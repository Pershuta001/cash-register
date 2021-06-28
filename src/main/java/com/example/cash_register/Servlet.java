package com.example.cash_register;

import com.example.cash_register.controller.commands.*;
import com.example.cash_register.controller.commands.auth.LoginCommand;
import com.example.cash_register.controller.commands.auth.LogoutCommand;
import com.example.cash_register.controller.commands.cashier.AddProductToReceiptCommand;
import com.example.cash_register.controller.commands.cashier.ConfirmReceiptCommand;
import com.example.cash_register.controller.commands.cashier.CreateReceiptCommand;
import com.example.cash_register.controller.commands.cashier.UpdateProductAmountInReceiptCommand;
import com.example.cash_register.controller.commands.expert.CreateProductCommand;
import com.example.cash_register.controller.commands.expert.DeleteProductCommand;
import com.example.cash_register.controller.commands.expert.GetAllProductsCommand;
import com.example.cash_register.controller.commands.expert.UpdateProductCommand;
import com.example.cash_register.controller.commands.manager.*;
import com.example.cash_register.model.service.ProductService;
import com.example.cash_register.model.service.ReceiptService;
import com.example.cash_register.model.service.UserService;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class Servlet extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();

    private static final Logger log = Logger.getLogger(Servlet.class);

    public void init(ServletConfig servletConfig) {
        BasicConfigurator.configure();

        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        commands.put("logout", new LogoutCommand());
        commands.put("login", new LoginCommand(new UserService()));

        commands.put("product/create", new CreateProductCommand(new ProductService()));
        commands.put("product/all", new GetAllProductsCommand(new ProductService()));
        commands.put("product/delete", new DeleteProductCommand(new ProductService()));
        commands.put("product/update", new UpdateProductCommand(new ProductService()));

        commands.put("receipt/create", new CreateReceiptCommand(new ReceiptService()));
        commands.put("receipt/confirm", new ConfirmReceiptCommand(new ReceiptService()));
        commands.put("receipt/add/product", new AddProductToReceiptCommand(new ReceiptService()));
        commands.put("receipt/update/product", new UpdateProductAmountInReceiptCommand(new ReceiptService()));

        commands.put("manager/x-report", new XReportCommand(new ReceiptService()));
        commands.put("manager/z-report", new GetZReportCommand(new ReceiptService()));
        commands.put("manager/init/z-report", new MakeZReportCommand(new ReceiptService()));
        commands.put("manager/receipts", new GetAllReceiptsCommand(new ReceiptService()));
        commands.put("receipt/delete", new DeleteReceiptCommand(new ReceiptService()));
        commands.put("receipt/view", new ViewReceiptDetailsCommand(new ReceiptService()));
        commands.put("receipt/product/delete", new DeleteProductFromReceipt(new ReceiptService()));

        log.debug("Commands was successfully initialized");
        log.trace("Number of commands --> " + commands.size());
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("Controller starts");

        String path = request.getRequestURI();
        path = path.replaceAll(".*/app/", "");
        log.trace("Requested command:" + path);

        Command command = commands.getOrDefault(path, (r) -> "/home.jsp)");

        String page = command.execute(request);
        log.trace("Response from command:" + page);

        if (page.contains("redirect:")) {
            response.sendRedirect(page.replace("redirect:", request.getContextPath()));
            log.debug("Controller finished, now go to redirect address --> " + page);
        } else {
            request.getRequestDispatcher(page).forward(request, response);
            log.debug("Controller finished, now go to forward address --> " + page);
        }
    }
}
