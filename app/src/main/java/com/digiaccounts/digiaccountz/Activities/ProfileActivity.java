package com.digiaccounts.digiaccountz.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.KeyListener;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.Activities.busineses.HomeActivityWithDrawer;
import com.digiaccounts.digiaccountz.Activities.busineses.ui.home.CustomerListBean;
import com.digiaccounts.digiaccountz.Activities.busineses.ui.home.HomeFragment;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.tables.SigninWithemailTable;

public class ProfileActivity extends AppCompatActivity{


    int PRIVATE_MODE = 0;
    String PREF_NAME = "digiaccountz";

    ImageView backbtn;
    EditText nameet,passwordet;
    TextView emailtv,mobilenotv;


    String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
    String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

    int ff =0;

    Button savebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        backbtn = findViewById(R.id.backbtn);
        nameet = findViewById(R.id.nameEt_inp);
        passwordet = findViewById(R.id.password_inp);
        emailtv = findViewById(R.id.email_inp);
        mobilenotv = findViewById(R.id.number_inp);
        savebtn = findViewById(R.id.saveBtn);

        SigninWithemailTable[] list =  MainActivity.database.signinDetails().loadAllUsers();
        final SigninWithemailTable ob = list[0];

        nameet.setText(ob.getFullname());
        emailtv.setText(ob.getEmail());
        passwordet.setText(ob.getPasword());
        mobilenotv.setText(ob.getMobileNumber());

        nameet.setTag(nameet.getKeyListener());
        nameet.setKeyListener(null);

        passwordet.setTag(passwordet.getKeyListener());
        passwordet.setKeyListener(null);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivityWithDrawer.ff = 0;
                finish();
            }
        });

        nameet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (nameet.getRight() - nameet.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        nameet.setKeyListener((KeyListener) nameet.getTag());
                        savebtn.setVisibility(View.VISIBLE);
                        return true;
                    }
                }
                return false;
            }
        });

        passwordet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (passwordet.getRight() - passwordet.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        passwordet.setKeyListener((KeyListener) passwordet.getTag());
                        savebtn.setVisibility(View.VISIBLE);
                        passwordet.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        return true;
                    }
                }
                return false;
            }
        });


        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nameet.getText().toString().equalsIgnoreCase("") && !passwordet.getText().toString().equalsIgnoreCase(""))
                {
                    MainActivity.database.signinDetails().UpdateUserInfo(ob.getId(),nameet.getText().toString(),passwordet.getText().toString());
                    Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                    HomeActivityWithDrawer.ff = 0;
                    finish();
                }
                else {
                    Toast.makeText(ProfileActivity.this, "details should not be empty.", Toast.LENGTH_SHORT).show();
                }

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
                Intent i = new Intent(ProfileActivity.this,ApplockActivity.class);
                i.putExtra("wheree","other");
                startActivity(i);
            }

        }

    }

}
