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

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.room.Room;

import com.digiaccounts.digiaccountz.Activities.MainActivity;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.MyDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class RemiderBroadcastForDelete extends BroadcastReceiver {

    MyDatabase database;
    @Override
    public void onReceive(Context k1, Intent k2) {
        // TODO Auto-generated method stub

        database = Room.databaseBuilder(k1,MyDatabase.class,"mydatabasee").allowMainThreadQueries().build();
        String reminderid = k2.getStringExtra("reminderid");

        database.RemiderManageTable().deleteRemindersbyid(Long.parseLong(reminderid));

    }
}