package com.digiaccounts.digiaccountz.roomdatabase.tables.reminders;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.digiaccounts.digiaccountz.roomdatabase.tables.transactions.TransactionTable;

@Dao
public interface ReminderTableDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long AddReminder(ReminderTable detail);

    @Query("SELECT * FROM reminders_table")
    public ReminderTable[] loadAllReminder();


    @Query("SELECT * FROM reminders_table WHERE transactionidd=:transid")
    public ReminderTable loadAllRemindersbyTransactionID(long transid);

    @Query("SELECT * FROM reminders_table WHERE id=:id")
    public ReminderTable loadAllRemindersbyID(long id);

    @Query("SELECT * FROM reminders_table WHERE customeridd=:customerid")
    public ReminderTable[] loadAllRemindersByCustomerID(long customerid);

    @Query("SELECT * FROM reminders_table WHERE businessidd=:businessid")
    public ReminderTable[] loadAllRemindersByBusinessID(long businessid);

    @Query("DELETE FROM reminders_table WHERE transactionidd = :transId")
    public void deleteRemindersbyTransactionid(long transId);

    @Query("DELETE FROM reminders_table WHERE id = :Id")
    public void deleteRemindersbyid(long Id);

    @Query("UPDATE reminders_table SET date=:datee,time=:timee WHERE transactionidd=:idd")
    public int UpdateRemiderDateTime(long idd,String datee,String timee);

//    @Query("SELECT * FROM useremail_table WHERE mobilenumber=:mobileno AND pasword=:pas")
//    public SigninWithemailTable[] loadAllUserLogin(String mobileno, String pas);



}
