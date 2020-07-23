package com.digiaccounts.digiaccountz.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.digiaccounts.digiaccountz.Activities.busineses.BusinessListActivity;
import com.digiaccounts.digiaccountz.Activities.busineses.HomeActivityWithDrawer;
import com.digiaccounts.digiaccountz.Activities.customers.CustomerListActivity;
import com.digiaccounts.digiaccountz.Activities.customers.CustomerUpdateActivity;
import com.digiaccounts.digiaccountz.Activities.signups.LanguageSelectionActivity;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.tables.customer.CustomerTable;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    ImageView backbtn;
    Button langbtn,applockbtn;

    Dialog dialog;
    Button mainbtn;

    int PRIVATE_MODE = 0;
    String PREF_NAME = "digiaccountz";

    int ff = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        backbtn = findViewById(R.id.backbtn);
        langbtn = findViewById(R.id.langBtn);
        applockbtn= findViewById(R.id.applockbtn);

        dialog = new Dialog(SettingsActivity.this);
        dialog.setContentView(R.layout.custom_applockdialog);
        mainbtn =dialog.findViewById(R.id.enable_disableBtn);
        ff=0;
        SharedPreferences sharedPref= getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        String stringObject = sharedPref.getString("applockstatus","");

        if (stringObject.equalsIgnoreCase("") || stringObject.equalsIgnoreCase("disable"))
        {
            mainbtn.setText("Enable");
        }
        else if (stringObject.equalsIgnoreCase("enable")){
            mainbtn.setText("Disable");
        }

        mainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
                String str = sharedPref.getString("applockstatus", "");

                if (str.equalsIgnoreCase("") || str.equalsIgnoreCase("disable"))
                {
                   SharedPreferences.Editor editor =  sharedPref.edit();
                    editor.putString("applockstatus","enable");
                    editor.apply();
                    editor.commit();
                    mainbtn.setText("Disable");
//
                }
                else if (str.equalsIgnoreCase("enable")){

                    SharedPreferences.Editor editor =  sharedPref.edit();
                    editor.putString("applockstatus","disable");
                    editor.apply();
                    editor.commit();

                    mainbtn.setText("Enable");

                }

                dialog.dismiss();

            }
        });


        langbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, LanguageSelectionActivity.class);
                i.putExtra("zxcv","homee");
                startActivity(i);
                finish();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivityWithDrawer.ff = 0;
                finish();
            }
        });

        applockbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applockDailog();
            }
        });

    }



    public void applockDailog(){
        dialog.show();
    }



//    SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
//    SharedPreferences.Editor editor =  sharedPref.edit();
//                    editor.putString("Tokenobject",new Gson().toJson(dmtoken));
//                    editor.apply();
//                    editor.commit();
//

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
                Intent i = new Intent(SettingsActivity.this,ApplockActivity.class);
                i.putExtra("wheree","other");
                ff=0;
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

}
