package com.example.cash_register.utils;

import com.example.cash_register.model.enums.Roles;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

import static com.example.cash_register.utils.Constants.*;

public class Validator {

    public static boolean nullOrEmpty(String str) {
        return str == null || str.equals("");
    }

    public static boolean isAllowed(HttpServletRequest request, Roles role) {
        return role.equals(request.getSession().getAttribute("role"));
    }


    public static boolean isIdValid(String id) {
        return Pattern.compile(ID_REG_EX).matcher(id).matches();
    }

    public static boolean isPageValid(String page) {
        return page == null || Pattern.compile(ID_REG_EX).matcher(page).matches();
    }

    public static boolean isNameValid(String name) {
        return Pattern.compile(NAME_REG_EX).matcher(name).matches();
    }

    public static boolean isPriceValid(String price) {
        return Pattern.compile(PRICE_REG_EX).matcher(price).matches();
    }

    public static boolean isWeightValid(String weight) {
        return Pattern.compile(WEIGHT_REG_EX).matcher(weight).matches();
    }

    public static boolean isQuantityValid(String quantity) {
        return Pattern.compile(QUANTITY_REG_EX).matcher(quantity).matches();
    }

    public static boolean isLoginValid(String login) {
        return Pattern.compile(LOGIN_REG_EX).matcher(login).matches();
    }


}
