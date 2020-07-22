package com.digiaccounts.digiaccountz.Activities.signups;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.digiaccounts.digiaccountz.R;

public class AddCustomers_SupplierActivity extends AppCompatActivity {

    Button nextbtn;
    TextView skipBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customers__supplier);
      //  getSupportActionBar().hide();
        nextbtn = findViewById(R.id.nextbutton_intwo);
        skipBtn = findViewById(R.id.skipBtnitwo);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddCustomers_SupplierActivity.this,DoTransactiononPhone_Activity.class));
                finish();
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddCustomers_SupplierActivity.this,LanguageSelectionActivity.class));
                finish();
            }
        });

    }
}
