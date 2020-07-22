package com.digiaccounts.digiaccountz.roomdatabase.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "useremail_table")
public class SigninWithemailTable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "fullname")
    private String fullname;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "mobilenumber")
    private String mobileNumber;

    @ColumnInfo(name = "pasword")
    private String pasword;


    public long getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }


    public String getPasword() {
        return pasword;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }
}
