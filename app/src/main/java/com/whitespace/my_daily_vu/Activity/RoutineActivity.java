package com.whitespace.my_daily_vu.Activity;


import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.whitespace.my_daily_vu.Routine_Settings.RoutineSettingsActivity;
import com.whitespace.my_daily_vu.Routine.ViewPagerAdapter;
import com.whitespace.my_daily_vu.ThemeSettings;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.whitespace.my_daily_vu.R;

import java.util.Calendar;

public class RoutineActivity extends AppCompatActivity {


    TextView department, details;

    LinearLayout changeBtn;

    //    private static final String PREF_NAME = "pref_name";
    private static final String PREF_TEACHERS_NAME = "pref_teacherName";
    private static final String PREF_SEMESTER = "pref_semester";
    private static final String PREF_SEC = "pref_sec";
    private static final String PREF_ROUTINE_TYPE = "pref_routineType";
    private static final String PREF_DEPT = "pref_dept";

    private AlertDialog.Builder alertdialogbuilder;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID;

    private TextView toolbarTitle;

    ThemeSettings themeSettings;
    private Switch darkModeSwitch;

    Dialog popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Theme Settings
        themeSettings = new ThemeSettings(this);
        if (themeSettings.loadNightModeState() == false) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        //...............
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        tabLayout = findViewById(R.id.tabLayoutID);
        viewPager = findViewById(R.id.viewPager);
        department = findViewById(R.id.departmentTextview);
        details = findViewById(R.id.detailsTextview);

        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getUid();

        user = mAuth.getCurrentUser();

        popup = new Dialog(this);

        changeBtn = findViewById(R.id.changeBtn);

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_profile = new Intent(RoutineActivity.this, RoutineSettingsActivity.class);
                startActivity(intent_profile);
            }
        });

//        For Action Bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("Routine");

//        For Tab Layout
        tabLayout.setupWithViewPager(viewPager);


//        For Navigation Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView = findViewById(R.id.navigation_view_left);

        Menu menu = navigationView.getMenu();

//        if (savedInstanceState == null) {
//            navigationView.setCheckedItem(R.id.routine);
//        }

//        For Calling Navigation Item Class

        NavigationItems();


        ViewPagerAdapter viewPagerAdapter_ = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter_);
        viewPager.setCurrentItem(0, true);
        CurrentDay();

        MenuItem menuItem = navigationView.getMenu().findItem(R.id.dark_mode_toggle); // This is the menu item that contains your switch
        Switch drawerSwitch = (Switch) menuItem.getActionView().findViewById(R.id.darkModeSwitch);
        drawerSwitch.setClickable(false);
        if (themeSettings.loadNightModeState() == false) {
            drawerSwitch.setChecked(false);
        } else {
            drawerSwitch.setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.routine_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        navigationView.setCheckedItem(R.id.dark_mode_toggle);
//        navigationView.getMenu().performIdentifierAction(R.id.dark_mode_toggle, 0);

        switch (item.getItemId()) {
            case R.id.notification:
                Intent notification = new Intent(RoutineActivity.this, NotificationActivity.class);
                startActivity(notification);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 1000);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void AlertDialog() {
        alertdialogbuilder = new AlertDialog.Builder(RoutineActivity.this, R.style.AlertDialog);
        alertdialogbuilder.setCancelable(false);
        alertdialogbuilder.setMessage("Do you want to exit?");
        alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(RoutineActivity.this, "Exit", Toast.LENGTH_SHORT).show();
                RoutineActivity.this.finish();
            }
        });

        alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(RoutineActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        alertdialogbuilder.show();
    }

    public void CurrentDay() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (Calendar.SUNDAY == dayOfWeek) {
            viewPager.setCurrentItem(0, true);
        } else if (Calendar.MONDAY == dayOfWeek) {
            viewPager.setCurrentItem(1, true);
        } else if (Calendar.TUESDAY == dayOfWeek) {
            viewPager.setCurrentItem(2, true);
        } else if (Calendar.WEDNESDAY == dayOfWeek) {
            viewPager.setCurrentItem(3, true);
        } else if (Calendar.THURSDAY == dayOfWeek) {
            viewPager.setCurrentItem(4, true);
        }
    }

    public void NavigationItems() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (menuItem.getItemId()) {
                            case R.id.routine:
                                break;
                            case R.id.vu_book:
                                Toast.makeText(RoutineActivity.this, "This feature is under development! :D", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.change_routine:
                                Intent changeRoutine = new Intent(RoutineActivity.this, RoutineSettingsActivity.class);
                                startActivity(changeRoutine);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                break;
                            case R.id.dark_mode_toggle:
                                if (themeSettings.loadNightModeState() == false) {
                                    themeSettings.setNightModeState(true);
                                    restartApp();   //Recreate activity
                                } else {
                                    themeSettings.setNightModeState(false);
                                    restartApp();   //Recreate activity
                                }
                                break;
                            case R.id.feedback:
                                Intent feedback = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "mydaily.vu@gmail.com"));

                                try {
                                    startActivity(Intent.createChooser(feedback, "Choose an e-mail client"));
                                } catch (android.content.ActivityNotFoundException e) {
                                    Toast.makeText(RoutineActivity.this, "There is no e-mail clint installed!", Toast.LENGTH_SHORT).show();

                                }
                                break;
                            case R.id.share:
                                popup.setContentView(R.layout.popup_share);
                                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                LinearLayout copy = popup.findViewById(R.id.copy);
                                copy.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String link = "https://play.google.com/store/apps/details?id=com.whitespace.my_daily_vu";
                                        ClipboardManager clipboard = (ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                                        ClipData clip = ClipData.newPlainText("", link);
                                        clipboard.setPrimaryClip(clip);
                                        Toast.makeText(RoutineActivity.this, "Link copied to clipboard", Toast.LENGTH_SHORT).show();
                                        popup.dismiss();
                                    }
                                });
                                ImageView close = popup.findViewById(R.id.close_popup);
                                close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        popup.dismiss();
                                    }
                                });
                                popup.show();
                                break;
                            case R.id.about:
                                Intent about_intent = new Intent(RoutineActivity.this, AboutActivity.class);
                                startActivity(about_intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                break;
                            case R.id.rate:
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW,
                                            Uri.parse("market://details?id=" + getPackageName())));
                                } catch (ActivityNotFoundException e) {
                                    startActivity(new Intent(Intent.ACTION_VIEW,
                                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                                }
                                break;
                        }
                    }
                }, 300);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    //Recreate activity

    private void restartApp() {
        Intent i = new Intent(RoutineActivity.this, RoutineActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //...............

    private void loadPopUp() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        department.setText(sharedPreferences.getString(PREF_DEPT, ""));

        String RoutineType = sharedPreferences.getString(PREF_ROUTINE_TYPE, "");
        String Department = sharedPreferences.getString(PREF_DEPT, "");
        String Semester = sharedPreferences.getString(PREF_SEMESTER, "");
        String Section = sharedPreferences.getString(PREF_SEC, "");
        String TeachersName = sharedPreferences.getString(PREF_TEACHERS_NAME, "");

        if (RoutineType == "" || Department == "") {
            popup.setContentView(R.layout.popup_select_routine);
            popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            LinearLayout select_routine = popup.findViewById(R.id.select_routine);
            select_routine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_profile = new Intent(RoutineActivity.this, RoutineSettingsActivity.class);
                    startActivity(intent_profile);
                }
            });
            ImageView close = popup.findViewById(R.id.close_popup);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.dismiss();
                }
            });
            popup.show();
        }
        if (RoutineType.equals("Teacher") && TeachersName == "") {
            popup.setContentView(R.layout.popup_select_routine);
            popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            LinearLayout select_routine = popup.findViewById(R.id.select_routine);
            select_routine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_profile = new Intent(RoutineActivity.this, RoutineSettingsActivity.class);
                    startActivity(intent_profile);
                }
            });
            ImageView close = popup.findViewById(R.id.close_popup);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.dismiss();
                }
            });
            popup.show();
        }
        if (RoutineType.equals("Student") && (Semester == "" || Section == "")) {
            popup.setContentView(R.layout.popup_select_routine);
            LinearLayout select_routine = popup.findViewById(R.id.select_routine);
            select_routine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_profile = new Intent(RoutineActivity.this, RoutineSettingsActivity.class);
                    startActivity(intent_profile);
                }
            });
            ImageView close = popup.findViewById(R.id.close_popup);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.dismiss();
                }
            });
            popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popup.show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (firstStart) {
            Intent intent = new Intent(RoutineActivity.this, OnboardingActivity.class);
            startActivity(intent);
            finish();
        }

        navigationView.setCheckedItem(R.id.routine);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        department.setText(sharedPreferences.getString(PREF_DEPT, ""));

        String RoutineType = sharedPreferences.getString(PREF_ROUTINE_TYPE, "");

        switch (RoutineType) {
            case "Teacher":
                details.setText("Routine: " + sharedPreferences.getString(PREF_TEACHERS_NAME, ""));
                break;
            case "Student":
                details.setText("Routine: " + sharedPreferences.getString(PREF_SEMESTER, "") +
                        " - " + sharedPreferences.getString(PREF_SEC, ""));
                break;
        }

        loadPopUp();
    }
}
