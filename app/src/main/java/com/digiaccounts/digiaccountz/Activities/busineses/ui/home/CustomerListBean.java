package com.digiaccounts.digiaccountz.Activities.busineses.ui.home;

public class CustomerListBean {

    String nametitle;
    String date;
    String price;
    int color;
    String customerid;
    String businessid;
    String balance;
    String youwillget;
    String youwillgive;
    String catagoty;

    public String getBalance() {
        return balance;
    }

    public String getYouwillget() {
        return youwillget;
    }

    public String getYouwillgive() {
        return youwillgive;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setYouwillget(String youwillget) {
        this.youwillget = youwillget;
    }

    public void setYouwillgive(String youwillgive) {
        this.youwillgive = youwillgive;
    }

    public CustomerListBean(String nametitle, String date, String price, int color, String customerid, String businessid, String catagoty) {
        this.nametitle = nametitle;
        this.date = date;
        this.price = price;
        this.color = color;
        this.customerid = customerid;
        this.businessid = businessid;
        this.catagoty = catagoty;
    }


    public String getCatagoty() {
        return catagoty;
    }



    public void setColor(int color) {
        this.color = color;
    }

    public String getCustomerid() {
        return customerid;
    }

    public String getBusinessid() {
        return businessid;
    }

    public int getColor() {
        return color;
    }

    public String getNametitle() {
        return nametitle;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }
}
