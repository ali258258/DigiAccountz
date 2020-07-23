package com.digiaccounts.digiaccountz.roomdatabase.tables.customer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "customers_table")
public class CustomerTable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "businessid")
    private long businessid;


    @ColumnInfo(name = "fullname")
    private String fullname;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "mobilenumber")
    private String mobileNumber;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "catagory")
    private String catagory;

    @ColumnInfo(name = "datetime")
    private String datetime;

    @ColumnInfo(name = "youwillget_amount")
    private String youwillget_amount;

    @ColumnInfo(name = "youwillgive_amount")
    private String youwillgive_amount;

    @ColumnInfo(name = "customer_balance")
    private String customer_balance;


    public long getId() {
        return id;
    }

    public long getBusinessid() {
        return businessid;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getCatagory() {
        return catagory;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getYouwillget_amount() {
        return youwillget_amount;
    }

    public String getYouwillgive_amount() {
        return youwillgive_amount;
    }

    public String getCustomer_balance() {
        return customer_balance;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBusinessid(long businessid) {
        this.businessid = businessid;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setYouwillget_amount(String youwillget_amount) {
        this.youwillget_amount = youwillget_amount;
    }

    public void setYouwillgive_amount(String youwillgive_amount) {
        this.youwillgive_amount = youwillgive_amount;
    }

    public void setCustomer_balance(String customer_balance) {
        this.customer_balance = customer_balance;
    }
}
