package com.digiaccounts.digiaccountz.Report_All.Others;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.Report_All.Others.Utils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class InformationDialog extends Dialog implements
        View.OnClickListener {

    Context c;
    File file;
    Button btn_dismiss;
    TextView tvfname, tvresolution, tvsize,
            tvtype, tvlastmodified, tvpath;

    com.digiaccounts.digiaccountz.Report_All.Others.Utils Utils;
    TextView resolutionheading;


    public InformationDialog(Context a, File file) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.file = file;
        Utils = new Utils(c);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.informationdialog);

        Window window = getWindow();
        window.getDecorView().setBackgroundColor(0);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        btn_dismiss = findViewById(R.id.btn_no);
        btn_dismiss.setOnClickListener(this);

        //declaring textviews
        tvfname = findViewById(R.id.tvfname);
        tvresolution = findViewById(R.id.tvresolution);
        tvsize = findViewById(R.id.tvsize);
        resolutionheading = findViewById(R.id.resolutionheading);
        tvtype = findViewById(R.id.tvtype);
        tvlastmodified = findViewById(R.id.tvlastmodified);
        tvpath = findViewById(R.id.tvpath);
        //Setting Data
        SetAllData();


    }

    private void SetAllData() {
        tvfname.setText("" + file.getName());
        tvsize.setText(GetSize(file));
        tvresolution.setText(GetResolution(file));
        tvtype.setText(GetType());
        tvlastmodified.setText(GetMyLastModified(file));
        tvpath.setText(file.getAbsolutePath());
    }

    private String GetType() {
        try {
            String extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
            return extension;
        } catch (Exception e) {
            return "Unknown";
        }
    }

    private String GetResolution(File file) {
        resolutionheading.setText("Pages");
        return 1 + "";
    }

    private String GetSize(File file) {
        try {
            DecimalFormat df2 = new DecimalFormat("#.##");
            double LengthInByte = file.length();
            double LengthInKB = LengthInByte / 1024;
            double LengthInMB = LengthInKB / 1024;
            if (LengthInMB > 1)
                return df2.format(LengthInMB) + " mb";
            else if (LengthInKB > 1)
                return df2.format(LengthInKB) + " kb";
            else
                return df2.format(LengthInByte) + " bytes";
        } catch (Exception e) {
            return "Unknown";
        }


    }

    private String GetMyLastModified(File file) {
        String mylastmodified = "" + new Date(file.lastModified()).toString();

        try {
            List<String> list = new ArrayList<String>(Arrays.asList(mylastmodified.split(" ")));
            list.remove(3);
            list.remove(3);
            mylastmodified = "" + list.get(0)
                    + " " + list.get(1) + " " + list.get(2) + " " + list.get(3);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mylastmodified;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

}