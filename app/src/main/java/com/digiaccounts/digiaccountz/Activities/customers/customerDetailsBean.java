package com.digiaccounts.digiaccountz.Activities.customers;

public class customerDetailsBean {

    String status;
    String type;
    String date;
    String amount;
    String comment;
    int color;
    long idd ;
    long businessidd ;
    long customeridd ;
    String reminder;
    String description;
    String image;

    public customerDetailsBean(long idd,long businessid,long customerid,String status, String type, String date, String amount, String comment, int color,String reminder, String description,String img) {
        this.status = status;
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.comment = comment;
        this.color = color;
        this.idd = idd;
        this.businessidd = businessid;
        this.customeridd = customerid;
        this.reminder = reminder;
        this.description = description;
        this.image = img;
    }


    public String getImage() {
        return image;
    }

    public String getReminder() {
        return reminder;
    }

    public String getDescription() {
        return description;
    }

    public String getComment() {
        return comment;
    }

    public int getColor() {
        return color;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }

    public long getIdd() {
        return idd;
    }

    public long getBusinessidd() {
        return businessidd;
    }

    public long getCustomeridd() {
        return customeridd;
    }
}
