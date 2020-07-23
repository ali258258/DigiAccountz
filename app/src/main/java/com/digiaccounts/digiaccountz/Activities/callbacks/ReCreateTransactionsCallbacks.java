package com.digiaccounts.digiaccountz.Activities.callbacks;

import com.digiaccounts.digiaccountz.roomdatabase.tables.customer.CustomerTable;

public interface ReCreateTransactionsCallbacks {

    public abstract void Changes(String recieve, String get,String balance);
}
