package com.digiaccounts.digiaccountz.roomdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.digiaccounts.digiaccountz.roomdatabase.tables.SigninWithEmailDao;
import com.digiaccounts.digiaccountz.roomdatabase.tables.SigninWithemailTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.business.BusinessManageDao;
import com.digiaccounts.digiaccountz.roomdatabase.tables.business.BusinessTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.customer.CustomerTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.customer.CustomerTableDAO;
import com.digiaccounts.digiaccountz.roomdatabase.tables.loginmanage.LoginManageDao;
import com.digiaccounts.digiaccountz.roomdatabase.tables.loginmanage.LoginManageTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.reminders.ReminderTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.reminders.ReminderTableDAO;
import com.digiaccounts.digiaccountz.roomdatabase.tables.transactions.TransactionTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.transactions.TransactionTableDAO;

@Database(entities = {SigninWithemailTable.class,LoginManageTable.class, CustomerTable.class, TransactionTable.class, BusinessTable.class, ReminderTable.class},version = 1)
public abstract class MyDatabase extends RoomDatabase
{

    public abstract SigninWithEmailDao signinDetails();

    public abstract LoginManageDao loginManageTable();

    public abstract CustomerTableDAO customerManageTable();

    public abstract TransactionTableDAO transactionManageTable();

    public abstract BusinessManageDao businessManageTable();

    public abstract ReminderTableDAO RemiderManageTable();

}
