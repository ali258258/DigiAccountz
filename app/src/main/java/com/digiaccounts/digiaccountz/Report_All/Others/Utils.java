package com.digiaccounts.digiaccountz.Report_All.Others;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.Report_All.Model.Customer_Report_Model;
import com.digiaccounts.digiaccountz.Report_All.Model.FullBusiness_Report_Model;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by HP on 4/1/2017.
 */

public class Utils {
    Context context;


    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    public static String final_Selected_path="";

    public Utils(Context context) {
        this.context = context;
        editor = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE).edit();
        prefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
    }


    //checking network connectivity
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    //getting date
    public String getDate() {
        DateFormat df = new SimpleDateFormat("d-M-yyyy");
        Date dateobj = new Date();
        Log.d("date", df.format(dateobj));
        return df.format(dateobj);
    }

    //getting time
    public String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
        return sdf.format(cal.getTime());
    }

    //Hiding keyboard

    public void hideSoftKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public void showToastFromResource(int message) {
        Toast.makeText(context, context.getResources().getString(message), Toast.LENGTH_SHORT).show();
    }

    public String getStringFromResource(int id) {
        return context.getResources().getString(id);
    }

    public int getIntegerFromResource(int id) {
        return context.getResources().getInteger(id);
    }


    public void SwitchActivity(Class java) {
        Intent intent = new Intent(context, java);
        context.startActivity(intent);
    }


    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        c.drawRect(0, 0, v.getWidth(), v.getHeight(), p);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return getResizedBitmap(b,1200,2010);
    }



    public void setCurrentCustomerReport(Customer_Report_Model geoNameList) {
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(geoNameList,
                new TypeToken<Customer_Report_Model>() {
                }.getType());

        if (!element.isJsonObject()) {
        } else {
            JsonObject jsonArray = element.getAsJsonObject();
            editor.putString(getStringFromResource(R.string.current_customer_report), jsonArray.toString()).commit();
        }
    }


    public Customer_Report_Model getCurrentCustomerReport() {
        Customer_Report_Model playersList = null;
        String myfvtlist = prefs.getString(getStringFromResource(R.string.current_customer_report), "");
        if (!myfvtlist.equals("")) {
            playersList = (Customer_Report_Model) fromJson(myfvtlist,
                    new TypeToken<Customer_Report_Model>() {
                    }.getType());
        }


        return playersList;
    }



    public void setCurrentFullBusinessReport(FullBusiness_Report_Model geoNameList) {
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(geoNameList,
                new TypeToken<FullBusiness_Report_Model>() {
                }.getType());

        if (!element.isJsonObject()) {
        } else {
            JsonObject jsonArray = element.getAsJsonObject();
            editor.putString(getStringFromResource(R.string.currnt_full_business_report), jsonArray.toString()).commit();
        }
    }


    public FullBusiness_Report_Model getCurrentCustomerFullBusinessReport() {
        FullBusiness_Report_Model playersList = null;
        String myfvtlist = prefs.getString(getStringFromResource(R.string.currnt_full_business_report), "");
        if (!myfvtlist.equals("")) {
            playersList = (FullBusiness_Report_Model) fromJson(myfvtlist,
                    new TypeToken<FullBusiness_Report_Model>() {
                    }.getType());
        }


        return playersList;
    }



    public String afterTextChanged(String view) {
        String s = null;
        try {
            // The comma in the format specifier does the trick
            s = String.format("%,d", Long.parseLong(view.toString()));
        } catch (NumberFormatException e) {
        }

        return s;
        // Set s back to the view after temporarily removing the text change listener
    }





    public static Object fromJson(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }


}
