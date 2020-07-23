package com.digiaccounts.digiaccountz.roomdatabase.tables.transactions;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions_table")
public class TransactionTable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "businessid")
    private long businessid;

    @ColumnInfo(name = "customerid")
    private long customerid;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "timee")
    private String timee;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "transactiontype")
    private String transactiontype;

    @ColumnInfo(name = "amount")
    private String amount;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "reminder")
    private String reminder;


    public long getId() {
        return id;
    }

    public long getBusinessid() {
        return businessid;
    }

    public long getCustomerid() {
        return customerid;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getTransactiontype() {
        return transactiontype;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getReminder() {
        return reminder;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setBusinessid(long businessid) {
        this.businessid = businessid;
    }

    public void setCustomerid(long customerid) {
        this.customerid = customerid;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTransactiontype(String transactiontype) {
        this.transactiontype = transactiontype;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getTimee() {
        return timee;
    }

    public void setTimee(String timee) {
        this.timee = timee;
    }
}
