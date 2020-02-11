package com.example.mydailyvu.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mydailyvu.R;
import com.example.mydailyvu.ThemeSettings;

public class TestActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbarTitle;

    ActionBarDrawerToggle actionBarDrawerToggle;
    Button switchModes;
    ThemeSettings themeSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Theme Settings
        themeSettings = new ThemeSettings(this);
        if(themeSettings.loadNightModeState()==false){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        //...............
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        switchModes = findViewById(R.id.switch_btn);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("Dark Mode");

        switchModes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(themeSettings.loadNightModeState()==false){
                    themeSettings.setNightModeState(true);
                    restartApp();   //Recreate activity
                } else {
                    themeSettings.setNightModeState(false);
                    restartApp();   //Recreate activity
                }
            }
        });


    }

    //Recreate activity

    private void restartApp() {
        Intent i = new Intent(TestActivity.this,TestActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    //...............
}

