package com.example.cash_register.controller.commands.auth;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.enums.Roles;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

public class LogoutCommand implements Command {
    private final Logger log = Logger.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        log.debug("Command starts");
        @SuppressWarnings("unchecked")
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");
        String login = (String) request.getSession().getAttribute("login");
        loggedUsers.remove(login);

        request.getSession().removeAttribute("login");
        request.getSession().setAttribute("role", Roles.GUEST);
        log.debug("Command finished");
        return "redirect:/home.jsp";
    }
}
