package com.digiaccounts.digiaccountz.Activities.busineses;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.Activities.ApplockActivity;
import com.digiaccounts.digiaccountz.Activities.MainActivity;
import com.digiaccounts.digiaccountz.Activities.callbacks.CreateCustomerCallbacks;
import com.digiaccounts.digiaccountz.Activities.callbacks.CreateTransactionCallback;
import com.digiaccounts.digiaccountz.Activities.customers.Contacts.ContactsBean;
import com.digiaccounts.digiaccountz.Activities.customers.Contacts.ContactsLoad;
import com.digiaccounts.digiaccountz.Activities.customers.Contacts.CustomAdapter_contacts;
import com.digiaccounts.digiaccountz.Activities.customers.CreateCustomerActivity;
import com.digiaccounts.digiaccountz.Activities.customers.CustomerListActivity;
import com.digiaccounts.digiaccountz.Activities.reminders.RemiderBroadcast;
import com.digiaccounts.digiaccountz.Activities.reminders.UpComingRemindersActivity;
import com.digiaccounts.digiaccountz.Activities.signups.SingupWithEmail_Activity;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.tables.business.BusinessTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.customer.CustomerTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.reminders.ReminderTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.transactions.TransactionTable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TransactionAdd_RealActivity extends AppCompatActivity {


    int ff =0;
    int PRIVATE_MODE = 0;
    String PREF_NAME = "digiaccountz";


        String passingsystem="";
    TimePicker myTimePicker;
    TimePickerDialog timePickerDialog;

    Calendar calendar = Calendar.getInstance();
    String timestring;

    public long transid=0;

    String imagebase64 = "";

    static CreateTransactionCallback listener;
    static CreateTransactionCallback listenerforhome;

    String businessid="";
    String customerid="";
    String statuss="";
    String nametitle;
    String type;

    ImageView cashCh,supplisrCh,onlineCh;
    TextView cashTv, suppliesTv,onlineTv;

    EditText dateEt;

    Configuration config;
    Locale locale;

    TextView cnhTv;
    ImageView backbtn;

    DatePickerDialog.OnDateSetListener date,date2;
    final Calendar myCalendar = Calendar.getInstance();

    EditText amountEt,descriptionEt,reminderEt,imageEt;
    String catagory="supplies";
    Button saveBtn;

    LinearLayout imagelv,reminderlv;
    BusinessTable b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_add__real);
        cnhTv = findViewById(R.id.cnhTv);
        imagelv = findViewById(R.id.imagelinear);
        reminderlv = findViewById(R.id.reminderlinear);
        customerid= getIntent().getStringExtra("customeridd");
        businessid= getIntent().getStringExtra("businessidd");
        statuss= getIntent().getStringExtra("checkingstatus");
        nametitle = getIntent().getStringExtra("customername");
        type = getIntent().getStringExtra("customertype");

         b =MainActivity.database.businessManageTable().loadWithID(Long.parseLong(businessid));
        if(b==null){
            Log.i("sasas","null");
        }
        else {
            Log.i("sasas","have");
        }
        cnhTv.setText(nametitle);
        amountEt = findViewById(R.id.amountEt_inAT);
        descriptionEt = findViewById(R.id.descEt_inAT);
        reminderEt = findViewById(R.id.reminderEt_inAT);
        dateEt = findViewById(R.id.dateEt_inAT);
        imageEt = findViewById(R.id.imageEt_inAT);
        saveBtn = findViewById(R.id.saveBtn);
        backbtn = findViewById(R.id.backbtn);

        dateEt.setText(giveDate());
        dateEt.setFocusable(false);
        dateEt.setKeyListener(null);
        reminderEt.setFocusable(false);
        reminderEt.setKeyListener(null);
        imageEt.setFocusable(false);
        imageEt.setKeyListener(null);

        cashCh = findViewById(R.id.cashCB_inAT);
        supplisrCh = findViewById(R.id.suppliesCB_inAT);
        onlineCh = findViewById(R.id.bankonlineCB_inAT);

        cashTv = findViewById(R.id.cashCBTvv_inAT);
        suppliesTv = findViewById(R.id.suppliesTv_inAT);
        onlineTv = findViewById(R.id.bankonlineTv_inAT);
        ff=0;

        supplisrCh.setImageResource(R.color.screenbackgroundsBlue);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerListActivity.ff=0;
                finish();
            }
        });



        cashTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supplisrCh.setImageResource(R.drawable.lineborder);
                onlineCh.setImageResource(R.drawable.lineborder);
                cashCh.setImageResource(R.color.screenbackgroundsBlue);
                catagory = "Cash";

                imagelv.setVisibility(View.GONE);
                reminderlv.setVisibility(View.GONE);

            }
        });

        suppliesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cashCh.setImageResource(R.drawable.lineborder);
                onlineCh.setImageResource(R.drawable.lineborder);
                supplisrCh.setImageResource(R.color.screenbackgroundsBlue);
                catagory = "Supplies";

                imagelv.setVisibility(View.VISIBLE);
                reminderlv.setVisibility(View.VISIBLE);



            }
        });

        onlineTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cashCh.setImageResource(R.drawable.lineborder);
                supplisrCh.setImageResource(R.drawable.lineborder);
                onlineCh.setImageResource(R.color.screenbackgroundsBlue);
                catagory = "Bank/Online";

                imagelv.setVisibility(View.GONE);
                reminderlv.setVisibility(View.GONE);

            }
        });


        cashCh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supplisrCh.setImageResource(R.drawable.lineborder);
                onlineCh.setImageResource(R.drawable.lineborder);
                cashCh.setImageResource(R.color.screenbackgroundsBlue);
                catagory = "Cash";

                imagelv.setVisibility(View.GONE);
                reminderlv.setVisibility(View.GONE);



            }
        });

        supplisrCh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cashCh.setImageResource(R.drawable.lineborder);
                onlineCh.setImageResource(R.drawable.lineborder);
                supplisrCh.setImageResource(R.color.screenbackgroundsBlue);
                catagory = "Supplies";

                imagelv.setVisibility(View.VISIBLE);
                reminderlv.setVisibility(View.VISIBLE);


            }
        });

        onlineCh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cashCh.setImageResource(R.drawable.lineborder);
                supplisrCh.setImageResource(R.drawable.lineborder);
                onlineCh.setImageResource(R.color.screenbackgroundsBlue);
                catagory = "Bank/Online";

                imagelv.setVisibility(View.GONE);
                reminderlv.setVisibility(View.GONE);

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

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }
        };
        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel2();
            }
        };

//        dateEt.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                new DatePickerDialog(TransactionAdd_RealActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                return false;
//            }
//        });


        imageEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ff=0;
                imageselectionDailog();
            }
        });

        dateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DatePickerDialog pckkk =  new DatePickerDialog(TransactionAdd_RealActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                pckkk.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                pckkk.show();
            }
        });

        reminderEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar newCalender = Calendar.getInstance();
                final DatePickerDialog dialog = new DatePickerDialog(TransactionAdd_RealActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                        final Calendar newDate = Calendar.getInstance();
                        Calendar newTime = Calendar.getInstance();
                        TimePickerDialog time = new TimePickerDialog(TransactionAdd_RealActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                newDate.set(year,month,dayOfMonth,hourOfDay,minute,0);
                                Calendar tem = Calendar.getInstance();
                                Log.w("TIME",System.currentTimeMillis()+"");
                                if(newDate.getTimeInMillis()-tem.getTimeInMillis()>0)
                                {
                                    passingsystem = newDate.getTime().toString();
                                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                    formatter.format(newDate.getTime());
                                    reminderEt.setText(""+formatter.format(newDate.getTime()));

                                    SimpleDateFormat mSDF = new SimpleDateFormat("hh:mm a");
                                    String time = mSDF.format(newDate.getTime());
                                    timestring = time;


                                }
                                else
                                    Toast.makeText(TransactionAdd_RealActivity.this,"Invalid time",Toast.LENGTH_SHORT).show();

                            }
                        },newTime.get(Calendar.HOUR_OF_DAY),newTime.get(Calendar.MINUTE),false);
                        time.show();

                    }
                },newCalender.get(Calendar.YEAR),newCalender.get(Calendar.MONTH),newCalender.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();
//                DatePickerDialog pckkk =  new DatePickerDialog(TransactionAdd_RealActivity.this, date2, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH));
//                pckkk.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//                pckkk.show();
            }
        });





       // Toast.makeText(getApplicationContext(),Locale.getDefault().getDisplayLanguage(), Toast.LENGTH_LONG).show();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!amountEt.getText().toString().equalsIgnoreCase("")
                && !dateEt.getText().toString().equalsIgnoreCase(""))
                {

                    if (!catagory.equalsIgnoreCase("")) {
                        try
                        {
                        CustomerListActivity.ff=0;
                        TransactionTable ob = new TransactionTable();
                        ob.setAmount(amountEt.getText().toString());
                        ob.setDate(dateEt.getText().toString());
                        ob.setBusinessid(Long.parseLong(businessid));
                        ob.setCustomerid(Long.parseLong(customerid));
                        ob.setStatus(statuss);
                        ob.setTimee(giveTime());
                        ob.setTransactiontype(catagory);
                        ob.setDescription(descriptionEt.getText().toString());
                        ob.setReminder(reminderEt.getText().toString());
                        ob.setImage(imagebase64);
                        transid = MainActivity.database.transactionManageTable().AddTransaction(ob);

                        CustomerTable t = MainActivity.database.customerManageTable().loadCustomerusingID(Long.parseLong(customerid));
                        int c_balance = Integer.parseInt(t.getCustomer_balance());
                        int c_youwillget = Integer.parseInt(t.getYouwillget_amount()); //recieved
                        int c_youwillgive = Integer.parseInt(t.getYouwillgive_amount()); // sent
                        int amt = Integer.parseInt(amountEt.getText().toString());


                        BusinessTable b = MainActivity.database.businessManageTable().loadWithID(Long.parseLong(businessid));
                        int b_balance = Integer.parseInt(b.getTotalamount());
                        int b_youwillget = Integer.parseInt(b.getTotalrecieved()); //recieved
                        int b_youwillgive = Integer.parseInt(b.getTotalgiven()); // sent

                        if (statuss.equalsIgnoreCase("Sent")) {
                            c_balance = c_balance - amt;
                            c_youwillgive = c_youwillgive + amt;

                            b_balance = b_balance - amt;
                            b_youwillgive = b_youwillgive + amt;
                        }
                        else if (statuss.equalsIgnoreCase("Recieved")) {
                            c_balance = c_balance + amt;
                            c_youwillget = c_youwillget + amt;

                            b_balance = b_balance + amt;
                            b_youwillget = b_youwillget + amt;
                        }

                        int dd = MainActivity.database.customerManageTable().UpdateAmountValues(Long.parseLong(customerid)
                                , Integer.toString(c_balance)
                                , Integer.toString(c_youwillget)
                                , Integer.toString(c_youwillgive));

                        int bb = MainActivity.database.businessManageTable().UpdateAmountValuesBusiness(Long.parseLong(businessid)
                                , Integer.toString(b_balance)
                                , Integer.toString(b_youwillget)
                                , Integer.toString(b_youwillgive));

                        listener.Callon(c_balance, c_youwillgive, c_youwillget);
                        listenerforhome.Callon(c_balance, c_youwillgive, c_youwillget);


                        if (!reminderEt.getText().toString().equalsIgnoreCase(""))
                        {

                            ReminderTable objj = new ReminderTable();
                            objj.setCustomerid(Long.parseLong(customerid));
                            objj.setBusinessid(Long.parseLong(businessid));
                            objj.setDate(reminderEt.getText().toString());
                            objj.setStatus("Active");
                            objj.setCustomername(t.getFullname());
                            objj.setTransactionid(transid);
                            objj.setTime(timestring);
                            long id =MainActivity.database.RemiderManageTable().AddReminder(objj);
                            Log.i("dderereID",""+id);
                            addReminderNew(calendar,amountEt.getText().toString(),nametitle,dateEt.getText().toString(),Long.parseLong(customerid),id,businessid,b.getBusinessname());

                        }
                        else {
                            finish();
                        }

                    }
                    catch(Exception e){
                        Log.i("hhgg77gg",e.getMessage());
                        Log.i("hhgg77gg",e.getMessage());
                    }

                    }
                    else {
                        Toast.makeText(TransactionAdd_RealActivity.this, "please select transaction type", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(TransactionAdd_RealActivity.this, "please provide mendatory details.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateEt.setText(sdf.format(myCalendar.getTime()));
    }
   private void updateLabel2() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        //reminderEt.setText(sdf.format(myCalendar.getTime()));
        openTimePickerDialog(false);

    }

    public static void setListenerCallback(CreateTransactionCallback callbacks){
        listener = callbacks;
    }
    public static void setListenerCallbackforhome(CreateTransactionCallback callbacks){
        listenerforhome = callbacks;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        Drawable d = new BitmapDrawable(getResources(),  Bitmap.createScaledBitmap(bitmap, 50, 50, true));
                        imageEt.setCompoundDrawablesWithIntrinsicBounds(null,null,d,null);
                        imageEt.setText("Image Attached");
                        imageEt.setEnabled(false);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                       // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
                        byte[] b = baos.toByteArray();

                        imagebase64 = Base64.encodeToString(b, Base64.DEFAULT);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 10:
                if(resultCode == RESULT_OK){
                    try {
                     //   Uri extras = data.getData();

                        Bitmap bitmap = android.provider.MediaStore.Images.Media.getBitmap(getContentResolver(), currentPhotoURi);
                        //  Bitmap bitmap = (Bitmap) extras.get("data");
                      //  Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), ex);
                        Drawable d = new BitmapDrawable(getResources(),  Bitmap.createScaledBitmap(bitmap, 50, 50, true));
                        imageEt.setCompoundDrawablesWithIntrinsicBounds(null,null,d,null);
                        imageEt.setText("Image Attached");
                        imageEt.setEnabled(false);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                      //  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
                        byte[] b = baos.toByteArray();

                        imagebase64 = Base64.encodeToString(b, Base64.DEFAULT);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i("assd3wd","err: "+e.getMessage());
                        Toast.makeText(TransactionAdd_RealActivity.this, "> "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


    private void openTimePickerDialog(boolean is24r) {

        timePickerDialog = new TimePickerDialog(TransactionAdd_RealActivity.this,
                onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), is24r);
        timePickerDialog.setTitle("Set Alarm Time");

        timePickerDialog.show();

    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
//            Calendar calSet = (Calendar) calNow.clone();

            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            if (calendar.compareTo(calNow) <= 0) {
                // Today Set time passed, count to tomorrow
                calNow.add(Calendar.DATE, 1);
            }

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter.format(calendar.getTime());
            reminderEt.setText(""+formatter.format(calendar.getTime()));

            SimpleDateFormat mSDF = new SimpleDateFormat("hh:mm a");
            String time = mSDF.format(calendar.getTime());
            timestring = time;
          //  Toast.makeText(TransactionAdd_RealActivity.this, ":"+timestring, Toast.LENGTH_SHORT).show();
        }
    };


    private void setAlarm(Calendar targetCal,String amount, String name, String date,long customerid,int reminderid) {
      //  reminderEt.setText(""+targetCal.getTime());


//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        formatter.format(targetCal.getTime());
//        reminderEt.setText(""+formatter.format(targetCal.getTime()));
        CustomerTable c = MainActivity.database.customerManageTable().loadCustomerusingID(customerid);

        Intent intent = new Intent(TransactionAdd_RealActivity.this, RemiderBroadcast.class);
        intent.putExtra("amttt",amount);
        intent.putExtra("namee",name);
        intent.putExtra("status",c.getCatagory());
        intent.putExtra("datee",date);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(TransactionAdd_RealActivity.this, reminderid, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Toast.makeText(this, "sddsd"+targetCal.getTimeInMillis(), Toast.LENGTH_SHORT).show();
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
        finish();

    }


    public String giveDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(cal.getTime());
    }

    public String giveTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(cal.getTime());
    }


    public void imageselectionDailog(){
        final Dialog dialog = new Dialog(TransactionAdd_RealActivity.this);
        dialog.setContentView(R.layout.custom_imageselectiondailog);
        Button camera =dialog.findViewById(R.id.cameraaBtn);
        Button gallery = dialog.findViewById(R.id.galleryBtn);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri photoURI = FileProvider.getUriForFile(TransactionAdd_RealActivity.this, "com.example.android.fileprovider", createImageFile());
                    currentPhotoURi = photoURI;
                    takePicture.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePicture, 10);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(TransactionAdd_RealActivity.this, "->"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
                dialog.dismiss();
            }
        });





        dialog.show();
    }




    public void addReminderNew(Calendar targetCal, final String amount, final String name, final String date, long customerid, final long reminderid,String businessiid,String bsunessname){

        Log.i("sdsdsdsd","->"+reminderid);

        final CustomerTable c = MainActivity.database.customerManageTable().loadCustomerusingID(customerid);


                Date remind = new Date(passingsystem.trim());
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
                calendar.setTime(remind);
                calendar.set(Calendar.SECOND,0);
                Intent intent = new Intent(TransactionAdd_RealActivity.this,RemiderBroadcast.class);
                intent.putExtra("amttt",amount);
                intent.putExtra("namee",name);
                intent.putExtra("status",c.getCatagory());
                intent.putExtra("datee",date);
                intent.putExtra("reminderid",""+reminderid);
                intent.putExtra("businessidd",""+businessiid);
                intent.putExtra("businessname",""+bsunessname);
                PendingIntent intent1 = PendingIntent.getBroadcast(TransactionAdd_RealActivity.this,(int)reminderid,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        if(Build.VERSION.SDK_INT < 23){
            if(Build.VERSION.SDK_INT >= 19){
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),intent1);
            }
            else{
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),intent1);
            }
        }
        else{
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),intent1);
        }


                Toast.makeText(TransactionAdd_RealActivity.this,"Inserted Successfully",Toast.LENGTH_SHORT).show();
                finish();

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
                Intent i = new Intent(TransactionAdd_RealActivity.this, ApplockActivity.class);
                i.putExtra("wheree","other");
                ff=0;
                startActivity(i);
            }

        }

    }



    String currentPhotoPath;
    Uri currentPhotoURi;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CustomerListActivity.ff=0;
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
