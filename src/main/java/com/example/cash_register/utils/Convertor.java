package com.example.cash_register.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Convertor {

    private static final double CURRENCY_COURSE_UAH_USD = 26.99;


    public static String convertToUSD(double usd) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(usd);
    }

    public static String convertToUAH(double usd) {
        return NumberFormat.getCurrencyInstance(new Locale("ua", "UA")).format(usd * CURRENCY_COURSE_UAH_USD);
    }

    public static String amountFormat(double d, boolean byWeight) {
        System.out.println(d);
        if (byWeight)
            return new DecimalFormat("#0.000").format(d);
        return new DecimalFormat("#0").format(d);
    }
}
