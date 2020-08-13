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
import com.digiaccounts.digiaccountz.Report_All.Adaptors.Customer_Adaptor_FB
import com.digiaccounts.digiaccountz.Report_All.Adaptors.TransactionsAdaptor
import com.digiaccounts.digiaccountz.Report_All.BgTasks.addcustomer_task
import com.digiaccounts.digiaccountz.Report_All.BgTasks.addtransactionstask
import com.digiaccounts.digiaccountz.Report_All.BgTasks.convertpdf
import com.digiaccounts.digiaccountz.Report_All.Interfaces.returnallfiles
import com.digiaccounts.digiaccountz.Report_All.Model.*
import com.digiaccounts.digiaccountz.Report_All.Others.Utils
import kotlinx.android.synthetic.main.activity_customer_wise_report.*
import kotlinx.android.synthetic.main.activity_customer_wise_report.download_btn
import kotlinx.android.synthetic.main.activity_full_business_report.*
import java.io.File

class FullBusinessReport : AppCompatActivity(), returnallfiles {

    lateinit var utils: Utils
    lateinit var fullbusinessReportModel: FullBusiness_Report_Model

    lateinit var myview: View


    lateinit var recyclerViewv: RecyclerView
    lateinit var layoutManagerv: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_business_report)


        recyclerViewv = findViewById(R.id.recyclerview_fb)
        layoutManagerv = LinearLayoutManager(this)

        layoutManagerv.orientation = RecyclerView.VERTICAL
        recyclerViewv.layoutManager = layoutManagerv


        utils = Utils(this)
        fullbusinessReportModel = utils.currentCustomerFullBusinessReport

        tv_business_name_fb.text=fullbusinessReportModel.business_name
        tv_report_date_fb.text=fullbusinessReportModel.report_date
        tv_total_sent_fb.text=fullbusinessReportModel.total_sent
        tv_total_recived_fb.text=fullbusinessReportModel.total_recived
        tv_net_balance_fb.text=fullbusinessReportModel.net_balance


        var sent=fullbusinessReportModel.total_sent.replace(",","")
        var rec=fullbusinessReportModel.total_recived.replace(",","")

        try {
            if(Integer.parseInt(sent)>Integer.parseInt(rec)) {
                tv_net_balance_text.text = "You will Recieve"
            tv_net_balance_fb.setTextColor(ContextCompat.getColor(this@FullBusinessReport,R.color.colorPrimary))
            }
            else {
                tv_net_balance_text.text = "You will Give"
                tv_net_balance_fb.setTextColor(ContextCompat.getColor(this@FullBusinessReport,R.color.appTextColorPurple))
            }
        } catch (e: Exception) {
        }



        myview = findViewById<RelativeLayout>(R.id.mainlayout2)

        var gaf = addcustomer_task()

        gaf.AssignContext(this@FullBusinessReport, this@FullBusinessReport)

        gaf.execute(fullbusinessReportModel.customer_id)



        download_btn.setOnClickListener {
            download_btn.visibility = View.GONE
            back_to_menu1.visibility= View.GONE
            GeneratePdf()
        }


        back_to_menu1.setOnClickListener {
            onBackPressed()
        }


    }

    fun GeneratePdf() {
        var task =
                convertpdf()
        task.AssignContext(this@FullBusinessReport, 0)


        var bgTaskModel =
                BgTaskModel(utils.loadBitmapFromView(myview), fullbusinessReportModel.business_name)
        task.execute(bgTaskModel)
    }

    override fun returnfiles(listfiles: List<File>) {

    }

    override fun return_transactions(listfiles: ArrayList<MyTransactions>) {
    }

    override fun return_all_customers_for_full_business(listfiles: ArrayList<Customer_Model_FB>) {

        for(jh in 0..(listfiles.size-1))
        {
            Log.d("valfgh",listfiles.get(jh).customer_name)
        }
        recyclerViewv.adapter=Customer_Adaptor_FB(this,listfiles)
    }




}
