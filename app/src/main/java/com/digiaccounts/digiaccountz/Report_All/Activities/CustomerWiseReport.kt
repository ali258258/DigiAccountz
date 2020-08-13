package com.digiaccounts.digiaccountz.Report_All.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digiaccounts.digiaccountz.*
import com.digiaccounts.digiaccountz.Report_All.Adaptors.TransactionsAdaptor
import com.digiaccounts.digiaccountz.Report_All.BgTasks.addtransactionstask
import com.digiaccounts.digiaccountz.Report_All.BgTasks.convertpdf
import com.digiaccounts.digiaccountz.Report_All.Interfaces.returnallfiles
import com.digiaccounts.digiaccountz.Report_All.Model.BgTaskModel
import com.digiaccounts.digiaccountz.Report_All.Model.Customer_Model_FB
import com.digiaccounts.digiaccountz.Report_All.Model.Customer_Report_Model
import com.digiaccounts.digiaccountz.Report_All.Model.MyTransactions
import com.digiaccounts.digiaccountz.Report_All.Others.Utils
import kotlinx.android.synthetic.main.activity_customer_wise_report.*
import kotlinx.android.synthetic.main.activity_customer_wise_report.download_btn
import kotlinx.android.synthetic.main.activity_full_business_report.*
import java.io.File

class CustomerWiseReport : AppCompatActivity(), returnallfiles {

    lateinit var utils: Utils
    lateinit var customerReportModel: Customer_Report_Model

    lateinit var myview: View


    lateinit var recyclerViewv: RecyclerView
    lateinit var layoutManagerv: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_wise_report)


        recyclerViewv = findViewById(R.id.recyclerview2)
        layoutManagerv = LinearLayoutManager(this)

        layoutManagerv.orientation = RecyclerView.VERTICAL
        recyclerViewv.layoutManager = layoutManagerv


        utils = Utils(this)
        customerReportModel = utils.currentCustomerReport


        tv_business_name.text = customerReportModel.business_name
        tv_report_date.text = customerReportModel.report_date
        tv_customer_number.text = customerReportModel.customer_number
        tv_customer_name.text = customerReportModel.customer_name
        tv_total_recived.text = customerReportModel.total_recived
        tv_total_sent.text = customerReportModel.total_sent
        tv_customer_balance.text = customerReportModel.customer_balance



        var sent=customerReportModel.total_sent.replace(",","")
        var rec=customerReportModel.total_recived.replace(",","")

        try {
            if(Integer.parseInt(sent)>Integer.parseInt(rec)) {
                tv_net_balance_text1.text = "You will Recieve"
                tv_customer_balance.setTextColor(ContextCompat.getColor(this@CustomerWiseReport,R.color.colorPrimary))
            }
            else {
                tv_net_balance_text1.text = "You will Give"
                tv_customer_balance.setTextColor(ContextCompat.getColor(this@CustomerWiseReport,R.color.appTextColorPurple))
            }
        } catch (e: Exception) {
        }



        tv_total_recived.setText(utils.afterTextChanged(tv_total_recived.text.toString()))
        tv_total_sent.setText(utils.afterTextChanged(tv_total_sent.text.toString()))
        tv_customer_balance.setText(utils.afterTextChanged(tv_customer_balance.text.toString()))



        var gaf = addtransactionstask()

        gaf.AssignContext(this@CustomerWiseReport, this@CustomerWiseReport)

        gaf.execute(customerReportModel.customer_id)


        myview = findViewById<RelativeLayout>(R.id.mainlayout1)

        download_btn.setOnClickListener {
            download_btn.visibility = View.GONE
            back_to_menu.visibility= View.GONE
            GeneratePdf()
        }

        back_to_menu.setOnClickListener {
            onBackPressed()
        }


    }

    fun GeneratePdf() {
        var task =
                convertpdf()
        task.AssignContext(this@CustomerWiseReport, 0)


        var bgTaskModel =
                BgTaskModel(utils.loadBitmapFromView(myview), customerReportModel.customer_name)
        task.execute(bgTaskModel)
    }

    override fun returnfiles(listfiles: List<File>) {

    }

    override fun return_transactions(listfiles: ArrayList<MyTransactions>) {

        Log.d("transactionssize1",listfiles.size.toString()+"")

        recyclerViewv.adapter = TransactionsAdaptor(this, listfiles)
    }

    override fun return_all_customers_for_full_business(listfiles: ArrayList<Customer_Model_FB>) {

    }
}
