package com.digiaccounts.digiaccountz.Activities.reminders;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.digiaccounts.digiaccountz.Activities.MainActivity;
import com.digiaccounts.digiaccountz.Activities.busineses.TransactionAdd_RealActivity;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.tables.customer.CustomerTable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class RemiderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context k1, Intent k2) {
        // TODO Auto-generated method stub

        String amount = k2.getStringExtra("amttt");
        String name = k2.getStringExtra("namee");
        String date = k2.getStringExtra("datee");
        String status = k2.getStringExtra("status");
        String reminderid = k2.getStringExtra("reminderid");
        String businessid = k2.getStringExtra("businessidd");
        String businessname = k2.getStringExtra("businessname");

//        String textt = "";
//
//        if (status.equalsIgnoreCase("customer")) {
//            textt = "Today you have to take “Rs." + amount + "“ from " + name + " ,which was given on " + date;
//
//        } else {
//            textt = "Today you have to pay “Rs." + amount + "“ to " + name + " ,which was taken on " + date;
//        }


        Uri alarmsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        Intent intent1 = new Intent(k1, UpcomingActivityNotification.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.putExtra("businessidd",""+businessid);
        intent1.putExtra("businessname",""+businessname);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(k1);
        taskStackBuilder.addParentStack(UpcomingActivityNotification.class);
        taskStackBuilder.addNextIntent(intent1);

        PendingIntent intent2 = taskStackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(k1);

        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("my_channel_01", "hello", NotificationManager.IMPORTANCE_HIGH);
        }

        Notification notification = builder.setContentTitle("Reminder")
                .setContentText("You Have Pending Reminders.")
                .setAutoCancel(true)
                .setSound(alarmsound).setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(intent2)
                .setChannelId("my_channel_01")
                .build();

        NotificationManager notificationManager = (NotificationManager) k1.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(1, notification);

        addReminderForDelete(k1,Integer.parseInt(reminderid));

    }





    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show_Notification(Context context,String textt){

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent=new Intent(context,MainActivity.class);
        String CHANNEL_ID="MYCHANNEL";
        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"bhn", NotificationManager.IMPORTANCE_LOW);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,1,intent,0);
        Notification notification=new Notification.Builder(context,CHANNEL_ID)
                .setContentTitle("Reminder")
                .setContentText(textt)
                .setSound(soundUri)
                .setPriority(Notification.PRIORITY_MAX)
                .setStyle(new Notification.BigTextStyle().bigText(textt))
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.sym_action_chat,"Reminder",pendingIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_action_chat)
                .build();

        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(getRandomNumber(),notification);


    }

    public void show_NotificationLower(Context context,String text){

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =   new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.sym_action_chat) // notification icon
                .setContentTitle("Reminder!") // title for notification
                .setContentText(text)
                .setSound(soundUri)
                .setPriority(Notification.PRIORITY_MAX)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(text))
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(context, MainActivity.class);
        @SuppressLint("WrongConstant") PendingIntent pi = PendingIntent.getActivity(context,0,intent,Intent.FLAG_ACTIVITY_NEW_TASK);
        mBuilder.setContentIntent(pi);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(getRandomNumber(), mBuilder.build());


    }

    private int getRandomNumber() {
        return (new Random()).nextInt((1000 - 1) + 1) + 1;
    }


    public String getTimeafter8hours(){
         Calendar calendar = null;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
            final Date date = new Date();
            calendar = Calendar.getInstance();

            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, 5);

            Log.i("55tggbfcd",sdf.format(calendar.getTime()));
        }
        catch (Exception e){
e.printStackTrace();
        }
        return calendar.getTime().toString();
    }



    public void addReminderForDelete(Context context, final int reminderid){

        Date remind = new Date(getTimeafter8hours());
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        calendar.setTime(remind);
        calendar.set(Calendar.SECOND,0);
        Intent intent = new Intent(context,RemiderBroadcastForDelete.class);
        intent.putExtra("reminderid",""+reminderid);
        PendingIntent intent1 = PendingIntent.getBroadcast(context,(reminderid+8),intent,PendingIntent.FLAG_UPDATE_CURRENT);
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

    }



}