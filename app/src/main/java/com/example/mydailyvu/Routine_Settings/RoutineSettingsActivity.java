package com.example.mydailyvu.Routine_Settings;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mydailyvu.R;

public class RoutineSettingsActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_settings);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Routine Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (findViewById(R.id.fragContainer) != null) {
            if (savedInstanceState != null)
                return;
            getFragmentManager().beginTransaction().add(R.id.fragContainer, new RoutineSettingsFragment()).commit();
        }

    }
}
