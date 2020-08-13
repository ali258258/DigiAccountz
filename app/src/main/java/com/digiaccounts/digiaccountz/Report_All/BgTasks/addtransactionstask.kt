package com.digiaccounts.digiaccountz.Report_All.BgTasks

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.digiaccounts.digiaccountz.Activities.MainActivity
import com.digiaccounts.digiaccountz.Report_All.Model.MyTransactions
import com.digiaccounts.digiaccountz.Report_All.Interfaces.returnallfiles
import com.digiaccounts.digiaccountz.Report_All.Others.Utils


public class addtransactionstask() : AsyncTask<Long, Integer, ArrayList<MyTransactions>>() {
    public var context: Context? = null
    public var utilss: Utils? = null

  lateinit var rf: returnallfiles
    lateinit var pd:ProgressDialog

    fun AssignContext(c: Context,rf: returnallfiles) {
        context = c;
        utilss =
                Utils(
                        c
                )
        this.rf=rf
        pd= ProgressDialog(c)
        pd.setMessage("Wait for a moment .... ")

    }




    override fun doInBackground(vararg params: Long?): ArrayList<MyTransactions> {


        var transactions = MainActivity.database.transactionManageTable().loadAllTransactionsByCustomerID(params[0]!!)

        //  String fdate = "" + transactions[0].getDate() + " " + transactions[0].getTimee()


        var transactionsList = ArrayList<MyTransactions>()


        Log.d("transactionssize",transactions.size.toString()+"")

        for (g  in 0..(transactions.size-1)) {
            transactionsList.add(MyTransactions(transactions[g].getTransactiontype().toString(), transactions[g].getId().toString(), transactions[g].getAmount(), transactions[g].getDescription(),transactions[g].getDate()))
        }


        return transactionsList
    }


    override fun onPostExecute(result: ArrayList<MyTransactions>?) {
        super.onPostExecute(result)

        pd.dismiss()
        rf.return_transactions(result!!)

    }

    override fun onPreExecute() {
        super.onPreExecute()

        pd.show()

    }
}

