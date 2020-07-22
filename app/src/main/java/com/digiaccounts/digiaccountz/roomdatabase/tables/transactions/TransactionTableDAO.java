package com.digiaccounts.digiaccountz.roomdatabase.tables.transactions;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.digiaccounts.digiaccountz.roomdatabase.tables.SigninWithemailTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.customer.CustomerTable;

@Dao
public interface TransactionTableDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long AddTransaction(TransactionTable detail);

    @Query("SELECT * FROM transactions_table")
    public TransactionTable[] loadAllTransactions();


    @Query("SELECT * FROM transactions_table WHERE customerid=:customeridd")
    public TransactionTable[] loadAllTransactionsByCustomerID(long customeridd);

    @Query("SELECT * FROM transactions_table WHERE businessid=:businessidd")
    public TransactionTable[] loadAllTransactionsByBusinessID(long businessidd);

    @Query("SELECT * FROM transactions_table WHERE id=:transid")
    public TransactionTable loadAllTransactionByTransactionID(long transid);


    @Query("DELETE FROM transactions_table WHERE id = :Id")
    public void deleteTransactionById(long Id);


    @Query("SELECT * FROM transactions_table WHERE customerid= :customerid AND date BETWEEN :startdate AND :todate")
    public TransactionTable[] loadAllTransactionsDatesWiseWithCustomer(long customerid ,String startdate, String todate);

    @Query("SELECT * FROM transactions_table WHERE businessid= :businessid AND date BETWEEN :startdate AND :todate")
    public TransactionTable[] loadAllTransactionsDatesWise(long businessid ,String startdate, String todate);



//    @Query("SELECT * FROM useremail_table WHERE mobilenumber=:mobileno AND pasword=:pas")
//    public SigninWithemailTable[] loadAllUserLogin(String mobileno, String pas);



}
