package com.example.cash_register.controller.commands.auth;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.model.enums.Roles;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");
        String login = (String) request.getSession().getAttribute("login");
        loggedUsers.remove(login);

        request.getSession().removeAttribute("login");
        request.getSession().setAttribute("role", Roles.GUEST);

        return "redirect:/home.jsp";
    }
}
