package com.whitespace.my_daily_vu.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.whitespace.my_daily_vu.R;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DURATION = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, RoutineActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DURATION);

    }
}
