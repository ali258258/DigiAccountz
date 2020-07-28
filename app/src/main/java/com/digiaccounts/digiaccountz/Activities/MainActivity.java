package com.digiaccounts.digiaccountz.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.Activities.busineses.HomeActivityWithDrawer;
import com.digiaccounts.digiaccountz.Activities.busineses.NewBusineses_Activity;
import com.digiaccounts.digiaccountz.Activities.busineses.TransactionAdd_RealActivity;
import com.digiaccounts.digiaccountz.Activities.customers.Contacts.ContactsLoad;
import com.digiaccounts.digiaccountz.Activities.customers.CustomerUpdateActivity;
import com.digiaccounts.digiaccountz.Activities.signups.LanguageSelectionActivity;
import com.digiaccounts.digiaccountz.Activities.signups.MoniteryourMoney_Activity;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.MyDatabase;
import com.digiaccounts.digiaccountz.roomdatabase.tables.SigninWithemailTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.business.BusinessTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.loginmanage.LoginManageTable;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

   public static MyDatabase database;

    int PRIVATE_MODE = 0;
    String PREF_NAME = "digiaccountz";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        database = Room.databaseBuilder(getApplicationContext(),MyDatabase.class,"mydatabasee").allowMainThreadQueries().build();

        SharedPreferences sharedPref= getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        final String stringObject = sharedPref.getString("applockstatus","");

        final SigninWithemailTable list[] = MainActivity.database.signinDetails().loadAllUsers();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (list.length>0)
                {
                    if (stringObject.equalsIgnoreCase("enable")){
                        Intent i = new Intent(MainActivity.this, ApplockActivity.class);
                        i.putExtra("wheree","login");
                        startActivity(i);
                        finish();
                    }
                    else {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                }
                else {
                        startActivity(new Intent(MainActivity.this, MoniteryourMoney_Activity.class));
                        finish();

                }

            }
        }, 3000);
    }

//    LoginManageTable list[] = MainActivity.database.loginManageTable().loadAll();
//        if (list.length>0)
//    {
//        if (list[0].getCheck().equalsIgnoreCase("active"))
//        {
//            id = list[0].getUserid();
//            BusinessTable bst =MainActivity.database.businessManageTable().loadWithID(id);
//            if (bst != null){
//                startActivity(new Intent(LoginActivity.this, HomeActivityWithDrawer.class));
//                finish();
//            }
//            else {
//                startActivity(new Intent(LoginActivity.this, NewBusineses_Activity.class));
//                finish();
//            }
//        }
//    }



}
