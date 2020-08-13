package com.digiaccounts.digiaccountz.Report_All.BgTasks

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.digiaccounts.digiaccountz.Activities.MainActivity
import com.digiaccounts.digiaccountz.Report_All.Model.Customer_Model_FB
import com.digiaccounts.digiaccountz.Report_All.Model.MyTransactions
import com.digiaccounts.digiaccountz.Report_All.Others.Utils
import com.digiaccounts.digiaccountz.Report_All.Interfaces.returnallfiles


public class addcustomer_task() : AsyncTask<ArrayList<Long>, Integer, ArrayList<Customer_Model_FB>>() {
    public var context: Context? = null
    public var utilss: Utils? = null

    lateinit var rf: returnallfiles
    lateinit var pd: ProgressDialog

    fun AssignContext(c: Context, rf: returnallfiles) {
        context = c;
        utilss =
                Utils(
                        c
                )
        this.rf = rf
        pd = ProgressDialog(c)
        pd.setMessage("Wait for a moment .... ")

    }


    override fun doInBackground(vararg params: ArrayList<Long>?): ArrayList<Customer_Model_FB> {


        var customerModelFb :Customer_Model_FB

        var customer_final_list = ArrayList<Customer_Model_FB>()


        for (i in 0..(params[0]!!.size - 1)) {

            Log.d("ival"+i,params[0]!!.get(i).toString())


            var customerTable = MainActivity.database.customerManageTable().loadCustomerusingID(params[0]!!.get(i))
            var trasaction_table = MainActivity.database.transactionManageTable().loadAllTransactionsByCustomerID(params[0]!!.get(i))
            var total_sent=customerTable.youwillgive_amount
            var total_recive=customerTable.youwillget_amount
            var net_sent_rec=customerTable.customer_balance




            Log.d("ival2"+i,customerTable.fullname)

            var arr_list = ArrayList<MyTransactions>()
            for (km in 0..(trasaction_table.size - 1)) {
                var mtm = trasaction_table.get(km)
                arr_list.add(MyTransactions(mtm.transactiontype.toString(), mtm.id.toString(), mtm.amount, mtm.description,mtm.date))
            }

            Log.d("ival4"+i,customerTable.fullname)

            customer_final_list.add(Customer_Model_FB(arr_list,customerTable.fullname,customerTable.mobileNumber,total_sent,total_recive,net_sent_rec))
        }



        for(jh in 0..(customer_final_list.size-1))
        {
            Log.d("ival3"+jh,customer_final_list.get(jh).customer_name)
        }


        return customer_final_list
    }


    override fun onPostExecute(result: ArrayList<Customer_Model_FB>?) {
        super.onPostExecute(result)

        pd.dismiss()
        rf.return_all_customers_for_full_business(result!!)

    }

    override fun onPreExecute() {
        super.onPreExecute()

        pd.show()

    }
}

