package com.digiaccounts.digiaccountz.Activities.signups;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.Activities.LoginActivity;
import com.digiaccounts.digiaccountz.Activities.MainActivity;
import com.digiaccounts.digiaccountz.Activities.languagemanage.LanguageManager;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.tables.SigninWithemailTable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingupWithEmail_Activity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    EditText dobEt;
    EditText fullnameEt,emailEt,passwordEt,confirmpasswordEt;
    DatePickerDialog.OnDateSetListener date;
    Button nextbutton;

    TextView signip_inTv;

    public static int checkfirst = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup_with_email_);
     //   getSupportActionBar().hide();
        dobEt = findViewById(R.id.dobEt);
//        dobEt.setFocusable(false);
//        dobEt.setKeyListener(null);
        fullnameEt = findViewById(R.id.fullnameEt);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        confirmpasswordEt = findViewById(R.id.confirmpasswordEt);
        signip_inTv = findViewById(R.id.signip_in);
        nextbutton = findViewById(R.id.nextbutton_inthree);
        dobEt.setEnabled(false);
        dobEt.setClickable(false);
        dobEt.setFocusable(false);
        dobEt.setText(VerifyPhone_numberActivity.mobilenumberSaved);


        if (LanguageManager.getInstance().getCurrentlanguage().equalsIgnoreCase("ur")){
            dobEt.setGravity(Gravity.RIGHT);
            fullnameEt.setGravity(Gravity.RIGHT);
            emailEt.setGravity(Gravity.RIGHT);
            passwordEt.setGravity(Gravity.RIGHT);
            confirmpasswordEt.setGravity(Gravity.RIGHT);
        }

        signip_inTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingupWithEmail_Activity.this,LoginActivity.class));
                finish();
            }
        });

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if (!fullnameEt.getText().toString().equalsIgnoreCase("") &&
                      !dobEt.getText().toString().equalsIgnoreCase("") &&
                      !passwordEt.getText().toString().equalsIgnoreCase("") &&
                      !confirmpasswordEt.getText().toString().equalsIgnoreCase("")) {
//                  if (isEmailValid(emailEt.getText().toString())) {
                  if (dobEt.getText().toString().equalsIgnoreCase(VerifyPhone_numberActivity.mobilenumberSaved))
                  {
                      if (passwordEt.getText().toString().length()>=4 && confirmpasswordEt.getText().toString().length()>=4)
                      {

                          if (passwordEt.getText().toString().equals(confirmpasswordEt.getText().toString()))
                          {
                           //   Toast.makeText(SingupWithEmail_Activity.this, "Registered successfully.", Toast.LENGTH_SHORT).show();
//                                finish();

                              SigninWithemailTable ob = new SigninWithemailTable();
                              ob.setFullname(fullnameEt.getText().toString());
                              ob.setEmail(emailEt.getText().toString());
                              ob.setMobileNumber(dobEt.getText().toString());
                              ob.setPasword(passwordEt.getText().toString());

                              SigninWithemailTable list[] = MainActivity.database.signinDetails().loadAllUsersaccordingtoEmail(emailEt.getText().toString());
                              Log.i("rfvcgrt",""+list.length);
                              if (list.length==0){
                                  MainActivity.database.signinDetails().addDetails(ob);
                                  Toast.makeText(SingupWithEmail_Activity.this, "Registered successfully.", Toast.LENGTH_SHORT).show();
                                  checkfirst =1;
                                  startActivity(new Intent(SingupWithEmail_Activity.this, LoginActivity.class));
                                  finish();
                              }
                              else {
                                  Toast.makeText(SingupWithEmail_Activity.this, "User Already Registered", Toast.LENGTH_SHORT).show();
                              }
                          }
                          else {
                              Toast.makeText(SingupWithEmail_Activity.this, "passwords not matched.", Toast.LENGTH_SHORT).show();
                                passwordEt.setError("not matched");
                                confirmpasswordEt.setError("not matched");
                          }
                      }
                      else {
                          Toast.makeText(SingupWithEmail_Activity.this, "Pin Code digits must be greater or equal to four (4)", Toast.LENGTH_SHORT).show();
                          passwordEt.setError("must be greater than 4 digits");
                          confirmpasswordEt.setError("must be greater than 4 digits");
                      }

                  } else
                      {
                      Toast.makeText(SingupWithEmail_Activity.this, "Number must be same as you entered on previous screen", Toast.LENGTH_SHORT).show();
                      dobEt.setError("Mobile Number is not same");
                  }
              }
              else {
                  Toast.makeText(SingupWithEmail_Activity.this, "Fill All feilds.", Toast.LENGTH_SHORT).show();
              }
            }
        });

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };


        dobEt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                new DatePickerDialog(SingupWithEmail_Activity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });






    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dobEt.setText(sdf.format(myCalendar.getTime()));
    }


    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
