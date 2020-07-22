package com.digiaccounts.digiaccountz.Activities.signups;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.digiaccounts.digiaccountz.Activities.LoginActivity;
import com.digiaccounts.digiaccountz.Activities.MainActivity;
import com.digiaccounts.digiaccountz.Activities.SettingsActivity;
import com.digiaccounts.digiaccountz.Activities.busineses.HomeActivityWithDrawer;
import com.digiaccounts.digiaccountz.Activities.languagemanage.LanguageManager;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.tables.SigninWithemailTable;

import java.util.Locale;

public class LanguageSelectionActivity extends AppCompatActivity {

   RelativeLayout englishlangBtn,urduroman,urdumain;
   String cont = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);



        if (getIntent().getStringExtra("zxcv") != null && getIntent().getStringExtra("zxcv").equalsIgnoreCase("homee")){
            cont = getIntent().getStringExtra("zxcv");
        }
        else {
            //offline
            SigninWithemailTable list[] = MainActivity.database.signinDetails().loadAllUsers();
            if (list.length > 0) {
                startActivity(new Intent(LanguageSelectionActivity.this, LoginActivity.class));
                finish();
            }
        }

        // end here



        englishlangBtn = findViewById(R.id.englishlangBtn);
        urdumain = findViewById(R.id.urdumainbtn);
        urduroman = findViewById(R.id.urduromanbtn);

        urduroman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LanguageSelectionActivity.this, "functionality is n progress.", Toast.LENGTH_SHORT).show();
            }
        });

        urdumain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cont.equalsIgnoreCase("homee")){
                    changeLocaleHome(LanguageSelectionActivity.this, new Locale("ur"));
                    LanguageManager.getInstance().setHandling(true);
                    LanguageManager.getInstance().setCurrentlanguage("ur");
                }
                else {
                    changeLocale(LanguageSelectionActivity.this, new Locale("ur"));
                    LanguageManager.getInstance().setHandling(true);
                    LanguageManager.getInstance().setCurrentlanguage("ur");
                }
                }
        });

        englishlangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cont.equalsIgnoreCase("homee")) {
                    changeLocaleHome(LanguageSelectionActivity.this, new Locale("en"));
                    LanguageManager.getInstance().setHandling(true);
                    LanguageManager.getInstance().setCurrentlanguage("en");
                } else {
                    changeLocale(LanguageSelectionActivity.this, new Locale("en"));
                    LanguageManager.getInstance().setHandling(true);
                    LanguageManager.getInstance().setCurrentlanguage("en");
                }
            }
        });

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
        startActivity(new Intent(LanguageSelectionActivity.this, VerifyPhone_numberActivity.class));
        finish();
    }

    public void changeLocaleHome(Context context, Locale locale) {

        Configuration conf = context.getResources().getConfiguration();
        conf.locale = locale;
        Locale.setDefault(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLayoutDirection(conf.locale);
        }

        context.getResources().updateConfiguration(conf, context.getResources().getDisplayMetrics());
        startActivity(new Intent(LanguageSelectionActivity.this, SettingsActivity.class));
        finish();
    }


}
