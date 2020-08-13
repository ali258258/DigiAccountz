package com.digiaccounts.digiaccountz.Report_All.Interfaces

import com.digiaccounts.digiaccountz.Report_All.Model.MyTransactions
import com.digiaccounts.digiaccountz.Report_All.Model.Customer_Model_FB
import java.io.File

interface returnallfiles {

    fun returnfiles(listfiles:List<File>)

    fun return_transactions(listfiles:ArrayList<MyTransactions>)

    fun return_all_customers_for_full_business(listfiles:ArrayList<Customer_Model_FB>)


}