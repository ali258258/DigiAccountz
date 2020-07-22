package com.digiaccounts.digiaccountz.Activities.customers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.Activities.ApplockActivity;
import com.digiaccounts.digiaccountz.Activities.MainActivity;
import com.digiaccounts.digiaccountz.Activities.ProfileActivity;
import com.digiaccounts.digiaccountz.Activities.busineses.BusinessListActivity;
import com.digiaccounts.digiaccountz.Activities.busineses.HomeActivityWithDrawer;
import com.digiaccounts.digiaccountz.Activities.busineses.ui.home.HomeFragment;
import com.digiaccounts.digiaccountz.Activities.callbacks.CreateCustomerCallbacks;
import com.digiaccounts.digiaccountz.Activities.customers.Contacts.ContactsBean;
import com.digiaccounts.digiaccountz.Activities.customers.Contacts.ContactsLoad;
import com.digiaccounts.digiaccountz.Activities.customers.Contacts.CustomAdapter_contacts;
import com.digiaccounts.digiaccountz.Activities.reminders.UpComingRemindersActivity;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.tables.customer.CustomerTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateCustomerActivity extends AppCompatActivity {

    int ff =0;
    int PRIVATE_MODE = 0;
    String PREF_NAME = "digiaccountz";


    ImageView customerCh,supplierCh;
    TextView customerTv, supplierTv;

    ImageView backbtn;

    EditText nameEt,emailEt,mobilenumberEt,addressEt;
    String catagory = "customer";
    Button submitBtn;

    ImageView contactMv;

    CustomAdapter_contacts adap_contacts;
//    ArrayList<ContactsBean> contactslist = new ArrayList<>();

   static CreateCustomerCallbacks listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);

        contactMv = findViewById(R.id.contactmv);

        customerCh = findViewById(R.id.customerCh);
        supplierCh = findViewById(R.id.supplierCH);

        customerTv = findViewById(R.id.customerTvv);
        supplierTv = findViewById(R.id.supplierTvv);

        nameEt = findViewById(R.id.nameEt_increatecustomer);
        emailEt = findViewById(R.id.emmailEt_increatecustomer);
        mobilenumberEt = findViewById(R.id.mobileEt_increatecustomer);
        addressEt = findViewById(R.id.AdressEt_increatecustomer);
        submitBtn = findViewById(R.id.submittBtn);
        backbtn = findViewById(R.id.backbtn);
        ff=0;
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivityWithDrawer.ff = 0;
                BusinessListActivity.ff = 0;
                finish();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if (!nameEt.getText().toString().equalsIgnoreCase(""))
                {

                    if (!mobilenumberEt.getText().toString().equalsIgnoreCase("")){
                        if(mobilenumberEt.getText().toString().contains("92") || mobilenumberEt.getText().toString().contains("03"))
                        {

                            Date c = Calendar.getInstance().getTime();
                            System.out.println("Current time => " + c);

                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                            String formattedDate = df.format(c);


                            //  Toast.makeText(CreateCustomerActivity.this, ": "+HomeFragment.currentBusinessID, Toast.LENGTH_SHORT).show();
                            CustomerTable obb = new CustomerTable();
                            obb.setBusinessid(HomeFragment.currentBusinessID);
                            obb.setFullname(nameEt.getText().toString());
                            obb.setEmail(emailEt.getText().toString());
                            obb.setMobileNumber(mobilenumberEt.getText().toString());
                            obb.setAddress(addressEt.getText().toString());
                            obb.setCatagory(catagory);
                            obb.setDatetime(formattedDate);
                            obb.setYouwillget_amount("0");
                            obb.setYouwillgive_amount("0");
                            obb.setCustomer_balance("0");
                            MainActivity.database.customerManageTable().AddCustomers(obb);
                            listener.Callon(obb);
                            Toast.makeText(CreateCustomerActivity.this, "Customer Added Sucessfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(CreateCustomerActivity.this, "Number Format is not Valid", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {

                        Date c = Calendar.getInstance().getTime();
                        System.out.println("Current time => " + c);

                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                        String formattedDate = df.format(c);


                        //  Toast.makeText(CreateCustomerActivity.this, ": "+HomeFragment.currentBusinessID, Toast.LENGTH_SHORT).show();
                        CustomerTable obb = new CustomerTable();
                        obb.setBusinessid(HomeFragment.currentBusinessID);
                        obb.setFullname(nameEt.getText().toString());
                        obb.setEmail(emailEt.getText().toString());
                        obb.setMobileNumber(mobilenumberEt.getText().toString());
                        obb.setAddress(addressEt.getText().toString());
                        obb.setCatagory(catagory);
                        obb.setDatetime(formattedDate);
                        obb.setYouwillget_amount("0");
                        obb.setYouwillgive_amount("0");
                        obb.setCustomer_balance("0");
                        MainActivity.database.customerManageTable().AddCustomers(obb);
                        listener.Callon(obb);
                        Toast.makeText(CreateCustomerActivity.this, "Customer Added Sucessfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });


        customerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supplierCh.setImageResource(R.drawable.lineborder);
                customerCh.setImageResource(R.color.screenbackgroundsBlue);
                catagory = "customer";


            }
        });

        supplierTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customerCh.setImageResource(R.drawable.lineborder);
                supplierCh.setImageResource(R.color.screenbackgroundsBlue);
                catagory = "supplier";

            }
        });


        customerCh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supplierCh.setImageResource(R.drawable.lineborder);
                customerCh.setImageResource(R.color.screenbackgroundsBlue);
                catagory = "customer";


            }
        });

        supplierCh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customerCh.setImageResource(R.drawable.lineborder);
                supplierCh.setImageResource(R.color.screenbackgroundsBlue);
                catagory = "supplier";

            }
        });

        contactMv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactsDailog();
            }
        });

        //getContactList();

    }


    public static void setListenerCallback(CreateCustomerCallbacks callbacks){
        listener = callbacks;
    }



    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (22) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    startManagingCursor(c);
                    if (c.moveToFirst()) {
                        String name = c.getString(c.getColumnIndexOrThrow(Contacts.People.PRIMARY_PHONE_ID));
                        String number = c.getString(c.getColumnIndexOrThrow(Contacts.People.NUMBER));
                        nameEt.setText(name);
                        mobilenumberEt.setText(number);
                        Toast.makeText(this,  ""+name, Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }


    public void contactsDailog(){
        final Dialog dialog = new Dialog(CreateCustomerActivity.this);
        dialog.setContentView(R.layout.custom_contactsdailog);
        ListView lv =dialog.findViewById(R.id.mylistview);
        EditText search = dialog.findViewById(R.id.searchEt_contatcs);
        adap_contacts = new CustomAdapter_contacts(CreateCustomerActivity.this, ContactsLoad.contactslist);
        lv.setAdapter(adap_contacts);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adap_contacts.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactsBean ob = (ContactsBean) adap_contacts.getItem(position);

                nameEt.setText(ob.getName());

                if(ob.getNumber().contains("92") || ob.getNumber().contains("03")) {
                    mobilenumberEt.setText(ob.getNumber());
                }
                else {
                    Toast.makeText(CreateCustomerActivity.this, "please select pakistan based country code number.", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();

            }
        });

        dialog.show();
    }

//    private void getContactList() {
//        ContentResolver cr = getContentResolver();
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
//                null, null, null, null);
//
//        if ((cur != null ? cur.getCount() : 0) > 0) {
//            while (cur != null && cur.moveToNext()) {
//                String id = cur.getString(
//                        cur.getColumnIndex(ContactsContract.Contacts._ID));
//                String name = cur.getString(cur.getColumnIndex(
//                        ContactsContract.Contacts.DISPLAY_NAME));
//
//                if (cur.getInt(cur.getColumnIndex(
//                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
//                    Cursor pCur = cr.query(
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                            new String[]{id}, null);
//                    while (pCur.moveToNext()) {
//                        String phoneNo = pCur.getString(pCur.getColumnIndex(
//                                ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        contactslist.add(new ContactsBean(name,phoneNo));
//                        Log.i("sadasdsad", "Name: " + name);
//                        Log.i("sadasdsad", "Phone Number: " + phoneNo);
//                    }
//                    pCur.close();
//                }
//            }
//        }
//        if(cur!=null){
//            cur.close();
//        }
//    }

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
                Intent i = new Intent(CreateCustomerActivity.this, ApplockActivity.class);
                i.putExtra("wheree","other");
                ff=0;
                startActivity(i);
            }

        }

    }

}
