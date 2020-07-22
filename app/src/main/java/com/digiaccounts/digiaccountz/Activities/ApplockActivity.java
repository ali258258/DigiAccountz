package com.digiaccounts.digiaccountz.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.tables.SigninWithemailTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.loginmanage.LoginManageTable;

public class ApplockActivity extends AppCompatActivity {

    Button submitBtn;
    EditText passwordet;
    String ss= "";

    public static int checking = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applock);
        submitBtn = findViewById(R.id.submitBtn_applock);
        passwordet = findViewById(R.id.passwordEt_applock);
         ss= getIntent().getStringExtra("wheree");
         checking = 0;

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!passwordet.getText().toString().equalsIgnoreCase("")){

                    LoginManageTable list[] = MainActivity.database.loginManageTable().loadAll();
                    SigninWithemailTable ob = MainActivity.database.signinDetails().loadUserByID(list[0].getUserid());

                    if (passwordet.getText().toString().equalsIgnoreCase(ob.getPasword())) {
                        if (ss.equalsIgnoreCase("other")) {
                            checking =1;
                            finish();
                        } else if (ss.equalsIgnoreCase("login")) {
                            startActivity(new Intent(ApplockActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                    else {
                        Toast.makeText(ApplockActivity.this, "password not matched.", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(ApplockActivity.this, "password required.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
