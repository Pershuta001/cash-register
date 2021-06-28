package com.example.cash_register.tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

import static com.example.cash_register.utils.Convertor.convertToUAH;
import static com.example.cash_register.utils.Convertor.convertToUSD;

public class PriceTag extends TagSupport {

    private double value;
    private String locale;

    public void setValue(double value) {
        this.value = value;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public int doStartTag() {
        JspWriter out = pageContext.getOut();
        try {
            out.println("en".equals(locale) || locale.length() == 0 ? convertToUSD(value) : convertToUAH(value));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}
