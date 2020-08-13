package com.digiaccounts.digiaccountz.Report_All.Model

class MyTransactions {

    lateinit var transaction_type:String
    lateinit var transaction_id:String
    lateinit var transaction_amount:String
    lateinit var transaction_description:String
    lateinit var transaction_date: String

    constructor()
    constructor(transaction_type: String, transaction_id: String, transaction_amount: String, transaction_description: String,transaction_date: String) {
        this.transaction_type = transaction_type
        this.transaction_id = transaction_id
        this.transaction_amount = transaction_amount
        this.transaction_description = transaction_description
        this.transaction_date=transaction_date
    }


}