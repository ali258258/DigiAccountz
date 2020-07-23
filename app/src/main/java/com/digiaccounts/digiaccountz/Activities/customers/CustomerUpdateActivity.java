package com.digiaccounts.digiaccountz.Activities.customers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.Activities.ApplockActivity;
import com.digiaccounts.digiaccountz.Activities.MainActivity;
import com.digiaccounts.digiaccountz.Activities.busineses.HomeActivityWithDrawer;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.tables.customer.CustomerTable;

public class CustomerUpdateActivity extends AppCompatActivity {

    int ff =0;
    int PRIVATE_MODE = 0;
    String PREF_NAME = "digiaccountz";


    String customerid;
    EditText nameEt,emailEt,mobilenumberEt,addressEt;
    Button updateBtn;

    ImageView backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        customerid= getIntent().getStringExtra("custid");
        backbtn = findViewById(R.id.backbtn);
        nameEt = findViewById(R.id.nameEt_increatecustomer);
        emailEt = findViewById(R.id.emmailEt_increatecustomer);
        mobilenumberEt = findViewById(R.id.mobileEt_increatecustomer);
        addressEt = findViewById(R.id.AdressEt_increatecustomer);
        updateBtn = findViewById(R.id.submittBtn);

        CustomerTable ob = MainActivity.database.customerManageTable().loadCustomerusingID(Long.parseLong(customerid));

        nameEt.setText(ob.getFullname());
        emailEt.setText(ob.getEmail());
        mobilenumberEt.setText(ob.getMobileNumber());
        addressEt.setText(ob.getAddress());



        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerListActivity.ff=0;
                finish();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             MainActivity.database.customerManageTable().UpdateInfo(Long.parseLong(customerid),nameEt.getText().toString(),
                     emailEt.getText().toString(),mobilenumberEt.getText().toString(),addressEt.getText().toString());
                Toast.makeText(CustomerUpdateActivity.this, "Sucessfully Updated.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(CustomerUpdateActivity.this, HomeActivityWithDrawer.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);


            }
        });

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
                Intent i = new Intent(CustomerUpdateActivity.this, ApplockActivity.class);
                i.putExtra("wheree","other");
                ff=0;
                startActivity(i);
            }

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CustomerListActivity.ff=0;
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
