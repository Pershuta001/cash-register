package com.example.cash_register.utils;

public class Constants {

    public final static int PAGE_SIZE = 10;

    public static final String NAME_REG_EX = "^[a-zA-Zа-яА-ЯіІїЇґҐєЄ \\d]{1,28}$";
    public static final String PRICE_REG_EX = "^([\\d]{1,5}([\\.][\\d]{1,2})?)";
    public static final String WEIGHT_REG_EX = "^([\\d]{1,5}([\\.][\\d]{1,3})?)$";
    public static final String QUANTITY_REG_EX = "^[\\d]{1,5}$";
    public static final String ID_REG_EX = "^[\\d]{1,8}$";
    public static final String LOGIN_REG_EX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
}
