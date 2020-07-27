package com.digiaccounts.digiaccountz.Activities.customers;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.Activities.MainActivity;
import com.digiaccounts.digiaccountz.Activities.ZoomableImagview;
import com.digiaccounts.digiaccountz.Activities.busineses.BusinessListActivity;
import com.digiaccounts.digiaccountz.Activities.busineses.TransactionAdd_RealActivity;
import com.digiaccounts.digiaccountz.Activities.busineses.ui.home.CustomerListBean;
import com.digiaccounts.digiaccountz.Activities.callbacks.CreateTransactionCallback;
import com.digiaccounts.digiaccountz.Activities.callbacks.ReCreateTransactionsCallbacks;
import com.digiaccounts.digiaccountz.Activities.reminders.RemiderBroadcast;
import com.digiaccounts.digiaccountz.R;
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

public class CustomAdapter_customerListing extends BaseAdapter {

    String timestring = "";
    String dateString = "";
    long transid= 0;

    DatePickerDialog.OnDateSetListener date2;
    TimePickerDialog.OnTimeSetListener onTimeSetListener ;
    final Calendar myCalendar = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();
    TimePickerDialog timePickerDialog;


    static ReCreateTransactionsCallbacks listener;
    static ReCreateTransactionsCallbacks listener2;

    Context context;
    private ArrayList<customerDetailsBean> list;
    private static LayoutInflater inflater=null;

    public CustomAdapter_customerListing(Context c, ArrayList<customerDetailsBean> l) {
        context = c;
        list=l;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
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
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.customitemforreports, null);

        TextView status = (TextView)vi.findViewById(R.id.statusTv); // title
        final TextView type = (TextView)vi.findViewById(R.id.typeTv); // artist name
        TextView date = (TextView)vi.findViewById(R.id.dateTv); // duration
        TextView desc = (TextView)vi.findViewById(R.id.descTv_); // duration
        final TextView amount = (TextView)vi.findViewById(R.id.amountTv); // duration
        ImageView overflow = (ImageView) vi.findViewById(R.id.overflowbtn);

        overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getType().equalsIgnoreCase("Supplies")) {
                    showPopupMenu(list.get(position).getImage(), list.get(position).getStatus(), list.get(position).getAmount(), list.get(position).getBusinessidd(), list.get(position).getCustomeridd(), list.get(position).getIdd(), v);
                }
                else {
                    showPopupMenuWithoutReminder(list.get(position).getImage(), list.get(position).getStatus(), list.get(position).getAmount(), list.get(position).getBusinessidd(), list.get(position).getCustomeridd(), list.get(position).getIdd(), v);
                }

            }
        });

        // Setting all values in listview
        desc.setText(list.get(position).getDescription());
        status.setText(list.get(position).getDate());
        type.setText(list.get(position).getType());
        date.setText(list.get(position).getReminder());
        amount.setText(bigDecimalData(list.get(position).getAmount()));
        status.setTextColor(list.get(position).getColor());
        amount.setTextColor(list.get(position).getColor());

        //        if (list.get(position).started_at.isEmpty() || list.get(position).started_at.equalsIgnoreCase("")){
//            //  reported.setText("Reported on: N/A");
//        }
//        else {
//            reported.setText(list.get(position).started_at);
//        }

        return vi;
    }


    private void showPopupMenuWithoutReminder(final String imgStr,final String status, final String amountEt, final long businessid, final long customerid, final long id, View view) {
        // inflate menu and attach it to a view onClick of which you want to display menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        //inflate menu items to popup menu
        inflater.inflate(R.menu.transactionmenu_without, popup.getMenu());
        //assign a cutom onClick Listener to it.
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getTitle().toString().equalsIgnoreCase("delete"))
                {
                    showDialog(status,amountEt,businessid,customerid,id);


                }
                else if (item.getItemId() == R.id.attachment_menu)
                {
                    if (!imgStr.equalsIgnoreCase("")) {

                        byte [] encodeByte=Base64.decode(imgStr.getBytes(),Base64.DEFAULT);
                        BitmapFactory.Options options=new BitmapFactory.Options();
                        options.inPurgeable = true;
                        Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length,options);
                        image = Bitmap.createScaledBitmap(image, 400, 400, false);

                        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.JPEG,100, baos);

                        //    byte [] b=baos.toByteArray();

                        //  byte[] decodedString = Base64.decode(imgStr, Base64.DEFAULT);
                        //   Bitmap decodedByte = BitmapFactory.decodeByteArray(b, 0, b.length);
                        imageShow_Dailog(context, image);
                    }
                    else {
                        Toast.makeText(context, "No Attachment for this transaction.", Toast.LENGTH_SHORT).show();
                    }

                }

                return false;
            }
        });
        //Show Popup.
        popup.show();
    }


    private void showPopupMenu(final String imgStr,final String status, final String amountEt, final long businessid, final long customerid, final long id, View view) {
        // inflate menu and attach it to a view onClick of which you want to display menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        //inflate menu items to popup menu
        inflater.inflate(R.menu.transactionmenu, popup.getMenu());
        //assign a cutom onClick Listener to it.
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getTitle().toString().equalsIgnoreCase("delete"))
                {
                    showDialog(status,amountEt,businessid,customerid,id);


                }
                else if (item.getItemId() == R.id.editreminder_menu)
                {
                    transid = id;
                    EditReminder(id);
                }
                else if (item.getItemId() == R.id.attachment_menu)
                {
                    if (!imgStr.equalsIgnoreCase("")) {

                        byte [] encodeByte=Base64.decode(imgStr.getBytes(),Base64.DEFAULT);
                        BitmapFactory.Options options=new BitmapFactory.Options();
                        options.inPurgeable = true;
                        Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length,options);
                        image = Bitmap.createScaledBitmap(image, 400, 400, false);

                        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.JPEG,100, baos);

                    //    byte [] b=baos.toByteArray();

                      //  byte[] decodedString = Base64.decode(imgStr, Base64.DEFAULT);
                     //   Bitmap decodedByte = BitmapFactory.decodeByteArray(b, 0, b.length);
                        imageShow_Dailog(context, image);
                    }
                    else {
                        Toast.makeText(context, "No Attachment for this transaction.", Toast.LENGTH_SHORT).show();
                    }

                }

                return false;
            }
        });
        //Show Popup.
        popup.show();
    }

    public static void setListenerCallback(ReCreateTransactionsCallbacks callbacks){
        listener = callbacks;
    }
    public static void setListenerCallback2(ReCreateTransactionsCallbacks callbacks){
        listener2 = callbacks;
    }

    public static String bigDecimalData(String data) {
        if (!TextUtils.isEmpty(data)) {
            BigDecimal bd = new BigDecimal(Double.parseDouble(data));
            DecimalFormat df = new DecimalFormat("#,###,###");
            return df.format(bd);
        }
        return "";
    }


    private void showDialog(final String status, final String amountEt, final long businessid, final long customerid, final long id) throws Resources.NotFoundException {
        new AlertDialog.Builder(context)
                .setTitle("Confirmation")
                .setMessage("Do you want to delete this transaction?")
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                    CustomerTable t =MainActivity.database.customerManageTable().loadCustomerusingID(customerid);
                    int c_balance = Integer.parseInt(t.getCustomer_balance());
                    int c_youwillget = Integer.parseInt(t.getYouwillget_amount()); //recieved
                    int c_youwillgive = Integer.parseInt(t.getYouwillgive_amount()); // sent
                    int amt = Integer.parseInt(amountEt);

                    BusinessTable b =MainActivity.database.businessManageTable().loadWithID(businessid);
                    int b_balance = Integer.parseInt(b.getTotalamount());
                    int b_youwillget = Integer.parseInt(b.getTotalrecieved()); //recieved
                    int b_youwillgive = Integer.parseInt(b.getTotalgiven()); // sent


                    if (status.equalsIgnoreCase("Sent")){
                      //  c_balance = c_balance-amt;
                        c_youwillgive= c_youwillgive-amt;
                     //   b_balance = b_balance-amt;
                        b_youwillgive= b_youwillgive-amt;
                    }
                    else if (status.equalsIgnoreCase("Recieved")){
                       // c_balance = c_balance-amt;
                        c_youwillget= c_youwillget-amt;

                       // b_balance = b_balance+amt;
                        b_youwillget= b_youwillget-amt;
                    }

                    int dd =MainActivity.database.customerManageTable().UpdateAmountValues(customerid
                            ,Long.toString(c_balance)
                            ,Integer.toString(c_youwillget)
                            ,Integer.toString(c_youwillgive));

                    int bb =MainActivity.database.businessManageTable().UpdateAmountValuesBusiness(businessid
                            ,Long.toString(b_balance)
                            ,Integer.toString(b_youwillget)
                            ,Integer.toString(b_youwillgive));

                    MainActivity.database.transactionManageTable().deleteTransactionById(id);
                    MainActivity.database.RemiderManageTable().deleteRemindersbyTransactionid(id);

                    long balancemy=0;

                    TransactionTable[] transactionlist=MainActivity.database.transactionManageTable().loadAllTransactionsByCustomerID(customerid);
                    if (transactionlist.length>0)
                    {
                        for (int i = 0; i<transactionlist.length ; i++) {
                            if (transactionlist[i].getStatus().equalsIgnoreCase("Sent")) {
                                balancemy = balancemy- Long.parseLong(transactionlist[i].getAmount());
                             }
                            else if (transactionlist[i].getStatus().equalsIgnoreCase("Recieved")){
                                balancemy = balancemy+Long.parseLong(transactionlist[i].getAmount());
                            }
                        }
                    }

                    int dds =MainActivity.database.customerManageTable().UpdateAmountValues(customerid
                            ,Long.toString(balancemy)
                            ,Integer.toString(c_youwillget)
                            ,Integer.toString(c_youwillgive));

                    int bbs =MainActivity.database.businessManageTable().UpdateAmountValuesBusiness(businessid
                            ,Long.toString(balancemy)
                            ,Integer.toString(b_youwillget)
                            ,Integer.toString(b_youwillgive));

                    listener.Changes(Long.toString(c_youwillget),Long.toString(c_youwillgive),Long.toString(balancemy));
                    listener2.Changes(Long.toString(c_youwillget),Long.toString(c_youwillgive),Long.toString(balancemy));
                    notifyDataSetChanged();


                            }
                        })
                .setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();
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


    private void setAlarm(Calendar targetCal,long transid) {

        TransactionTable tr = MainActivity.database.transactionManageTable().loadAllTransactionByTransactionID(transid);
        CustomerTable cr = MainActivity.database.customerManageTable().loadCustomerusingID(tr.getCustomerid());
        BusinessTable br = MainActivity.database.businessManageTable().loadWithID(tr.getBusinessid());
        ReminderTable rr = MainActivity.database.RemiderManageTable().loadAllRemindersbyTransactionID(transid);

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

        MainActivity.database.RemiderManageTable().UpdateRemiderDateTime(transid,dateString,timestring);

    }


    public void imageShow_Dailog(Context context, Bitmap bitmap){
        final Dialog dialog = new Dialog(context,R.style.WideDialog);
        dialog.setContentView(R.layout.imageshowdailog);
        ZoomableImagview Mv =dialog.findViewById(R.id.imgshow_main);

        Mv.setImageBitmap(bitmap);

        dialog.show();
    }



}


