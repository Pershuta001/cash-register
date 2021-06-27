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

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Servlet extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig) {
        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        commands.put("logout", new LogoutCommand());
        commands.put("login", new LoginCommand(new UserService()));
        commands.put("registration", new RegisterCommand(new UserService()));
        commands.put("exception", new ExceptionCommand());

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

        String path = request.getRequestURI();
        System.out.println(path);
        path = path.replaceAll(".*/app/", "");
        System.out.println(path);

        Command command = commands.getOrDefault(path,
                (r) -> "/home.jsp)");
        String page = command.execute(request);
        if (page.contains("redirect:")) {
            response.sendRedirect(page.replace("redirect:", request.getContextPath()));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
