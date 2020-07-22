package com.digiaccounts.digiaccountz.Activities.customers.Contacts;

public class ContactsBean {

    String name;
    String number;

    public ContactsBean(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
