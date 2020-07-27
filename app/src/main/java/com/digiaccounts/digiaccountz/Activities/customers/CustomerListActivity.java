package com.digiaccounts.digiaccountz.Activities.customers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import com.digiaccounts.digiaccountz.Activities.ApplockActivity;
import com.digiaccounts.digiaccountz.Activities.MainActivity;
import com.digiaccounts.digiaccountz.Activities.busineses.HomeActivityWithDrawer;
import com.digiaccounts.digiaccountz.Activities.busineses.TransactionAdd_RealActivity;
import com.digiaccounts.digiaccountz.Activities.callbacks.CreateTransactionCallback;
import com.digiaccounts.digiaccountz.Activities.callbacks.ReCreateTransactionsCallbacks;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.MyDatabase;
import com.digiaccounts.digiaccountz.roomdatabase.tables.customer.CustomerTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.transactions.TransactionTable;
import com.maltaisn.calcdialog.CalcDialog;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CustomerListActivity extends AppCompatActivity implements CreateTransactionCallback, ReCreateTransactionsCallbacks, CalcDialog.CalcDialogCallback {

    public static int ff =0;
    int PRIVATE_MODE = 0;
    String PREF_NAME = "digiaccountz";

    @Nullable
    private BigDecimal value = null;

    ListView lv ;
    CustomAdapter_customerListing adap;
    Button youwillreceiveBtn,youwillgiveBtn;

    String businessid="";
    String customerid="";
    String balance="";
    String youwillget  ="";
    String youwillgive="";
    String nameTitle="";
    String currency="";
    String catagory="";

    TextView giveTv,getTv,balanceTv;
    ImageView filterMv;

    ArrayList<customerDetailsBean> Tlist = new ArrayList<>();

    final CalcDialog calcDialog = new CalcDialog();


    DatePickerDialog.OnDateSetListener startdate,enddate;
    final Calendar startdateCalendar = Calendar.getInstance();
    Calendar enddareCalendar = Calendar.getInstance();
    String startdateStr="";
    String enddateStr="";


    TextView balanceheading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        nameTitle= getIntent().getStringExtra("customername");
        TransactionAdd_RealActivity.setListenerCallback(this);
        CustomAdapter_customerListing.setListenerCallback(this);
        lv = findViewById(R.id.mylistview);
        giveTv= findViewById(R.id.c_youwillgiveTvv);
        balanceTv= findViewById(R.id.c_balanceeTvv);
        getTv= findViewById(R.id.c_youwillgetTvv);
        balanceheading = findViewById(R.id.balanheading);
        youwillreceiveBtn = findViewById(R.id.youwillreceiveBtn);
        youwillgiveBtn = findViewById(R.id.youwillgiveBtn);
        filterMv = findViewById(R.id.ssaa);
        ff=0;
        Toolbar toolbar = findViewById(R.id.toolbarme);
        toolbar.setTitle(nameTitle);
        toolbar.inflateMenu(R.menu.customerlistmenu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         customerid= getIntent().getStringExtra("customeridd");
         businessid= getIntent().getStringExtra("businessidd");
         balance= getIntent().getStringExtra("cbalance");
        youwillget= getIntent().getStringExtra("cyouwillget");
        currency= getIntent().getStringExtra("busnesscurrency");
         youwillgive= getIntent().getStringExtra("cyouwillgive");
         catagory= getIntent().getStringExtra("catagory");


        adap = new CustomAdapter_customerListing(this,Tlist);
        lv.setAdapter(adap);

        NormalList();

        if (Integer.parseInt(youwillget)>=Integer.parseInt(youwillgive)){
            balanceTv.setTextColor(Color.parseColor("#ae09a5"));
            balanceheading.setText("You will Pay");
        }
        else if (Integer.parseInt(youwillget)<Integer.parseInt(youwillgive)){
            balanceTv.setTextColor(Color.parseColor("#0066ff"));
            balanceheading.setText("You will Receive");

        }


        getTv.setText(currency+": "+bigDecimalData(youwillget));
        giveTv.setText(currency+": "+bigDecimalData(youwillgive));
        balanceTv.setText(currency+": "+ bigDecimalData(""+Math.abs(Integer.parseInt(balance))));




        youwillgiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerListActivity.this, TransactionAdd_RealActivity.class);
                intent.putExtra("checkingstatus","Sent");
                intent.putExtra("customeridd",customerid);
                intent.putExtra("customername", nameTitle);
                intent.putExtra("businessidd",businessid);
                intent.putExtra("customertype",catagory);
                startActivity(intent);
            }
        });

        youwillreceiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerListActivity.this, TransactionAdd_RealActivity.class);
                intent.putExtra("checkingstatus","Recieved");
                intent.putExtra("customeridd",customerid);
                intent.putExtra("customername",nameTitle);
                intent.putExtra("businessidd",businessid);
                intent.putExtra("customertype",catagory);
                startActivity(intent);
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
                showPopupMenu(CustomerListActivity.this,v);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.customerlistmenu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        HomeActivityWithDrawer.ff = 0;
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
                case R.id.action_editcustomer:
                    Intent i = new Intent(CustomerListActivity.this,CustomerUpdateActivity.class);
                    i.putExtra("custid",customerid);
                    startActivity(i);
                return true;

            case R.id.action_calculator:
                calcDialog.getSettings().setInitialValue(value);
                calcDialog.show(getSupportFragmentManager(), "calc_dialog");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void Callon(long balance, long youwillgive, long youwillget) {

        if (youwillget>=youwillgive){
            balanceTv.setTextColor(Color.parseColor("#ae09a5"));
            balanceheading.setText("You will Pay");
        }
        else if (youwillget<youwillgive){
            balanceTv.setTextColor(Color.parseColor("#0066ff"));
            balanceheading.setText("You will Receive");
        }

        Tlist.clear();
        TransactionTable[] transactionlist=MainActivity.database.transactionManageTable().loadAllTransactionsByCustomerID(Long.parseLong(customerid));
        CustomerTable ob = MainActivity.database.customerManageTable().loadCustomerusingID(Long.parseLong(customerid));
        getTv.setText(bigDecimalData(ob.getYouwillget_amount()));
        giveTv.setText(bigDecimalData(ob.getYouwillgive_amount()));
        balanceTv.setText(currency+": "+bigDecimalData(""+Math.abs(Integer.parseInt(ob.getCustomer_balance()))));
        if (transactionlist.length>0)
        {
            for (int i = 0; i<transactionlist.length ; i++) {
                if (transactionlist[i].getStatus().equalsIgnoreCase("Sent")) {
                    Tlist.add(new customerDetailsBean(transactionlist[i].getId(),transactionlist[i].getBusinessid(),transactionlist[i].getCustomerid(),transactionlist[i].getStatus(), transactionlist[i].getTransactiontype(),
                            transactionlist[i].getDate()+" "+transactionlist[i].getTimee(), transactionlist[i].getAmount(), transactionlist[i].getDescription(), Color.parseColor("#0066ff"),transactionlist[i].getReminder(),transactionlist[i].getDescription(),transactionlist[i].getImage()));
                }
                else if (transactionlist[i].getStatus().equalsIgnoreCase("Recieved")){
                    Tlist.add(new customerDetailsBean(transactionlist[i].getId(),transactionlist[i].getBusinessid(),transactionlist[i].getCustomerid(),transactionlist[i].getStatus(), transactionlist[i].getTransactiontype(),
                            transactionlist[i].getDate()+" "+transactionlist[i].getTimee(), transactionlist[i].getAmount(), transactionlist[i].getDescription(), Color.parseColor("#ae09a5"),transactionlist[i].getReminder(),transactionlist[i].getDescription(),transactionlist[i].getImage()));
                }
            }
        }
        adap.notifyDataSetChanged();
    }
c

    @Override
    public void Changes(String recieve, String get, String balance) {

        giveTv.setText(get);
        getTv.setText(recieve);
        balanceTv.setText(""+Math.abs(Integer.parseInt(balance)));

        if (Integer.parseInt(recieve)>=Integer.parseInt(get)){
            balanceTv.setTextColor(Color.parseColor("#ae09a5"));
            balanceheading.setText("You will Pay");
        }
        else if (Integer.parseInt(recieve)<Integer.parseInt(get)){
            balanceTv.setTextColor(Color.parseColor("#0066ff"));
            balanceheading.setText("You will Receive");
        }


        Tlist.clear();
        TransactionTable[] transactionlist=MainActivity.database.transactionManageTable().loadAllTransactionsByCustomerID(Long.parseLong(customerid));
        if (transactionlist.length>0)
        {
            for (int i = 0; i<transactionlist.length ; i++) {
                if (transactionlist[i].getStatus().equalsIgnoreCase("Sent")) {
                    Tlist.add(new customerDetailsBean(transactionlist[i].getId(),transactionlist[i].getBusinessid(),transactionlist[i].getCustomerid(),transactionlist[i].getStatus(), transactionlist[i].getTransactiontype(),
                            transactionlist[i].getDate()+" "+transactionlist[i].getTimee(), transactionlist[i].getAmount(), transactionlist[i].getDescription(), Color.parseColor("#0066ff"),transactionlist[i].getReminder(),transactionlist[i].getDescription(),transactionlist[i].getImage()));
                }
                else if (transactionlist[i].getStatus().equalsIgnoreCase("Recieved")){
                    Tlist.add(new customerDetailsBean(transactionlist[i].getId(),transactionlist[i].getBusinessid(),transactionlist[i].getCustomerid(),transactionlist[i].getStatus(), transactionlist[i].getTransactiontype(),
                            transactionlist[i].getDate()+" "+transactionlist[i].getTimee(), transactionlist[i].getAmount(), transactionlist[i].getDescription(), Color.parseColor("#ae09a5"),transactionlist[i].getReminder(),transactionlist[i].getDescription(),transactionlist[i].getImage()));
                }
            }
        }
        adap.notifyDataSetChanged();
//        adap.notify();

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

        DatePickerDialog pckkk =  new DatePickerDialog(CustomerListActivity.this, enddate, enddareCalendar
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

                    DatePickerDialog pckkk =  new DatePickerDialog(CustomerListActivity.this, startdate, startdateCalendar
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

    public void filteredList(String startdate,String enddate) {

        Tlist.clear();
        TransactionTable[] checkingList = MainActivity.database.transactionManageTable().loadAllTransactionsDatesWiseWithCustomer(Long.parseLong(customerid),startdate, enddate);
        for (int i = 0; i<checkingList.length ; i++) {
            if (checkingList[i].getStatus().equalsIgnoreCase("Sent")) {
                Tlist.add(new customerDetailsBean(checkingList[i].getId(),checkingList[i].getBusinessid(),checkingList[i].getCustomerid(),checkingList[i].getStatus(), checkingList[i].getTransactiontype(),
                        checkingList[i].getDate()+" "+checkingList[i].getTimee(), checkingList[i].getAmount(), checkingList[i].getDescription(), Color.parseColor("#0066ff"),checkingList[i].getReminder(),checkingList[i].getDescription(),checkingList[i].getImage()));
            }
            else if (checkingList[i].getStatus().equalsIgnoreCase("Recieved")){
                Tlist.add(new customerDetailsBean(checkingList[i].getId(),checkingList[i].getBusinessid(),checkingList[i].getCustomerid(),checkingList[i].getStatus(), checkingList[i].getTransactiontype(),
                        checkingList[i].getDate()+" "+checkingList[i].getTimee(), checkingList[i].getAmount(), checkingList[i].getDescription(), Color.parseColor("#ae09a5"),checkingList[i].getReminder(),checkingList[i].getDescription(),checkingList[i].getImage()));
            }
        }
        adap.notifyDataSetChanged();

    }


    public void NormalList() {

        Tlist.clear();
        TransactionTable[] transactionlist=MainActivity.database.transactionManageTable().loadAllTransactionsByCustomerID(Long.parseLong(customerid));
        if (transactionlist.length>0)
        {
            for (int i = 0; i<transactionlist.length ; i++) {
                if (transactionlist[i].getStatus().equalsIgnoreCase("Sent")) {
                    Tlist.add(new customerDetailsBean(transactionlist[i].getId(),transactionlist[i].getBusinessid(),transactionlist[i].getCustomerid(),transactionlist[i].getStatus(), transactionlist[i].getTransactiontype(),
                            transactionlist[i].getDate()+" "+transactionlist[i].getTimee(), transactionlist[i].getAmount(), transactionlist[i].getDescription(), Color.parseColor("#0066ff"),transactionlist[i].getReminder(),transactionlist[i].getDescription(),transactionlist[i].getImage()));
                }
                else if (transactionlist[i].getStatus().equalsIgnoreCase("Recieved")){
                    Tlist.add(new customerDetailsBean(transactionlist[i].getId(),transactionlist[i].getBusinessid(),transactionlist[i].getCustomerid(),transactionlist[i].getStatus(), transactionlist[i].getTransactiontype(),
                            transactionlist[i].getDate()+" "+transactionlist[i].getTimee(), transactionlist[i].getAmount(), transactionlist[i].getDescription(), Color.parseColor("#ae09a5"),transactionlist[i].getReminder(),transactionlist[i].getDescription(),transactionlist[i].getImage()));
                }
            }
        }
        adap.notifyDataSetChanged();
    }

    @Override
    public void onValueEntered(int requestCode, @Nullable BigDecimal value) {
        this.value = value;
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
                Intent i = new Intent(CustomerListActivity.this, ApplockActivity.class);
                i.putExtra("wheree","other");
                ff=0;
                startActivity(i);
            }

        }

    }

}
