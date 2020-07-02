package com.whitespace.mydailyvu.Add_Class;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.whitespace.mydailyvu.R;

public class Add_Class_Sunday extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbarTitle;

    private EditText semester, section, subject, subjectCode, teacher1, teacher2, room, roomCode;

    private CheckBox checkboxClass, checkboxLab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__class);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);

        semester = findViewById(R.id.semester);
        section = findViewById(R.id.section);
        subject = findViewById(R.id.subject);
        subjectCode = findViewById(R.id.subjectCode);
        teacher1 = findViewById(R.id.teacher1);
        teacher2 = findViewById(R.id.teacher2);
        room = findViewById(R.id.room);
        roomCode = findViewById(R.id.roomCode);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("Sunday");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        checkboxClass = findViewById(R.id.checkboxClass);
        checkboxLab = findViewById(R.id.checkboxLab);

        checkboxSettings();

    }

    private void checkboxSettings() {
        checkboxClass.setChecked(true);
        teacher1.setEnabled(true);
        teacher2.setEnabled(false);

        checkboxClass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkboxClass.isChecked()) {
                    teacher1.setEnabled(true);
                    teacher2.setEnabled(false);
                    checkboxLab.setChecked(false);
                }
            }
        });

        checkboxLab.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkboxLab.isChecked()) {
                    teacher1.setEnabled(true);
                    teacher2.setEnabled(true);
                    checkboxClass.setChecked(false);
                }
            }
        });
    }
}
