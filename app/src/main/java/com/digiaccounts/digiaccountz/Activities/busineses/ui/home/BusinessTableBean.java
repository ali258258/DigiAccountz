package com.digiaccounts.digiaccountz.Activities.busineses.ui.home;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class BusinessTableBean {


    private long id;

    private long userid;


    private String businessname;
    private String totalamount;

    private String totalgiven;
    private String totalrecieved;

    private String businesscurrency;


    public BusinessTableBean(long id, long userid, String businessname, String totalamount, String totalgiven, String totalrecieved, String businesscurency) {
        this.id = id;
        this.userid = userid;
        this.businessname = businessname;
        this.totalamount = totalamount;
        this.totalgiven = totalgiven;
        this.totalrecieved = totalrecieved;
        this.businesscurrency = businesscurency;
    }

    public long getId() {
        return id;
    }

    public long getUserid() {
        return userid;
    }


    public String getBusinesscurrency() {
        return businesscurrency;
    }

    public String getBusinessname() {
        return businessname;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public String getTotalgiven() {
        return totalgiven;
    }

    public String getTotalrecieved() {
        return totalrecieved;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public void setBusinessname(String businessname) {
        this.businessname = businessname;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public void setTotalgiven(String totalgiven) {
        this.totalgiven = totalgiven;
    }

    public void setTotalrecieved(String totalrecieved) {
        this.totalrecieved = totalrecieved;
    }

    public void setId(long id) {
        this.id = id;
    }
}
