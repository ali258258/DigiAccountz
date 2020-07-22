package com.digiaccounts.digiaccountz.roomdatabase.tables.loginmanage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.digiaccounts.digiaccountz.roomdatabase.tables.SigninWithemailTable;

@Dao
public interface LoginManageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void add(LoginManageTable detail);

    @Query("SELECT * FROM loginmanage_table")
    public LoginManageTable[] loadAll();

    @Query("SELECT * FROM loginmanage_table WHERE userid=:idd")
    public LoginManageTable[] loadWithID(long idd);

    @Query("UPDATE loginmanage_table SET checklogin=:chk WHERE userid=:id")
    public int UpdateStatus(String chk,long id);

}
