package com.digiaccounts.digiaccountz.Activities.busineses;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.Activities.ApplockActivity;
import com.digiaccounts.digiaccountz.Activities.DownloadReports_Activity;
import com.digiaccounts.digiaccountz.Activities.MainActivity;
import com.digiaccounts.digiaccountz.Activities.busineses.ui.home.CustomAdapterforcustomerlist;
import com.digiaccounts.digiaccountz.Activities.busineses.ui.home.CustomerListBean;
import com.digiaccounts.digiaccountz.Activities.busineses.ui.home.HomeFragment;
import com.digiaccounts.digiaccountz.Activities.customers.Contacts.ContactsBean;
import com.digiaccounts.digiaccountz.Activities.customers.Contacts.ContactsLoad;
import com.digiaccounts.digiaccountz.Activities.customers.Contacts.CustomAdapter_contacts;
import com.digiaccounts.digiaccountz.Activities.customers.CreateCustomerActivity;
import com.digiaccounts.digiaccountz.Activities.customers.CustomAdapter_customerListing;
import com.digiaccounts.digiaccountz.Activities.customers.CustomerListActivity;
import com.digiaccounts.digiaccountz.Activities.customers.customerDetailsBean;
import com.digiaccounts.digiaccountz.Activities.reminders.UpComingRemindersActivity;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.tables.business.BusinessTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.customer.CustomerTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.transactions.TransactionTable;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BusinessListActivity extends AppCompatActivity {


    public static int ff =0;
    int PRIVATE_MODE = 0;
    String PREF_NAME = "digiaccountz";


    BusinessTable bt;

    DatePickerDialog.OnDateSetListener startdate,enddate;
    final Calendar startdateCalendar = Calendar.getInstance();
    Calendar enddareCalendar = Calendar.getInstance();
    String startdateStr="";
    String enddateStr="";

    TextView balanceheading;

    LinearLayout homeLv;
    ListView lv;
    ImageView addMv,filterMv;

    TextView totalrecieved,totalgiven,balance;
    ArrayList<customerDetailsBean> list = new ArrayList<>();

    EditText searchEt_reports;


    String businessid="";

    ArrayList<CustomerListBean> Clist = new ArrayList<>();


    Toolbar toolbar;

    CustomAdapterforcustomerlist adap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_list);
        bt = MainActivity.database.businessManageTable().loadWithID(HomeFragment.currentBusinessID);


        searchEt_reports = findViewById(R.id.searchEt_reports);
        addMv = findViewById(R.id.addBtn);
        balanceheading= findViewById(R.id.balheading);
        lv = findViewById(R.id.mylistview);
        homeLv = findViewById(R.id.tohommeLv);
        filterMv = findViewById(R.id.ssaa);
        totalrecieved = findViewById(R.id.totalrecieved_inbusinesslist);
        totalgiven = findViewById(R.id.totalgiven_inbusinesslist);
        balance = findViewById(R.id.balance_inbusinesslist);
         toolbar = findViewById(R.id.toolbarme);
        toolbar.setTitle(bt.getBusinessname());
        toolbar.inflateMenu(R.menu.businesslistmenu);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        totalrecieved.setText(bt.getBusinesscurrency()+": "+bigDecimalData(bt.getTotalgiven()));
        totalgiven.setText(bt.getBusinesscurrency()+": "+bigDecimalData(bt.getTotalrecieved()));
        balance.setText(bt.getBusinesscurrency()+": "+bigDecimalData(""+Math.abs(Integer.parseInt(bt.getTotalamount()))));
        businessid= getIntent().getStringExtra("businessidd");

        if (Integer.parseInt(bt.getTotalrecieved())>=Integer.parseInt(bt.getTotalgiven())){
            balance.setTextColor(Color.parseColor("#ae09a5"));
            balanceheading.setText("You will Pay");

        }
        else if (Integer.parseInt(bt.getTotalrecieved())<Integer.parseInt(bt.getTotalgiven())){
            balance.setTextColor(Color.parseColor("#0066ff"));
            balanceheading.setText("You will Receive");
        }

        adap = new CustomAdapterforcustomerlist(this,Clist);
        lv.setAdapter(adap);
        inisilizeList();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CustomerListBean object = (CustomerListBean) adap.getItem(position);

                Intent intent = new Intent(BusinessListActivity.this, BusinessTransactions.class);
                intent.putExtra("customeridd", object.getCustomerid());
                intent.putExtra("customername", object.getNametitle());
                intent.putExtra("businessidd", object.getBusinessid());
                intent.putExtra("cbalance", object.getBalance());
                intent.putExtra("cyouwillget", object.getYouwillget());
                intent.putExtra("cyouwillgive", object.getYouwillgive());
                startActivity(intent);

            }
        });


        homeLv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivityWithDrawer.ff = 0;
                finish();
            }
        });

        addMv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusinessListActivity.this, CreateCustomerActivity.class));
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


//        filterMv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopupMenu(BusinessListActivity.this,v);
//            }
//        });


        searchEt_reports.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adap.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.businesslistmenu, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        HomeActivityWithDrawer.ff=0;
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

                case R.id.action_switchbusiness:
                    businessSwitch_Dailog();
                return true;

            case R.id.action_settingss22:
//                TransactionTable[] t = MainActivity.database.transactionManageTable().loadAllTransactionsByBusinessID(bt.getId());
//                CustomerTable[] c = MainActivity.database.customerManageTable().loadAllCustomersByBusinessID(bt.getId());
//                pdfCreate(bt.getBusinessname(),bigDecimalData(bt.getTotalamount()),bigDecimalData(bt.getTotalrecieved()),bigDecimalData(bt.getTotalgiven()),c);
//
                businessnDailog(bt.getBusinessname());
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public static String bigDecimalData(String data) {
        if (!TextUtils.isEmpty(data)) {
            BigDecimal bd = new BigDecimal(Double.parseDouble(data));
            DecimalFormat df = new DecimalFormat("#,###,###");
            return df.format(bd);
        }
        return "";
    }


    private void startDateformat() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        startdateStr = sdf.format(startdateCalendar.getTime());

        DatePickerDialog pckkk =  new DatePickerDialog(BusinessListActivity.this, enddate, enddareCalendar
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

                    DatePickerDialog pckkk =  new DatePickerDialog(BusinessListActivity.this, startdate, startdateCalendar
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


    public void filteredList(String startdate,String enddate)
    {

        list.clear();
        TransactionTable[] checkingList = MainActivity.database.transactionManageTable().loadAllTransactionsDatesWise(Long.parseLong(businessid),startdate, enddate);
        for (int i = 0; i<checkingList.length ; i++) {
            if (checkingList[i].getStatus().equalsIgnoreCase("Sent")) {
                list.add(new customerDetailsBean(checkingList[i].getId(),checkingList[i].getBusinessid(),checkingList[i].getCustomerid(),checkingList[i].getStatus(), checkingList[i].getTransactiontype(),
                        checkingList[i].getDate(), checkingList[i].getAmount(), checkingList[i].getDescription(), Color.parseColor("#0066ff"),checkingList[i].getReminder(),checkingList[i].getDescription(),checkingList[i].getImage()));
            }
            else if (checkingList[i].getStatus().equalsIgnoreCase("Recieved")){
                list.add(new customerDetailsBean(checkingList[i].getId(),checkingList[i].getBusinessid(),checkingList[i].getCustomerid(),checkingList[i].getStatus(), checkingList[i].getTransactiontype(),
                        checkingList[i].getDate(), checkingList[i].getAmount(), checkingList[i].getDescription(), Color.parseColor("#ae09a5"),checkingList[i].getReminder(),checkingList[i].getDescription(),checkingList[i].getImage()));
            }
        }
        adap.notifyDataSetChanged();

    }




    public void NormalList() {

        list.clear();
        TransactionTable[] transactionlist=MainActivity.database.transactionManageTable().loadAllTransactionsByBusinessID(Long.parseLong(businessid));
        if (transactionlist.length>0)
        {
            for (int i = 0; i<transactionlist.length ; i++) {

                if (transactionlist[i].getStatus().equalsIgnoreCase("Sent"))
                {
                    list.add(new customerDetailsBean(transactionlist[i].getId(),transactionlist[i].getBusinessid(),transactionlist[i].getCustomerid(),transactionlist[i].getStatus(), transactionlist[i].getTransactiontype(),
                            transactionlist[i].getDate(), transactionlist[i].getAmount(), transactionlist[i].getDescription(), Color.parseColor("#0066ff"),transactionlist[i].getReminder(),transactionlist[i].getDescription(),transactionlist[i].getImage()));
                }
                else if (transactionlist[i].getStatus().equalsIgnoreCase("Recieved")){
                    list.add(new customerDetailsBean(transactionlist[i].getId(),transactionlist[i].getBusinessid(),transactionlist[i].getCustomerid(),transactionlist[i].getStatus(), transactionlist[i].getTransactiontype(),
                            transactionlist[i].getDate(), transactionlist[i].getAmount(), transactionlist[i].getDescription(), Color.parseColor("#ae09a5"),transactionlist[i].getReminder(),transactionlist[i].getDescription(),transactionlist[i].getImage()));
                }
            }
        }
        adap.notifyDataSetChanged();
    }



    public void fullbusinessReport(String buinessname,String businessbalance,String totalRecieved,String totalsent,CustomerTable[] customers){
        // Toast.makeText(DownloadReports_Activity.this, "in", Toast.LENGTH_SHORT).show();

        String textH = "";

        if (Integer.parseInt(totalRecieved)>=Integer.parseInt(totalsent)){
            textH = "You will Recieve";
        }
        else if (Integer.parseInt(totalRecieved)<Integer.parseInt(totalsent)) {
            textH = "You will Pay";
        }


        PdfDocument mypdfdocument = new PdfDocument();
        PdfDocument.PageInfo mypageinfo = new PdfDocument.PageInfo.Builder(1200,2010,1).create();
        PdfDocument.Page mypage = mypdfdocument.startPage(mypageinfo);
        int pagewidht = 1200;
        int pageheight = 2010;

        Paint mypaint = new Paint();
        int x = 115, y=25;

        mypaint.setTextSize(30f);
        mypaint.setTextAlign(Paint.Align.CENTER);
        mypage.getCanvas().drawText(buinessname.toUpperCase(),pagewidht/2,y,mypaint);

        mypaint.setTextSize(10f);
        int linex =10 , liney=35;
        //mypage.getCanvas().drawText("---------------------------------------------------------------------------------------------",linex,liney,mypaint);
        mypage.getCanvas().drawLine(10,liney,pagewidht-10,liney,mypaint);


           int transx = 10, transy = 55;

        for (int i =0; i<customers.length; i++){
            mypaint.setTextAlign(Paint.Align.CENTER);
            mypaint.setTextSize(22f);
            mypage.getCanvas().drawText(customers[i].getFullname(),pagewidht/2, transy,mypaint);
            transy = transy +20;
            mypaint.setTextAlign(Paint.Align.CENTER);
            mypaint.setTextSize(18f);
            mypage.getCanvas().drawText("Transactions",pagewidht/2, transy,mypaint);

            transy = transy +20;

            mypaint.setStyle(Paint.Style.STROKE);
            mypaint.setStrokeWidth(2);
            mypage.getCanvas().drawRect(20,transy,pagewidht-20,transy+20,mypaint);

            // sr // desc // type (supplies) // datetime // amount
            transy = transy +15;
            mypaint.setTextAlign(Paint.Align.LEFT);
            mypaint.setStyle(Paint.Style.FILL);
            mypaint.setTextSize(15f);
            mypage.getCanvas().drawText("Sr",40, transy,mypaint);
            mypage.getCanvas().drawText("Description",160, transy,mypaint);
            mypage.getCanvas().drawText("Type",600, transy,mypaint);
            mypage.getCanvas().drawText("Date/Time",850, transy,mypaint);
            mypage.getCanvas().drawText("Amount",1100, transy,mypaint);

            transy = transy +25;

            TransactionTable[] transactions = MainActivity.database.transactionManageTable().loadAllTransactionsByCustomerID(customers[i].getId());
            for (int j=0 ; j<transactions.length; j++){

                mypage.getCanvas().drawText(""+(j+1),42, transy,mypaint);
                mypage.getCanvas().drawText(transactions[j].getDescription(),160, transy,mypaint);
                mypage.getCanvas().drawText(transactions[j].getStatus()+" ("+transactions[j].getTransactiontype()+")",560, transy,mypaint);
                mypage.getCanvas().drawText(transactions[j].getDate()+" "+transactions[j].getTimee(),820, transy,mypaint);
                mypage.getCanvas().drawText(bigDecimalData(transactions[j].getAmount()),1100, transy,mypaint);
                transy = transy+20;
            }

            transy = transy+30;
        }

        mypage.getCanvas().drawLine(15,transy-15,pagewidht-15,transy-15,mypaint);

        transy = transy+15;
        mypaint.setTextSize(20f);
        mypaint.setTextAlign(Paint.Align.RIGHT);
        mypage.getCanvas().drawText("Date: "+giveDate()+" "+giveTime(),pagewidht-20,transy,mypaint);
        transy=transy+20;
        mypage.getCanvas().drawText(textH+": "+bigDecimalData(""+Math.abs(Integer.parseInt(businessbalance))),pagewidht-20,transy,mypaint);
        transy=transy+20;
        mypage.getCanvas().drawText("You Will Recieve: "+bigDecimalData(""+Math.abs(Integer.parseInt(totalRecieved))),pagewidht-20,transy,mypaint);
        transy=transy+20;
        mypage.getCanvas().drawText("You Will Pay: "+bigDecimalData(""+Math.abs(Integer.parseInt(totalsent))),pagewidht-20,transy,mypaint);




        transy=transy+30;
        mypaint.setTextAlign(Paint.Align.CENTER);
        mypaint.setTextSize(22f);
        mypage.getCanvas().drawText("Thank you for using Digi Accountz",pagewidht/2, transy,mypaint);


        mypdfdocument.finishPage(mypage);

        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"DigiPDF");

        if (f.exists()){ }
        else {f.mkdir();}
        String saving = buinessname.replaceAll(" ","_");
        File myfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/DigiPDF",saving+".pdf");
        try {
            myfile.createNewFile();
            mypdfdocument.writeTo(new FileOutputStream(myfile));
            Toast.makeText(BusinessListActivity.this, "Full Business Report Created", Toast.LENGTH_SHORT).show();


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                show_Notification(BusinessListActivity.this,buinessname+".pdf Downloaded.");
            }
            else {
                show_NotificationLower(BusinessListActivity.this,buinessname+".pdf Downloaded.");
            }

        }
        catch (Exception e ){
            Log.i("wqqwqqww",e.getMessage());
            Toast.makeText(BusinessListActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

  //       mypdfdocument.close();

    }



    public void customerwiseReport(String business ,String customername,String customerbalance,String totalRecieved,String totalsent,CustomerTable customers){


String textH="";
        if (Integer.parseInt(totalRecieved)>=Integer.parseInt(totalsent)){
            textH = "You will Recieve";
        }
        else if (Integer.parseInt(totalRecieved)<Integer.parseInt(totalsent)) {
            textH = "You will Pay";
        }

        PdfDocument mypdfdocument = new PdfDocument();
        PdfDocument.PageInfo mypageinfo = new PdfDocument.PageInfo.Builder(1200,2010,1).create();
        PdfDocument.Page mypage = mypdfdocument.startPage(mypageinfo);
        int pagewidht = 1200;
        int pageheight = 2010;

        Paint mypaint = new Paint();
        int x = 115, y=25;

        mypaint.setTextSize(30f);
        mypaint.setTextAlign(Paint.Align.CENTER);
        mypage.getCanvas().drawText(customername.toUpperCase()+" ("+customers.getCatagory()+")",pagewidht/2,y,mypaint);

        mypaint.setTextSize(10f);
        int linex =10 , liney=35;
        mypage.getCanvas().drawLine(10,liney,pagewidht-10,liney,mypaint);

        int transx = 10, transy = 55;

        for (int i =0; i<1; i++){
            transy = transy +10;
            mypaint.setTextAlign(Paint.Align.CENTER);
            mypaint.setTextSize(22f);
            mypage.getCanvas().drawText("Transactions",pagewidht/2, transy,mypaint);

            transy = transy +20;

            mypaint.setStyle(Paint.Style.STROKE);
            mypaint.setStrokeWidth(2);
            mypage.getCanvas().drawRect(20,transy,pagewidht-20,transy+20,mypaint);

            // sr // desc // type (supplies) // datetime // amount
            transy = transy +15;
            mypaint.setTextAlign(Paint.Align.LEFT);
            mypaint.setStyle(Paint.Style.FILL);
            mypaint.setTextSize(15f);
            mypage.getCanvas().drawText("Sr",40, transy,mypaint);
            mypage.getCanvas().drawText("Description",160, transy,mypaint);
            mypage.getCanvas().drawText("Type",600, transy,mypaint);
            mypage.getCanvas().drawText("Date/Time",850, transy,mypaint);
            mypage.getCanvas().drawText("Amount",1100, transy,mypaint);

            transy = transy +25;

            TransactionTable[] transactions = MainActivity.database.transactionManageTable().loadAllTransactionsByCustomerID(customers.getId());
            for (int j=0 ; j<transactions.length; j++){

                mypage.getCanvas().drawText(""+(j+1),42, transy,mypaint);
                mypage.getCanvas().drawText(transactions[j].getDescription(),160, transy,mypaint);
                mypage.getCanvas().drawText(transactions[j].getStatus()+" ("+transactions[j].getTransactiontype()+")",560, transy,mypaint);
                mypage.getCanvas().drawText(transactions[j].getDate()+" "+transactions[j].getTimee(),820, transy,mypaint);
                mypage.getCanvas().drawText(bigDecimalData(transactions[j].getAmount()),1100, transy,mypaint);

                transy = transy+20;

            }

            transy = transy+30;
        }


        mypage.getCanvas().drawLine(15,transy-15,pagewidht-15,transy-15,mypaint);

        transy = transy+15;
        mypaint.setTextSize(20f);
        mypaint.setTextAlign(Paint.Align.RIGHT);
        mypage.getCanvas().drawText("Business: "+business,pagewidht-20,transy,mypaint);
        transy=transy+20;

        mypage.getCanvas().drawText("Date: "+giveDate()+" "+giveTime(),pagewidht-20,transy,mypaint);
        transy=transy+20;
        mypage.getCanvas().drawText(customers.getCatagory()+" Balance: "+bigDecimalData(""+Math.abs(Integer.parseInt(customerbalance))),pagewidht-20,transy,mypaint);
        transy=transy+20;
        mypage.getCanvas().drawText("You Will Recieve: "+bigDecimalData(""+Math.abs(Integer.parseInt(totalRecieved))),pagewidht-20,transy,mypaint);
        transy=transy+20;
        mypage.getCanvas().drawText("You Will Pay: "+bigDecimalData(""+Math.abs(Integer.parseInt(totalsent))),pagewidht-20,transy,mypaint);



        transy=transy+30;
        mypaint.setTextAlign(Paint.Align.CENTER);
        mypaint.setTextSize(22f);
        mypage.getCanvas().drawText("Thank you for using Digi Accountz",pagewidht/2, transy,mypaint);


        mypdfdocument.finishPage(mypage);

        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"DigiPDF");

        if (f.exists()){ }
        else {f.mkdir();}
        String saving = customername.replaceAll(" ","_");
        File myfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/DigiPDF",saving+".pdf");
        try {
            myfile.createNewFile();
            mypdfdocument.writeTo(new FileOutputStream(myfile));
            Toast.makeText(BusinessListActivity.this, "Customer Wise Report Created", Toast.LENGTH_SHORT).show();


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                show_Notification(BusinessListActivity.this,customername+".pdf Downloaded.");
            }
            else {
                show_NotificationLower(BusinessListActivity.this,customername+".pdf Downloaded.");
            }

        }
        catch (Exception e ){
            Log.i("wqqwqqww",e.getMessage());
            Toast.makeText(BusinessListActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        mypdfdocument.close();
    }







    public void businessSwitch_Dailog(){
        final Dialog dialog = new Dialog(BusinessListActivity.this);
        dialog.setContentView(R.layout.custom_businessswitchdailog);
        ListView lv =dialog.findViewById(R.id.mylistview);

        ArrayList<String> slist = new ArrayList<>();
        final ArrayAdapter<String> adap;
        final BusinessTable[] tables = MainActivity.database.businessManageTable().loadAllBusiness();

        for (int d = 0 ; d<tables.length; d++){
         slist.add(tables[d].getBusinessname());
        }

        adap = new ArrayAdapter<String>(BusinessListActivity.this,android.R.layout.simple_list_item_1,slist);
        lv.setAdapter(adap);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BusinessTable obj = tables[position];
                businessid = Long.toString(obj.getId());
                bt = MainActivity.database.businessManageTable().loadWithID(obj.getId());

                toolbar.setTitle(obj.getBusinessname());
                totalrecieved.setText(bt.getBusinesscurrency()+": "+bigDecimalData(bt.getTotalrecieved()));
                totalgiven.setText(bt.getBusinesscurrency()+": "+bigDecimalData(bt.getTotalgiven()));
                balance.setText(bt.getBusinesscurrency()+": "+bigDecimalData(""+Math.abs(Integer.parseInt(bt.getTotalamount()))));
                if (Integer.parseInt(bt.getTotalrecieved())>=Integer.parseInt(bt.getTotalgiven())){
                    balance.setTextColor(Color.parseColor("#ae09a5"));
                }
                else if (Integer.parseInt(bt.getTotalrecieved())<Integer.parseInt(bt.getTotalgiven())){
                    balance.setTextColor(Color.parseColor("#0066ff"));
                }

                inisilizeList();

                dialog.dismiss();

            }
        });

        dialog.show();
    }

    public void inisilizeList(){
        Clist.clear();
        CustomerTable customerList[] = MainActivity.database.customerManageTable().loadAllCustomersByBusinessID(Long.parseLong(businessid));
        for (int i =0; i<customerList.length; i++)
        {
            Log.i("sdsfrrrgv",":"+customerList[i].getId());
            CustomerListBean ob = new CustomerListBean(customerList[i].getFullname(),customerList[i].getDatetime(),customerList[i].getCustomer_balance(),Color.parseColor("#ae09a5"),String.valueOf(customerList[i].getId()),String.valueOf(customerList[i].getBusinessid()),customerList[i].getCatagory());
            ob.setBalance(customerList[i].getCustomer_balance());
            ob.setYouwillget(customerList[i].getYouwillget_amount());
            ob.setYouwillgive(customerList[i].getYouwillgive_amount());
            if (Integer.parseInt(customerList[i].getYouwillget_amount())>=Integer.parseInt(customerList[i].getYouwillgive_amount())){
                ob.setColor(Color.parseColor("#ae09a5"));
            }
            else if (Integer.parseInt((customerList[i].getYouwillget_amount()))<Integer.parseInt(customerList[i].getYouwillgive_amount())){
                ob.setColor(Color.parseColor("#0066ff"));
            }
            Clist.add(ob);
        }
        adap.notifyDataSetChanged();
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show_Notification(Context context,String textt){

        Intent intent=new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
        String CHANNEL_ID="MYCHANNEL";
        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"bhn", NotificationManager.IMPORTANCE_LOW);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,1,intent,0);
        Notification notification=new Notification.Builder(context,CHANNEL_ID)
                .setContentText(textt)
                .setContentTitle("DigiAccountz")
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.sym_action_chat,"download",pendingIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_action_chat)
                .build();

        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1,notification);


    }

    public void show_NotificationLower(Context context,String text){

        NotificationCompat.Builder mBuilder =   new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.sym_action_chat) // notification icon
                .setContentTitle("DigiAccountz") // title for notification
                .setContentText(text)
                .setAutoCancel(true); // clear notification after click
        Intent intent=new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
        @SuppressLint("WrongConstant") PendingIntent pi = PendingIntent.getActivity(context,0,intent,Intent.FLAG_ACTIVITY_NEW_TASK);
        mBuilder.setContentIntent(pi);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());


    }


    public String giveDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(cal.getTime());
    }

    public String giveTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(cal.getTime());
    }


    //datetime
    // type
    // sr // desc // type (supplies) // datetime // amount

    // reminder multiple wih showinng
    // clicking on reminders showing reminder details

    //customer report , customer date wise report ()
    // business report datewise (showing date)

    //


    public void businessnDailog(final String businessname){
        final Dialog dialog = new Dialog(BusinessListActivity.this);
        dialog.setContentView(R.layout.custom_businessselectiondailog);
        Button fullbusiness =dialog.findViewById(R.id.fullbusinessBtn);
        Button customerwise = dialog.findViewById(R.id.customerwiseBtn);
        Button datewise = dialog.findViewById(R.id.datewiseBtn);

        fullbusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionTable[] t = MainActivity.database.transactionManageTable().loadAllTransactionsByBusinessID(bt.getId());
                CustomerTable[] c = MainActivity.database.customerManageTable().loadAllCustomersByBusinessID(bt.getId());
                fullbusinessReport(bt.getBusinessname(),bigDecimalData(bt.getTotalamount()),bigDecimalData(bt.getTotalrecieved()),bigDecimalData(bt.getTotalgiven()),c);

                dialog.dismiss();
            }
        });

        customerwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerTable[] c = MainActivity.database.customerManageTable().loadAllCustomersByBusinessID(bt.getId());
                ArrayList<String> list = new ArrayList<>();
                for (int i =0 ; i<c.length; i++){
                    list.add(c[i].getFullname()+" ("+c[i].getCatagory()+")");
                }
                customersDailog(businessname,list,c);
                dialog.dismiss();

            }
        });


        datewise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BusinessListActivity.this, "Functionality is in progress", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void customersDailog(final String businessname, ArrayList<String> list, final CustomerTable[] customers){
        final Dialog dialog = new Dialog(BusinessListActivity.this);
        dialog.setContentView(R.layout.custom_cusdailog);
        ListView lv =dialog.findViewById(R.id.mylistview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(BusinessListActivity.this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CustomerTable customer = customers[position];

                customerwiseReport(businessname,customer.getFullname(),customer.getCustomer_balance(),customer.getYouwillget_amount(),customer.getYouwillgive_amount(),customer);

                dialog.dismiss();

            }
        });

        dialog.show();
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
                Intent i = new Intent(BusinessListActivity.this, ApplockActivity.class);
                i.putExtra("wheree","other");
                ff=0;
                startActivity(i);
            }

        }

    }
}
