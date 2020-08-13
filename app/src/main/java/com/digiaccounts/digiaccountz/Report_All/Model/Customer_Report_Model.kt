package com.digiaccounts.digiaccountz.Report_All.Model

class Customer_Report_Model {

    lateinit var business_name:String
    lateinit var report_date:String
    lateinit var customer_number:String
    lateinit var customer_name:String
    lateinit var total_sent:String
    lateinit var total_recived:String
     var customer_id:Long = 0
    lateinit var customer_balance:String


    constructor()
    constructor(business_name: String, report_date: String, customer_number: String, customer_name: String, total_sent: String, total_recived: String, customer_balance: String, customer_id:Long) {
        this.business_name = business_name
        this.report_date = report_date
        this.customer_number = customer_number
        this.customer_name = customer_name
        this.total_sent = total_sent
        this.total_recived = total_recived
        this.customer_balance = customer_balance
        this.customer_id=customer_id
    }





}