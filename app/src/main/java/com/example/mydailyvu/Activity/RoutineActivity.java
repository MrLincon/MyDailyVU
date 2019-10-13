package com.example.mydailyvu.Activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.mydailyvu.AboutActivity;
import com.example.mydailyvu.Authentication.LoginActivity;
import com.example.mydailyvu.FeedbackActivity;
import com.example.mydailyvu.Profile_CR.CrListActivity;
import com.example.mydailyvu.Profile_CR.CrProfileActivity;
import com.example.mydailyvu.Profile_Teacher.TeacherListActivity;
import com.example.mydailyvu.R;
import com.example.mydailyvu.Routine_Settings.RoutineSettingsActivity;
import com.example.mydailyvu.Routine.ViewPagerAdapter;
import com.example.mydailyvu.Webview.CSEActivity;
import com.example.mydailyvu.Webview.NoticeBoardActivity;
import com.example.mydailyvu.Webview.StudentPanelActivity;
import com.example.mydailyvu.Webview.VUActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class RoutineActivity extends AppCompatActivity {


    TextView department, details, changeBtn;

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
    private String userID;

    private TextView toolbarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

//        if (savedInstanceState == null) {
//            navigationView.setCheckedItem(R.id.routine);
//        }

//        For Calling Navigation Item Class

        NavigationItems();


        ViewPagerAdapter viewPagerAdapter_ = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter_);
        viewPager.setCurrentItem(0, true);
        CurrentDay();


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RoutineActivity.this);
        String ROUTINE = sharedPreferences.getString(PREF_ROUTINE_TYPE, "");
        String DEPARTMENT = sharedPreferences.getString(PREF_DEPT, "");
        String SEMESTER = sharedPreferences.getString(PREF_SEMESTER, "");
        String SECTION = sharedPreferences.getString(PREF_SEC, "");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.routine_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.profileTeacher:
//                        Intent teacher_profile = new Intent(RoutineActivity.this, TeacherProfileActivity.class);
//                        startActivity(teacher_profile);
                break;
            case R.id.profileCr:
                Intent cr_profile = new Intent(RoutineActivity.this, CrProfileActivity.class);
                startActivity(cr_profile);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
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
                    doubleBackToExitPressedOnce=false;
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
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.routine:
//                        Intent intent_students = new Intent(RoutineActivity.this, RoutineActivity.class);
//                        startActivity(intent_students);
                        break;
                    case R.id.vu:
                        Intent intent_vu = new Intent(RoutineActivity.this, VUActivity.class);
                        startActivity(intent_vu);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.student_panel:
                        Intent intent_student_panel = new Intent(RoutineActivity.this, StudentPanelActivity.class);
                        startActivity(intent_student_panel);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.dept_cse:
                        Intent intent_dept_cse = new Intent(RoutineActivity.this, CSEActivity.class);
                        startActivity(intent_dept_cse);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.teachers_list:
                        Intent all_teacher = new Intent(RoutineActivity.this, TeacherListActivity.class);
                        startActivity(all_teacher);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.cr_list:
                        Intent all_cr = new Intent(RoutineActivity.this, CrListActivity.class);
                        startActivity(all_cr);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.notice_board:
                        Intent intent_notice_board = new Intent(RoutineActivity.this, NoticeBoardActivity.class);
                        startActivity(intent_notice_board);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.feedback:
                        Intent feedback_intent = new Intent(RoutineActivity.this, FeedbackActivity.class);
                        startActivity(feedback_intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.about:
                        Intent about_intent = new Intent(RoutineActivity.this, AboutActivity.class);
                        startActivity(about_intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.log_out:
                        FirebaseAuth.getInstance().signOut();
                        Intent log_in = new Intent(RoutineActivity.this, LoginActivity.class);
                        startActivity(log_in);
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

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

        String ROUTINE = sharedPreferences.getString(PREF_ROUTINE_TYPE, "");
        String DEPARTMENT = sharedPreferences.getString(PREF_DEPT, "");
        String SEMESTER = sharedPreferences.getString(PREF_SEMESTER, "");
        String SECTION = sharedPreferences.getString(PREF_SEC, "");
        if (ROUTINE.equals("Student")) {
            if (DEPARTMENT.equals("CSE")) {
                if (SEMESTER.equals("8th")) {
                    if (SECTION.equals("A")) {
                        Toast.makeText(RoutineActivity.this, "Working", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
