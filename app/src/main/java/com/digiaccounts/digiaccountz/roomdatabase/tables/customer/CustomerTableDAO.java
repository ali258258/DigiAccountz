package com.digiaccounts.digiaccountz.roomdatabase.tables.customer;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.digiaccounts.digiaccountz.roomdatabase.tables.SigninWithemailTable;

@Dao
public interface CustomerTableDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void AddCustomers(CustomerTable detail);

    @Query("SELECT * FROM customers_table")
    public CustomerTable[] loadAllCustomers();


    @Query("SELECT * FROM customers_table WHERE businessid=:businessid")
    public CustomerTable[] loadAllCustomersByBusinessID(long businessid);

    @Query("SELECT * FROM customers_table WHERE id=:idd")
    public CustomerTable loadCustomerusingID(long idd);

    @Query("DELETE FROM customers_table WHERE id = :Id")
    public void deleteCustomerById(long Id);

    @Query("UPDATE customers_table SET customer_balance=:balance,youwillget_amount=:get,youwillgive_amount=:give WHERE id=:idd")
    public int UpdateAmountValues(long idd,String balance,String get,String give);

    @Query("UPDATE customers_table SET fullname=:name,email=:email,mobilenumber=:mobileno,address=:address WHERE id=:idd")
    public int UpdateInfo(long idd,String name,String email,String mobileno,String address);


}
