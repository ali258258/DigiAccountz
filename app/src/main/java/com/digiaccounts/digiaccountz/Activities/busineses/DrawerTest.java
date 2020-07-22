package com.digiaccounts.digiaccountz.Activities.busineses;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.digiaccounts.digiaccountz.R;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class DrawerTest extends AppCompatActivity {


    int first =1;

    LinearLayout container;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_test);
        getSupportActionBar().hide();
    //    container = findViewById(R.id.containermy);
        Toolbar toolbar = findViewById(R.id.toolbar);
        DuoDrawerLayout drawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
        DuoDrawerToggle drawerToggle = new DuoDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        drawerLayout.openDrawer();

        if (drawerLayout.isDrawerOpen()){
            drawerLayout.closeDrawer();
        }


      //  drawerLayout.closeDrawer();

//
//            RelativeLayout.LayoutParams layoutParams = new
//                    RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.MATCH_PARENT);
//            layoutParams.setMargins(0, 100, 0, 0);
//            container.setLayoutParams(layoutParams);
//
//


    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
