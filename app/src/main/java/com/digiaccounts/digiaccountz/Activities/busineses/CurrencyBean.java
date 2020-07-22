package com.digiaccounts.digiaccountz.Activities.busineses;

public class CurrencyBean {
    String tag;
    String currency;


    public CurrencyBean(String tag, String currency) {
        this.tag = tag;
        this.currency = currency;
    }

    public String getTag() {
        return tag;
    }

    public String getCurrency() {
        return currency;
    }
}
