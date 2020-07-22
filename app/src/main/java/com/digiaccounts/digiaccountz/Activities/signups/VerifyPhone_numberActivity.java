package com.digiaccounts.digiaccountz.Activities.signups;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.R;
import com.hbb20.CountryCodePicker;

public class VerifyPhone_numberActivity extends AppCompatActivity {

TextView signinwithemail,verify;
EditText numberet;
public static String mobilenumberSaved = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);
      //  getSupportActionBar().hide();
        verify = findViewById(R.id.verifyBtn);
        numberet = findViewById(R.id.numberEt_inverify);
        signinwithemail = findViewById(R.id.signinwithemailBtn);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!numberet.getText().toString().equalsIgnoreCase("")) {
                    if (numberet.getText().length() == 10) {
                        mobilenumberSaved = "92"+numberet.getText().toString();
                        startActivity(new Intent(VerifyPhone_numberActivity.this,CodeVerificationActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(VerifyPhone_numberActivity.this, "Please enter complete mobile number", Toast.LENGTH_SHORT).show();
                        numberet.setError("Example: 3331234567");
                    }
                }
                else {
                    Toast.makeText(VerifyPhone_numberActivity.this, "Mobile Number Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signinwithemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VerifyPhone_numberActivity.this,SingupWithEmail_Activity.class));
                finish();

            }
        });


    }

//    public void onCountryPickerClick(View view) {
//        Toast.makeText(this, "call", Toast.LENGTH_SHORT).show();
//        picker.performClick();
//        picker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
//            @Override
//            public void onCountrySelected() {
//                Toast.makeText(VerifyPhone_numberActivity.this, "sdsds", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
}
