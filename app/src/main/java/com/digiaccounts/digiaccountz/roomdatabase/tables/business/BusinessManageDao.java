package com.digiaccounts.digiaccountz.roomdatabase.tables.business;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.digiaccounts.digiaccountz.roomdatabase.tables.loginmanage.LoginManageTable;

@Dao
public interface BusinessManageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addBusiness(BusinessTable detail);

    @Query("SELECT * FROM business_table")
    public BusinessTable[] loadAllBusiness();

    @Query("SELECT * FROM business_table WHERE id=:idd")
    public BusinessTable loadWithID(long idd);

//    @Query("UPDATE business_table SET checklogin=:chk WHERE userid=:id")
//    public int UpdateStatus(String chk, long id);

    @Query("UPDATE business_table SET totalamount=:balance,totalrecieved=:get,totalgiven=:give WHERE id=:idd")
    public int UpdateAmountValuesBusiness(long idd,String balance,String get,String give);

}
