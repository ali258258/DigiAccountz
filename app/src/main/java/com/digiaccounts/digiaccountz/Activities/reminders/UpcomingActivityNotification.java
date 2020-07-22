package com.digiaccounts.digiaccountz.Activities.reminders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.digiaccounts.digiaccountz.Activities.MainActivity;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.MyDatabase;
import com.digiaccounts.digiaccountz.roomdatabase.tables.business.BusinessTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.customer.CustomerTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.reminders.ReminderTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.transactions.TransactionTable;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class UpcomingActivityNotification extends AppCompatActivity {


    ImageView backbtn;
    TextView businessname;
    CustomAdapter_UpcomingRemindersListing adap;
    ListView lv;
    String businessidStr="";
    String businessnameStr = "";

    MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming_reminders);
        backbtn = findViewById(R.id.backbtn);
        lv = findViewById(R.id.reminderListview);
        businessname = findViewById(R.id.businessname_upcoming);

        database = Room.databaseBuilder(getApplicationContext(),MyDatabase.class,"mydatabasee").allowMainThreadQueries().build();


        businessidStr = getIntent().getStringExtra("businessidd");
        businessnameStr = getIntent().getStringExtra("businessname");
        businessname.setText("("+ businessnameStr +")");

        final ReminderTable[] list = database.RemiderManageTable().loadAllRemindersByBusinessID(Long.parseLong(businessidStr));
        final BusinessTable bs = database.businessManageTable().loadWithID(Long.parseLong(businessidStr));
        adap = new CustomAdapter_UpcomingRemindersListing(this,list);
        lv.setAdapter(adap);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TransactionTable transaction = MainActivity.database.transactionManageTable().loadAllTransactionByTransactionID(list[position].getTransactionid());
                CustomerTable cus = MainActivity.database.customerManageTable().loadCustomerusingID(transaction.getCustomerid());
                String textt = "";

                if (cus.getCatagory().equalsIgnoreCase("customer")){
                    textt= "Today you have to take “"+bs.getBusinesscurrency()+"."+bigDecimalData(transaction.getAmount())+"“ from "+list[position].getCustomername()+" ,which was given on "+transaction.getDate();

                }
                else {
                    textt= "Today you have to pay “"+bs.getBusinesscurrency()+"."+bigDecimalData(transaction.getAmount())+"“ to "+list[position].getCustomername()+" ,which was taken on "+transaction.getDate();
                }

                remindernDailog(textt);


            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpcomingActivityNotification.this,MainActivity.class));
                finish();
            }
        });
    }

    public void remindernDailog(String text){
        final Dialog dialog = new Dialog(UpcomingActivityNotification.this);
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
}