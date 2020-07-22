package com.digiaccounts.digiaccountz.Activities.busineses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.Activities.ApplockActivity;
import com.digiaccounts.digiaccountz.Activities.LoginActivity;
import com.digiaccounts.digiaccountz.Activities.MainActivity;
import com.digiaccounts.digiaccountz.Activities.languagemanage.LanguageManager;
import com.digiaccounts.digiaccountz.Activities.reminders.UpComingRemindersActivity;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.tables.SigninWithemailTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.business.BusinessTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.loginmanage.LoginManageDao;
import com.digiaccounts.digiaccountz.roomdatabase.tables.loginmanage.LoginManageTable;

import java.util.ArrayList;

public class NewBusineses_Activity extends AppCompatActivity {

    int ff =0;
    int PRIVATE_MODE = 0;
    String PREF_NAME = "digiaccountz";



    Spinner spinnerinnewbusiness;
    ArrayAdapter<String> adap;
    EditText busisnessnameEt;
    Button letsgoBtn;
    public static String showname = "";
    ArrayList<CurrencyBean> listc = new ArrayList<CurrencyBean>();
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_busineses_);
//        getSupportActionBar().hide();
        busisnessnameEt = findViewById(R.id.busnessEtlogin);
        letsgoBtn = findViewById(R.id.letsgoBtn);
        spinnerinnewbusiness = findViewById(R.id.spinnerinnewbusiness);

        listc.add(new CurrencyBean("RS","Rupees"));
        listc.add(new CurrencyBean("DH","UAE Dirham"));
        listc.add(new CurrencyBean("$","US Dollar"));
        listc.add(new CurrencyBean("Peso","Filipino Peso"));
        listc.add(new CurrencyBean("€","Euro"));

        list.add(getResources().getString(R.string.pleaseselectstr_inCB));
        for (int r=0 ; r<listc.size(); r++){
            list.add(listc.get(r).getCurrency()+"-"+listc.get(r).getTag());
        }

        adap = new ArrayAdapter<>(this,R.layout.customspinnerforbusinesscreate,list);
        spinnerinnewbusiness.setAdapter(adap);

        if (LanguageManager.getInstance().getCurrentlanguage().equalsIgnoreCase("ur")){
            spinnerinnewbusiness.setGravity(Gravity.RIGHT);
            busisnessnameEt.setGravity(Gravity.RIGHT);
        }

       // "Rupees - Rs", "UAE Dirham - DH", "Saudi Riyal - SR", "US Dollar - $", "Filipino Peso - Peso", "Euro - €"

        letsgoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!busisnessnameEt.getText().toString().equalsIgnoreCase("") && !spinnerinnewbusiness.getSelectedItem().toString().equalsIgnoreCase("Please Select")) {

                    BusinessTable obb = new BusinessTable();
                    obb.setUserid(LoginActivity.id);
                    obb.setBusinessname(busisnessnameEt.getText().toString());
                    obb.setTotalamount("0");
                    obb.setTotalgiven("0");
                    obb.setBusinesscurrency(listc.get(spinnerinnewbusiness.getSelectedItemPosition()-1).getTag());
                    obb.setTotalrecieved("0");
                    MainActivity.database.businessManageTable().addBusiness(obb);
                    showname = busisnessnameEt.getText().toString();
                    startActivity(new Intent(NewBusineses_Activity.this,HomeActivityWithDrawer.class));
                    finish();
                }
                else {
                    Toast.makeText(NewBusineses_Activity.this, "provide both details", Toast.LENGTH_SHORT).show();
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
                Intent i = new Intent(NewBusineses_Activity.this, ApplockActivity.class);
                i.putExtra("wheree","other");
                startActivity(i);
            }

        }

    }
}
