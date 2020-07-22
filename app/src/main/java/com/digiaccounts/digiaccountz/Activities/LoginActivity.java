package com.digiaccounts.digiaccountz.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.Activities.busineses.HomeActivityWithDrawer;
import com.digiaccounts.digiaccountz.Activities.busineses.NewBusineses_Activity;
import com.digiaccounts.digiaccountz.Activities.busineses.TransactionAdd_RealActivity;
import com.digiaccounts.digiaccountz.Activities.languagemanage.LanguageManager;
import com.digiaccounts.digiaccountz.Activities.signups.MoniteryourMoney_Activity;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.tables.SigninWithemailTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.business.BusinessTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.loginmanage.LoginManageTable;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
//ae09a5
    EditText emailEt,passwordEt;
    Button signinBtn;
    public static long id;

    TextView signupBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getSupportActionBar().hide();
        emailEt = findViewById(R.id.emailEtlogin);
        passwordEt = findViewById(R.id.passwordEtlogin);
        signinBtn = findViewById(R.id.signinBtn);
        signupBtn = findViewById(R.id.signup_inlogin);

        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_CONTACTS,Manifest.permission.CAMERA,Manifest.permission.ACCESS_NOTIFICATION_POLICY},1);
        // language changing code

//        if (LanguageManager.getInstance().getCurrentlanguage().equalsIgnoreCase("ur")){
//            passwordEt.setGravity(Gravity.RIGHT);
//            emailEt.setGravity(Gravity.RIGHT);
//        }
//        if (LanguageManager.getInstance().isHandling() == false) {
//
//            changeLocale(LoginActivity.this, new Locale("ur"));
//            LanguageManager.getInstance().setHandling(true);
//            LanguageManager.getInstance().setCurrentlanguage("ur");
//        }


        LoginManageTable list[] = MainActivity.database.loginManageTable().loadAll();
        if (list.length>0)
        {
            if (list[0].getCheck().equalsIgnoreCase("active"))
            {
                id = list[0].getUserid();
                BusinessTable bst =MainActivity.database.businessManageTable().loadWithID(id);
                if (bst != null){
                    startActivity(new Intent(LoginActivity.this, HomeActivityWithDrawer.class));
                    finish();
                }
                else {
                    startActivity(new Intent(LoginActivity.this, NewBusineses_Activity.class));
                    finish();
                }
            }
        }


        // end here


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MoniteryourMoney_Activity.class));
                finish();
            }
        });

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emailEt.getText().toString().equalsIgnoreCase("") && !passwordEt.getText().toString().equalsIgnoreCase(""))
                {
                    if (emailEt.getText().length()==12){
                        if (passwordEt.getText().toString().length()>=4){

                                SigninWithemailTable list[] = MainActivity.database.signinDetails().loadAllUserLogin(emailEt.getText().toString(),passwordEt.getText().toString());
                                if (list.length>0)
                                {
                                    id = list[0].getId();
                                    LoginManageTable loginlist[] = MainActivity.database.loginManageTable().loadWithID(id);
                                    if (loginlist.length>0)
                                    {

                                        int u= MainActivity.database.loginManageTable().UpdateStatus("active",id);
                                        BusinessTable bst =MainActivity.database.businessManageTable().loadWithID(id);
                                        if (bst !=null){
                                            Toast.makeText(LoginActivity.this, "Login Succcessfull", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, HomeActivityWithDrawer.class));
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(LoginActivity.this, "Login Succcessfull", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, NewBusineses_Activity.class));
                                            finish();
                                        }


                                    }
                                    else {

                                        LoginManageTable vv = new LoginManageTable();
                                        vv.setUserid(id);
                                        vv.setCheck("active");
                                        MainActivity.database.loginManageTable().add(vv);

                                        BusinessTable bst =MainActivity.database.businessManageTable().loadWithID(id);
                                        if (bst !=null){
                                            Toast.makeText(LoginActivity.this, "Login Succcessfull", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, HomeActivityWithDrawer.class));
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(LoginActivity.this, "Login Succcessfull", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, NewBusineses_Activity.class));
                                            finish();
                                        }
                                    }
                                }
                            else
                                {
                                Toast.makeText(LoginActivity.this, "Invalid Credentials.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "password length is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Number should be proper", Toast.LENGTH_SHORT).show();
                        emailEt.setError("Example: 923331234567");
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });




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

    public void changeLocale(Context context, Locale locale) {

        Configuration conf = context.getResources().getConfiguration();
        conf.locale = locale;
        Locale.setDefault(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLayoutDirection(conf.locale);
        }

        context.getResources().updateConfiguration(conf, context.getResources().getDisplayMetrics());
        String url = context.getResources().getString(R.string.amountinAT);
      //  Toast.makeText(context, ": "+url, Toast.LENGTH_SHORT).show();
        this.recreate();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
