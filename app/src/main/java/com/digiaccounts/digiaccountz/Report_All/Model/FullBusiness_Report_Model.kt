package com.digiaccounts.digiaccountz.Report_All.Model

class FullBusiness_Report_Model {

    lateinit var business_name:String
    lateinit var report_date:String
    lateinit var total_sent:String
    lateinit var total_recived:String
     var customer_id=ArrayList<Long>()
    lateinit var net_balance:String


    constructor()
    constructor(business_name: String, report_date: String, total_sent: String, total_recived: String, net_balance: String, customer_id:ArrayList<Long>) {
        this.business_name = business_name
        this.report_date = report_date
        this.total_sent = total_sent
        this.total_recived = total_recived
        this.net_balance = net_balance
        this.customer_id=customer_id
    }





}