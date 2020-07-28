package com.digiaccounts.digiaccountz.Activities.reminders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.digiaccounts.digiaccountz.Activities.ApplockActivity;
import com.digiaccounts.digiaccountz.Activities.MainActivity;
import com.digiaccounts.digiaccountz.Activities.TermsandCondition_Activity;
import com.digiaccounts.digiaccountz.Activities.busineses.HomeActivityWithDrawer;
import com.digiaccounts.digiaccountz.Activities.busineses.TransactionAdd_RealActivity;
import com.digiaccounts.digiaccountz.Activities.busineses.ui.home.HomeFragment;
import com.digiaccounts.digiaccountz.Activities.callbacks.ReminderUpdateCallback;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.MyDatabase;
import com.digiaccounts.digiaccountz.roomdatabase.tables.business.BusinessTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.customer.CustomerTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.reminders.ReminderTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.transactions.TransactionTable;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UpComingRemindersActivity extends AppCompatActivity implements ReminderUpdateCallback {

    ImageView backbtn;
    TextView businessname;
    CustomAdapter_UpcomingRemindersListing adap;
    ListView lv;
    String businessidStr="";
    String businessnameStr = "";

    MyDatabase database;

    int ff =0;
    int PRIVATE_MODE = 0;
    String PREF_NAME = "digiaccountz";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming_reminders);
        backbtn = findViewById(R.id.backbtn);
        lv = findViewById(R.id.reminderListview);
        businessname = findViewById(R.id.businessname_upcoming);

        database = Room.databaseBuilder(getApplicationContext(),MyDatabase.class,"mydatabasee").allowMainThreadQueries().build();

        CustomAdapter_UpcomingRemindersListing.setListenerCallback(this);

        businessidStr = getIntent().getStringExtra("businessidd");
        businessnameStr = getIntent().getStringExtra("businessname");
        businessname.setText("("+ businessnameStr +")");

        final ReminderTable[] list = database.RemiderManageTable().loadAllRemindersByBusinessID(Long.parseLong(businessidStr));
        final BusinessTable bs = database.businessManageTable().loadWithID(Long.parseLong(businessidStr));
        adap = new CustomAdapter_UpcomingRemindersListing(this,list,"show");
        lv.setAdapter(adap);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TransactionTable transaction = MainActivity.database.transactionManageTable().loadAllTransactionByTransactionID(list[position].getTransactionid());
                CustomerTable cus = MainActivity.database.customerManageTable().loadCustomerusingID(transaction.getCustomerid());
                String textt = "";

                if (cus.getCatagory().equalsIgnoreCase("customer")){
                    textt= "Today you have to take "+bs.getBusinesscurrency()+" "+bigDecimalData(transaction.getAmount())+" from "+list[position].getCustomername()+", which was given on "+transaction.getDate();

                }
                else {
                    textt= "Today you have to pay "+bs.getBusinesscurrency()+" "+bigDecimalData(transaction.getAmount())+" to "+list[position].getCustomername()+", which was taken on "+transaction.getDate();
                }

                remindernDailog(textt);


            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivityWithDrawer.ff = 0;
                finish();
            }
        });
    }

    public void remindernDailog(String text){
        final Dialog dialog = new Dialog(UpComingRemindersActivity.this);
        dialog.setContentView(R.layout.custom_reminderdailog);
        TextView textView =dialog.findViewById(R.id.texttv);

        textView.setText(text);

        dialog.show();
    }



    public static String bigDecimalData(String data) {
        if (!TextUtils.isEmpty(data)) {
            BigDecimal bd = new BigDecimal(Double.parseDouble(data));
            DecimalFormat df = new DecimalFormat("#,###,###");
            return df.format(bd);
        }
        return "";
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
                Intent i = new Intent(UpComingRemindersActivity.this, ApplockActivity.class);
                i.putExtra("wheree","other");
                startActivity(i);
            }

        }

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
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void changess(){

    }

    @Override
    public void Callon() {
     ReminderTable[] list = database.RemiderManageTable().loadAllRemindersByBusinessID(Long.parseLong(businessidStr));
     BusinessTable bs = database.businessManageTable().loadWithID(Long.parseLong(businessidStr));
     adap = new CustomAdapter_UpcomingRemindersListing(UpComingRemindersActivity.this,list,"show");
     lv.setAdapter(adap);
    }
}
