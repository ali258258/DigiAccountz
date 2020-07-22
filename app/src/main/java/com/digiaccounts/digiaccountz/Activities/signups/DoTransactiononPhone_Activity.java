package com.digiaccounts.digiaccountz.Activities.signups;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.digiaccounts.digiaccountz.R;

public class DoTransactiononPhone_Activity extends AppCompatActivity {


    Button nextbtn;
    TextView skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_transactionon_phone_);
       // getSupportActionBar().hide();
        skip = findViewById(R.id.skipBtnithree);
        nextbtn = findViewById(R.id.nextbutton_inthree);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoTransactiononPhone_Activity.this,LanguageSelectionActivity.class));
                finish();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoTransactiononPhone_Activity.this,LanguageSelectionActivity.class));
                finish();
            }
        });


    }
}
