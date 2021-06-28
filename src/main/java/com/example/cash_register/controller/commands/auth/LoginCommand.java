package com.example.cash_register.controller.commands.auth;

import com.example.cash_register.controller.commands.Command;
import com.example.cash_register.controller.commands.CommandUtility;
import com.example.cash_register.model.entity.User;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.example.cash_register.utils.Validator.isLoginValid;
import static com.example.cash_register.utils.Validator.nullOrEmpty;

public class LoginCommand implements Command {

    private final UserService userService;

    private final Logger log = Logger.getLogger(LoginCommand.class);

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        log.debug("LoginCommand starts");

        Roles role = (Roles) request.getSession().getAttribute("role");
        if (role != null && !role.equals(Roles.GUEST)) {
            log.trace("User :" + request.getSession().getAttribute("id")
                    + " is already logged in current session");
            return "redirect:/home.jsp";
        }

        String login = request.getParameter("login");
        log.trace("Obtained login parameter: '" + login + '\'');

        String password = request.getParameter("password");

        if (nullOrEmpty(login) || nullOrEmpty(password)) {
            return "/login.jsp";
        }

        if (!isLoginValid(login)) {
            request.setAttribute("exception", "Wrong login format");
            log.error("Invalid login parameter obtained");
            return "/WEB-INF/exception.jsp";
        }

        Optional<User> userOpt = userService.getUserByLoginAndPassword(login, password);
        if (!userOpt.isPresent()) {
            log.error(String.format("No user with credentials: login = '%s' and password = '%s'", login, password));
            return "/login.jsp";
        }

        if (CommandUtility.checkUserIsLogged(request, login)) {
            request.setAttribute("exception", "You have been already logged in. Please log out first");
            log.error(String.format("User with login: login = '%s' already has active session", login));
            return "/WEB-INF/exception.jsp";
        }

        User user = userOpt.get();
        CommandUtility.setUserRoleId(request, user.getRole(), user.getId(), login);
        log.trace(String.format("User '%s' was logged in with role = '%s'", login, user.getRole().name()));
        log.debug("Command finished");
        return "redirect:/home.jsp";
    }
}