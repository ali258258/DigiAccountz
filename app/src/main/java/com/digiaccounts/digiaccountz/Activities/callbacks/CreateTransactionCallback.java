package com.digiaccounts.digiaccountz.Activities.callbacks;


import com.digiaccounts.digiaccountz.roomdatabase.tables.transactions.TransactionTable;

public interface CreateTransactionCallback {

    public abstract void Callon(int balance, int youwillgive, int youwillget);

}
