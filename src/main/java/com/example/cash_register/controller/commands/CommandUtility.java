package com.example.cash_register.controller.commands;

import com.example.cash_register.model.enums.Roles;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class CommandUtility {

    public static void setUserRoleId(HttpServletRequest request,
                                 Roles role, Long id) {
        HttpSession session = request.getSession();
        session.setAttribute("role", role);
        session.setAttribute("id", id);
    }

    public static void removeUserRole(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("id");
        session.setAttribute("role", Roles.GUEST);
    }

    public static boolean checkUserIsLogged(HttpServletRequest request, String userName) {
        @SuppressWarnings("unchecked")
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        if (loggedUsers.stream().anyMatch(userName::equals)) {
            return true;
        }
        loggedUsers.add(userName);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return false;
    }
}
