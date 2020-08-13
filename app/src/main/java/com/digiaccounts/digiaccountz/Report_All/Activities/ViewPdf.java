package com.digiaccounts.digiaccountz.Report_All.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;

import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.Report_All.Others.Utils;

import java.io.File;

public class ViewPdf extends Activity {

    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        utils = new Utils(this);

        File f;


        finish();
        Intent intent = new Intent(Intent.ACTION_VIEW);


        f = new File(Utils.final_Selected_path);
        intent.setDataAndType(Uri.fromFile(f), "application/pdf");

        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);


    }


}