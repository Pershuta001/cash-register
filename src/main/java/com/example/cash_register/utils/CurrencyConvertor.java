package com.example.cash_register.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyConvertor {

    private static final double CURRENCY_COURSE_UAH_USD = 26.99;


    public static String convertToUSD(double usd) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(usd);
    }

    public static String convertToUAH(double usd) {
        return NumberFormat.getCurrencyInstance(new Locale("ua", "UA")).format(usd * CURRENCY_COURSE_UAH_USD);
    }
}
