package com.example.cash_register.controller.commands;

import com.example.cash_register.model.entity.User;
import com.example.cash_register.model.enums.Roles;
import com.example.cash_register.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.example.cash_register.utils.Validator.nullOrEmpty;

public class RegisterCommand implements Command {

    private final UserService userService;

    public RegisterCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");

        //todo validation
        if (nullOrEmpty(login) || nullOrEmpty(password) ||
                nullOrEmpty(name) || nullOrEmpty(surname))
            return "/registration.jsp";

        Optional<User> user = userService.addNewUser(login, password, name, surname);

        if (!user.isPresent())
            return "/registration.jsp";

        //CommandUtility.setUserRoleLogin(request, Roles.CASHIER, login);
        return "redirect:/home.jsp";
    }
}
