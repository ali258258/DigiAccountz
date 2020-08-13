package com.digiaccounts.digiaccountz.Report_All.Model

class Customer_Model_FB {

    var myTransactions= ArrayList<MyTransactions>()
    lateinit var customer_name:String
    lateinit var customer_number:String
    lateinit var total_sent:String
    lateinit var total_recive:String
    lateinit var net_balance:String

    constructor()
    constructor(myTransactions: ArrayList<MyTransactions>, customer_name: String, customer_number: String,
                totalsent:String,totalrecive:String,netbalance:String) {
        this.myTransactions = myTransactions
        this.customer_name = customer_name
        this.customer_number = customer_number
        this.total_sent=totalsent
        this.total_recive=totalrecive
        this.net_balance=netbalance
    }


}