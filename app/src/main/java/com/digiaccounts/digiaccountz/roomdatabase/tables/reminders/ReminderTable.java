package com.digiaccounts.digiaccountz.roomdatabase.tables.reminders;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminders_table")
public class ReminderTable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "customeridd")
    private long customerid;

    @ColumnInfo(name = "businessidd")
    private long businessid;

    @ColumnInfo(name = "customername")
    private String customername;

    @ColumnInfo(name = "transactionidd")
    private long transactionid;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "status")
    private String status;


    public long getId() {
        return id;
    }

    public long getCustomerid() {
        return customerid;
    }

    public long getTransactionid() {
        return transactionid;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCustomerid(long customerid) {
        this.customerid = customerid;
    }

    public void setTransactionid(long transactionid) {
        this.transactionid = transactionid;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomername() {
        return customername;
    }

    public String getTime() {
        return time;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getBusinessid() {
        return businessid;
    }

    public void setBusinessid(long businessid) {
        this.businessid = businessid;
    }
}
