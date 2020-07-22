package com.digiaccounts.digiaccountz.Activities.signups;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.digiaccounts.digiaccountz.R;

import org.w3c.dom.Text;

public class MoniteryourMoney_Activity extends AppCompatActivity {


    Button nextbtn;
    TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moniteryour_money_);
      //  getSupportActionBar().hide();
        skip = findViewById(R.id.skipBtninone);
        nextbtn = findViewById(R.id.nextBtn_inmoniter);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(MoniteryourMoney_Activity.this,AddCustomers_SupplierActivity.class));
             finish();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoniteryourMoney_Activity.this,LanguageSelectionActivity.class));
                finish();
            }
        });
    }
}
