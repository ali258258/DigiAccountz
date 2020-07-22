package com.digiaccounts.digiaccountz.Activities.busineses;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.Activities.ApplockActivity;
import com.digiaccounts.digiaccountz.Activities.MainActivity;
import com.digiaccounts.digiaccountz.Activities.busineses.ui.home.CustomerListBean;
import com.digiaccounts.digiaccountz.Activities.customers.CustomerListActivity;
import com.digiaccounts.digiaccountz.Activities.customers.customerDetailsBean;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.tables.transactions.TransactionTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BusinessTransactions extends AppCompatActivity {

    int ff =0;
    int PRIVATE_MODE = 0;
    String PREF_NAME = "digiaccountz";


    DatePickerDialog.OnDateSetListener startdate,enddate;
    final Calendar startdateCalendar = Calendar.getInstance();
    Calendar enddareCalendar = Calendar.getInstance();
    String startdateStr="";
    String enddateStr="";


    ImageView backbtn;
    TextView nameEt;
    ListView listView;
    String customername="",customerid="";
    String businessid = "";



    ImageView filterMv;
    CustomAdapter_BusinessListing adap;
    ArrayList<customerDetailsBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_transactions);
        nameEt = findViewById(R.id.nameet_BTS);
        backbtn = findViewById(R.id.backbtn);
        listView = findViewById(R.id.listview_BTS);
        filterMv = findViewById(R.id.ssaa);

        customerid= getIntent().getStringExtra("customeridd");
        businessid= getIntent().getStringExtra("businessidd");
        customername= getIntent().getStringExtra("customername");

        nameEt.setText(customername);
        ff=0;
        adap = new CustomAdapter_BusinessListing(this,list);
        listView.setAdapter(adap);
        NormalList();


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusinessListActivity.ff=0;
                finish();
            }
        });


        startdate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                startdateCalendar.set(Calendar.YEAR, year);
                startdateCalendar.set(Calendar.MONTH, monthOfYear);
                startdateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                startDateformat();
            }
        };
        enddate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                enddareCalendar.set(Calendar.YEAR, year);
                enddareCalendar.set(Calendar.MONTH, monthOfYear);
                enddareCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                endDateformat();
            }
        };


        filterMv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(BusinessTransactions.this,v);
            }
        });


    }

    public void filteredList(String startdate,String enddate) {

        list.clear();
        TransactionTable[] checkingList = MainActivity.database.transactionManageTable().loadAllTransactionsDatesWiseWithCustomer(Long.parseLong(customerid),startdate, enddate);
        for (int i = 0; i<checkingList.length ; i++) {
            if (checkingList[i].getStatus().equalsIgnoreCase("Sent")) {
                list.add(new customerDetailsBean(checkingList[i].getId(),checkingList[i].getBusinessid(),checkingList[i].getCustomerid(),checkingList[i].getStatus(), checkingList[i].getTransactiontype(),
                        checkingList[i].getDate()+" "+checkingList[i].getTimee(), checkingList[i].getAmount(), checkingList[i].getDescription(), Color.parseColor("#0066ff"),checkingList[i].getReminder(),checkingList[i].getDescription(),checkingList[i].getImage()));
            }
            else if (checkingList[i].getStatus().equalsIgnoreCase("Recieved")){
                list.add(new customerDetailsBean(checkingList[i].getId(),checkingList[i].getBusinessid(),checkingList[i].getCustomerid(),checkingList[i].getStatus(), checkingList[i].getTransactiontype(),
                        checkingList[i].getDate()+" "+checkingList[i].getTimee(), checkingList[i].getAmount(), checkingList[i].getDescription(), Color.parseColor("#ae09a5"),checkingList[i].getReminder(),checkingList[i].getDescription(),checkingList[i].getImage()));
            }
        }
        adap.notifyDataSetChanged();

    }


    public void NormalList() {

        list.clear();
        TransactionTable[] transactionlist=MainActivity.database.transactionManageTable().loadAllTransactionsByCustomerID(Long.parseLong(customerid));
        if (transactionlist.length>0)
        {
            for (int i = 0; i<transactionlist.length ; i++) {
                if (transactionlist[i].getStatus().equalsIgnoreCase("Sent")) {
                    list.add(new customerDetailsBean(transactionlist[i].getId(),transactionlist[i].getBusinessid(),transactionlist[i].getCustomerid(),transactionlist[i].getStatus(), transactionlist[i].getTransactiontype(),
                            transactionlist[i].getDate()+" "+transactionlist[i].getTimee(), transactionlist[i].getAmount(), transactionlist[i].getDescription(), Color.parseColor("#0066ff"),transactionlist[i].getReminder(),transactionlist[i].getDescription(),transactionlist[i].getImage()));
                }
                else if (transactionlist[i].getStatus().equalsIgnoreCase("Recieved")){
                    list.add(new customerDetailsBean(transactionlist[i].getId(),transactionlist[i].getBusinessid(),transactionlist[i].getCustomerid(),transactionlist[i].getStatus(), transactionlist[i].getTransactiontype(),
                            transactionlist[i].getDate()+" "+transactionlist[i].getTimee(), transactionlist[i].getAmount(), transactionlist[i].getDescription(), Color.parseColor("#ae09a5"),transactionlist[i].getReminder(),transactionlist[i].getDescription(),transactionlist[i].getImage()));
                }
            }
        }
        adap.notifyDataSetChanged();

    }


    private void startDateformat() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        startdateStr = sdf.format(startdateCalendar.getTime());

        DatePickerDialog pckkk =  new DatePickerDialog(BusinessTransactions.this, enddate, enddareCalendar
                .get(Calendar.YEAR), enddareCalendar.get(Calendar.MONTH),
                enddareCalendar.get(Calendar.DAY_OF_MONTH));
        pckkk.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        pckkk.setMessage("Select End Date");
        pckkk.show();

    }

    private void endDateformat() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        enddateStr = sdf.format(enddareCalendar.getTime());

        filteredList(startdateStr,enddateStr);

    }



    private void showPopupMenu(final Context context, View view) {
        // inflate menu and attach it to a view onClick of which you want to display menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        //inflate menu items to popup menu
        inflater.inflate(R.menu.datewisefilter_menu, popup.getMenu());
        //assign a cutom onClick Listener to it.
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.datewie_menu)
                {

                    DatePickerDialog pckkk =  new DatePickerDialog(BusinessTransactions.this, startdate, startdateCalendar
                            .get(Calendar.YEAR), startdateCalendar.get(Calendar.MONTH),
                            startdateCalendar.get(Calendar.DAY_OF_MONTH));
                    pckkk.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                    pckkk.setMessage("Select Start Date");
                    pckkk.show();
                    Toast.makeText(context, "Note: Start date must be previous from end date.", Toast.LENGTH_LONG).show();
                }

                if (item.getItemId() == R.id.all_menu)
                {
                    NormalList();
                }


                return false;
            }
        });
        //Show Popup.
        popup.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (ff == 0){
            ff++;
        }
        else {
            SharedPreferences sharedPref= getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            final String stringObject = sharedPref.getString("applockstatus","");
            if (stringObject.equalsIgnoreCase("enable")){
                Intent i = new Intent(BusinessTransactions.this, ApplockActivity.class);
                i.putExtra("wheree","other");
                ff=0;
                startActivity(i);
            }

        }

    }

}
