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

import static com.example.cash_register.utils.Validator.isLoginValid;
import static com.example.cash_register.utils.Validator.nullOrEmpty;

public class LoginCommand implements Command {

    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        Roles role = (Roles) request.getSession().getAttribute("role");
        if (role != null && !role.equals(Roles.GUEST)) {
            return "redirect:/home.jsp";
        }

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (nullOrEmpty(login) || nullOrEmpty(password)) {
            return "/login.jsp";
        }

        if (!isLoginValid(login)) {
            request.setAttribute("exception", "Wrong login format");
            return "/WEB-INF/exception.jsp";
        }

        Optional<User> userOpt = userService.getUserByLoginAndPassword(login, password);
        if (!userOpt.isPresent()) {
            return "/login.jsp";
        }
        if (CommandUtility.checkUserIsLogged(request, login)) {
            request.setAttribute("exception", "You have been already logged in. Please log out first");
            return "/WEB-INF/exception.jsp";
        }

        User user = userOpt.get();
        CommandUtility.setUserRoleId(request, user.getRole(), user.getId(), login);
        return "redirect:/home.jsp";
    }

}
