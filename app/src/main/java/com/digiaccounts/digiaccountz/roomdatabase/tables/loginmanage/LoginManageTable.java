package com.digiaccounts.digiaccountz.roomdatabase.tables.loginmanage;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "loginmanage_table")
public class LoginManageTable {

    @ColumnInfo(name = "checklogin")
    private String check;

    @PrimaryKey
    @ColumnInfo(name = "userid")
    private long userid;

    public String getCheck() {
        return check;
    }

    public long getUserid() {
        return userid;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }
}
