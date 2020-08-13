package com.digiaccounts.digiaccountz.Activities.reminders;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.Activities.MainActivity;
import com.digiaccounts.digiaccountz.Activities.callbacks.CreateTransactionCallback;
import com.digiaccounts.digiaccountz.Activities.callbacks.ReminderUpdateCallback;
import com.digiaccounts.digiaccountz.Activities.customers.customerDetailsBean;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.MyDatabase;
import com.digiaccounts.digiaccountz.roomdatabase.tables.business.BusinessTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.customer.CustomerTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.reminders.ReminderTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.transactions.TransactionTable;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CustomAdapter_UpcomingRemindersListing extends BaseAdapter {
   static ReminderUpdateCallback listener;
   static ReminderUpdateCallback listener2;

    Context context;
    private ReminderTable[] list;
    private static LayoutInflater inflater=null;


    DatePickerDialog.OnDateSetListener date2;
    TimePickerDialog.OnTimeSetListener onTimeSetListener ;
    final Calendar myCalendar = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();
    TimePickerDialog timePickerDialog;

    MyDatabase database;

    String timestring = "";
    String dateString = "";
    long transid= 0;

    String who ;


    public CustomAdapter_UpcomingRemindersListing(Context c, ReminderTable[] l,String whoo, MyDatabase data) {
        context = c;
        list=l;
        database = data;
        who = whoo;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat formatt = new SimpleDateFormat("dd/MM/yyyy");
                dateString = formatt.format(myCalendar.getTime());

                openTimePickerDialog(false);

            }
        };

        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

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

                SimpleDateFormat mSDF = new SimpleDateFormat("hh:mm a");
                String time = mSDF.format(calendar.getTime());
                timestring = time;
                setAlarm(calendar,transid);
            }
        };

    }


    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return list[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.customlayout_forreminders, null);

        TextView name = (TextView)vi.findViewById(R.id.nameEt_inR); // title
        TextView date = (TextView)vi.findViewById(R.id.dateEt_inR); // duration
        TextView time = (TextView)vi.findViewById(R.id.timeEt_inR); // duration
        ImageView overflow = (ImageView) vi.findViewById(R.id.overflowbtn);

        if (list[position].getDate().equalsIgnoreCase(giveDate())){
            name.setTextColor(Color.parseColor("#8BC34A"));
            date.setTextColor(Color.parseColor("#8BC34A"));
            time.setTextColor(Color.parseColor("#8BC34A"));

        }
        else {
            name.setTextColor(Color.parseColor("#0066ff"));
            date.setTextColor(Color.parseColor("#0066ff"));
            time.setTextColor(Color.parseColor("#0066ff"));
        }

        // Setting all values in listview
        name.setText(list[position].getCustomername());
        date.setText(list[position].getDate());
        time.setText(list[position].getTime());

//        if (who.equalsIgnoreCase("hide")){
//            overflow.setVisibility(View.GONE);
//        }



        overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(list[position].getId(),v);
            }
        });


        return vi;
    }

    public static String bigDecimalData(String data) {
        if (!TextUtils.isEmpty(data)) {
            BigDecimal bd = new BigDecimal(Double.parseDouble(data));
            DecimalFormat df = new DecimalFormat("#,###,###");
            return df.format(bd);
        }
        return "";
    }

    public String giveDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(cal.getTime());
    }


    private void setAlarm(Calendar targetCal,long transid) {

        ReminderTable rr = database.RemiderManageTable().loadAllRemindersbyTransactionID(transid);
        TransactionTable tr = database.transactionManageTable().loadAllTransactionByTransactionID(transid);
        CustomerTable cr = database.customerManageTable().loadCustomerusingID(tr.getCustomerid());
        BusinessTable br = database.businessManageTable().loadWithID(tr.getBusinessid());


        Date remind = new Date(targetCal.getTime().toString().trim());
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        calendar.setTime(remind);
        calendar.set(Calendar.SECOND,0);
        Intent intent = new Intent(context,RemiderBroadcast.class);
        intent.putExtra("amttt",""+tr.getAmount());
        intent.putExtra("namee",cr.getFullname());
        intent.putExtra("status",cr.getCatagory());
        intent.putExtra("datee",rr.getDate());
        intent.putExtra("reminderid",""+rr.getId());
        intent.putExtra("businessidd",""+br.getId());
        intent.putExtra("businessname",""+br.getBusinessname());
        PendingIntent intent1 = PendingIntent.getBroadcast(context,(int)rr.getId(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

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

        database.RemiderManageTable().UpdateRemiderDateTime(transid,dateString,timestring);

        if (listener!=null) {
            listener.Callon();
        }
        if (listener2!=null) {
            listener2.Callon();
        }
    }


    private void EditReminder(final long transactionid) {

        DatePickerDialog pckkk =  new DatePickerDialog(context, date2, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        pckkk.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        pckkk.show();

    }



    private void openTimePickerDialog(boolean is24r) {

        timePickerDialog = new TimePickerDialog(context,
                onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), is24r);
        timePickerDialog.setTitle("Set Alarm Time");

        timePickerDialog.show();

    }


    private void showPopupMenu( final long id, View view) {
        Log.i("7788jujnhg","::"+id);
        final ReminderTable rr = database.RemiderManageTable().loadAllRemindersbyID(id);
        // inflate menu and attach it to a view onClick of which you want to display menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        //inflate menu items to popup menu
        inflater.inflate(R.menu.remindermenu, popup.getMenu());
        //assign a cutom onClick Listener to it.
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.editreminder_menu)
                {
                    transid = rr.getTransactionid();
                    Log.i("7788jujnhg","trans::"+transid);
                    EditReminder(transid);
                }


                return false;
            }
        });
        //Show Popup.
        popup.show();
    }

    public static void setListenerCallback(ReminderUpdateCallback callbacks){
        listener = callbacks;
    }
    public static void setListenerCallback2(ReminderUpdateCallback callbacks){
        listener2 = callbacks;
    }

}
