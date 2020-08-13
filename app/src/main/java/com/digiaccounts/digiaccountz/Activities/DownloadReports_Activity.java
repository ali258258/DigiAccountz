package com.digiaccounts.digiaccountz.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.tables.business.BusinessTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.transactions.TransactionTable;

import java.io.File;
import java.io.FileOutputStream;

public class DownloadReports_Activity extends AppCompatActivity {


    long businessID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_reports_);

        BusinessTable[] businessTables = MainActivity.database.businessManageTable().loadAllBusiness();
        businessID = businessTables[0].getId();
        TransactionTable[] transactions = MainActivity.database.transactionManageTable().loadAllTransactionsByBusinessID(businessID);
        pdfCreate(businessTables[0].getBusinessname(),transactions);
    }


    public void pdfCreate(String buinessname,TransactionTable[] transactions){
       // Toast.makeText(DownloadReports_Activity.this, "in", Toast.LENGTH_SHORT).show();
        PdfDocument mypdfdocument = new PdfDocument();
        PdfDocument.PageInfo mypageinfo = new PdfDocument.PageInfo.Builder(300,600,1).create();
        PdfDocument.Page mypage = mypdfdocument.startPage(mypageinfo);

        Paint mypaint = new Paint();
        int x = 115, y=25;

        mypage.getCanvas().drawText(buinessname.toUpperCase(),x,y,mypaint);

        int linex =10 , liney=35;
        mypage.getCanvas().drawText("---------------------------------------------------------------------------------------------",linex,liney,mypaint);


        int transx = 10, transy = 45;
        for (int i =0; i<transactions.length; i++){
            Paint mypain = new Paint();
            mypain.setTextSize(7f);

            mypage.getCanvas().drawText(""+(i+1)+") Amount: "+transactions[i].getAmount()+"  Type: "+transactions[i].getTransactiontype()+"  D/T: "+transactions[i].getDate()+" "+transactions[i].getTimee()+"  ("+transactions[i].getStatus()+")",transx,transy,mypain);
            transy= transy+15;

        }


        mypdfdocument.finishPage(mypage);

        File f = new File(Environment.getExternalStorageDirectory().getPath(),"Cashify");
        if (f.exists()){ }
        else {f.mkdir();}
        String saving = buinessname.replaceAll(" ","_");
        File myfile = new File(Environment.getExternalStorageDirectory().getPath()+"/Cashify",saving+".pdf");
        try {
            myfile.createNewFile();
            mypdfdocument.writeTo(new FileOutputStream(myfile));
            Toast.makeText(DownloadReports_Activity.this, "Created", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e ){
            Log.i("wqqwqqww",e.getMessage());
            Toast.makeText(DownloadReports_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    mypdfdocument.close();

    }
}
