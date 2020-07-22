package com.digiaccounts.digiaccountz.Activities.signups;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.R;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

public class CodeVerificationActivity extends AppCompatActivity {


    TextView resendcodeBtn;
    OtpView otpView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verification);
        resendcodeBtn = findViewById(R.id.resendcodeBtn);
        otpView = findViewById(R.id.otp_view);

        resendcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
              if (otp.equalsIgnoreCase("5555")){
              startActivity(new Intent(CodeVerificationActivity.this,SingupWithEmail_Activity.class));
              finish();
              }
              else {
                  Toast.makeText(CodeVerificationActivity.this, "Invalid code.", Toast.LENGTH_SHORT).show();
              }
            }
        });


    }
}
