package com.example.cash_register.controller.commands.auth;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.controller.commands.CommandUtility;
import com.example.cash_register.model.entity.User;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.UserService;
import com.example.cash_register.utils.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.util.Optional;

public class LoginCommand implements Command {

    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        Roles role = (Roles) request.getSession().getAttribute("role");
        if (role != null && !role.equals(Roles.GUEST)) {
            return "/home.jsp";
        }

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (Validator.nullOrEmpty(login) || Validator.nullOrEmpty(password)
                || !userService.existsByLoginAndPassword(login, password)) {
            return "/login.jsp";
        }

        if (CommandUtility.checkUserIsLogged(request, login)) {
            return "/WEB-INF/error.jsp";
        }

        Optional<User> userOpt = userService.getUserByLoginAndPassword(login, password);
        if (!userOpt.isPresent()) {
            return "/login.jsp";
        }

        User user = userOpt.get();
        CommandUtility.setUserRoleId(request, user.getRole(), user.getId(), login);
        return "redirect:/home.jsp";
    }

}
