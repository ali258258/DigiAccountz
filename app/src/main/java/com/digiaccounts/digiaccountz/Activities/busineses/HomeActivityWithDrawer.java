package com.digiaccounts.digiaccountz.Activities.busineses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.Activities.ApplockActivity;
import com.digiaccounts.digiaccountz.Activities.ApplockHandling;
import com.digiaccounts.digiaccountz.Activities.LoginActivity;
import com.digiaccounts.digiaccountz.Activities.MainActivity;
import com.digiaccounts.digiaccountz.Activities.ProfileActivity;
import com.digiaccounts.digiaccountz.Activities.SettingsActivity;
import com.digiaccounts.digiaccountz.Activities.TermsandCondition_Activity;
import com.digiaccounts.digiaccountz.Activities.busineses.ui.home.HomeFragment;
import com.digiaccounts.digiaccountz.Activities.customers.Contacts.ContactsLoad;
import com.digiaccounts.digiaccountz.Activities.languagemanage.LanguageManager;
import com.digiaccounts.digiaccountz.Activities.reminders.UpComingRemindersActivity;
import com.digiaccounts.digiaccountz.Activities.signups.LanguageSelectionActivity;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.tables.loginmanage.LoginManageTable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;

public class HomeActivityWithDrawer extends AppCompatActivity {

    int PRIVATE_MODE = 0;
    String PREF_NAME = "digiaccountz";

    public static  int condition = 0;
    private AppBarConfiguration mAppBarConfiguration;
    MenuItem itemm;

  public static int ff = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_with_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ff=0;


     //   ApplockHandling.homescreen = 0;




//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_logout_me:
                        int u= MainActivity.database.loginManageTable().UpdateStatus("deactive", LoginActivity.id);
                       // Toast.makeText(HomeActivityWithDrawer.this, "::"+u, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomeActivityWithDrawer.this,LoginActivity.class));
                        finish();
                        return true;
                    case R.id.nav_homep:
                        drawer.closeDrawers();
                        startActivity(new Intent(HomeActivityWithDrawer.this, ProfileActivity.class));
                        return true;
                    case R.id.nav_slideshow:
                        drawer.closeDrawers();
                        startActivity(new Intent(HomeActivityWithDrawer.this, TermsandCondition_Activity.class));
                       // showPopupMenu(navigationView);
                        return true;
                    case R.id.nav_downloads:
                        drawer.closeDrawers();
                        openFolder();
                        // showPopupMenu(navigationView);
                        return true;
                    case R.id.nav_gallery:
                        drawer.closeDrawers();
                        Intent intent = new Intent(HomeActivityWithDrawer.this, UpComingRemindersActivity.class);
                        intent.putExtra("businessidd",""+HomeFragment.currentBusinessID);
                        intent.putExtra("businessname",""+ HomeFragment.currentBusinessName);
                        startActivity(intent);
                        return true;
//                    case R.id.nav_languageselection:
//                        Intent i = new Intent(HomeActivityWithDrawer.this, LanguageSelectionActivity.class);
//                        i.putExtra("zxcv","homee");
//                        startActivity(i);
//                        finish();
//                        return true;

                    default:
                        return false;
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_activity_with_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
     //    Toast.makeText(this, "::", Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(HomeActivityWithDrawer.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showPopupMenu( View view) {
        // inflate menu and attach it to a view onClick of which you want to display menu
        PopupMenu popup = new PopupMenu(HomeActivityWithDrawer.this, view);
        MenuInflater inflater = popup.getMenuInflater();
        //inflate menu items to popup menu
        inflater.inflate(R.menu.moremenu, popup.getMenu());
        //assign a cutom onClick Listener to it.
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


//                if (item.getItemId() == R.id.editreminder_menu)
//                {
//
//                }

                return false;
            }
        });
        //Show Popup.
        popup.show();
    }


    public void openFolder(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                +  File.separator + "DigiPDF" + File.separator);
        intent.setDataAndType(uri, "text/csv");
        startActivity(Intent.createChooser(intent, "Open folder"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ff == 0){
            ff++;
        }
        else {
                SharedPreferences sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
                final String stringObject = sharedPref.getString("applockstatus", "");
                if (stringObject.equalsIgnoreCase("enable")) {
                    Intent i = new Intent(HomeActivityWithDrawer.this, ApplockActivity.class);
                    i.putExtra("wheree", "other");
                    ff=0;
                    startActivity(i);
                }


        }

        }



}
