package com.digiaccounts.digiaccountz.roomdatabase.tables.business;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "business_table")
public class BusinessTable {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "userid")
    private long userid;

    @ColumnInfo(name = "businessname")
    private String businessname;

    @ColumnInfo(name = "businesscurreny")
    private String businesscurrency;

    @ColumnInfo(name = "totalamount")
    private String totalamount;

    @ColumnInfo(name = "totalgiven")
    private String totalgiven;

    @ColumnInfo(name = "totalrecieved")
    private String totalrecieved;

    public long getId() {
        return id;
    }

    public long getUserid() {
        return userid;
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

    public String getBusinesscurrency() {
        return businesscurrency;
    }

    public void setBusinesscurrency(String businesscurrency) {
        this.businesscurrency = businesscurrency;
    }

    public void setId(long id) {
        this.id = id;
    }
}
