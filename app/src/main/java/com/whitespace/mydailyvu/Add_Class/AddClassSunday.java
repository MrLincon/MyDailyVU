package com.whitespace.mydailyvu.Add_Class;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.whitespace.mydailyvu.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddClassSunday extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbarTitle;

    LinearLayout btnStartTime, btnEndTime;
    TextView startTime, endTime;

    private EditText semester, section, group, subject, subjectCode, teacher1, teacher2, room, roomCode;

    private CheckBox checkboxClass, checkboxLab;

    FloatingActionButton fabSunday;

    private int CalendarHour, CalendarMinute;
    String format, am_pm;
    Calendar calendar;
    TimePickerDialog timepickerdialog;
    int orderInHour, orderInMinute;

    private FirebaseAuth mAuth;
    private String userID;
    private DocumentReference document_ref;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__class);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);

        btnStartTime = findViewById(R.id.btnStartTime);
        btnEndTime = findViewById(R.id.btnEndTime);
        startTime = findViewById(R.id.tvStartTime);
        endTime = findViewById(R.id.tvEndTime);

        semester = findViewById(R.id.semester);
        section = findViewById(R.id.section);
        group = findViewById(R.id.group);
        subject = findViewById(R.id.subject);
        subjectCode = findViewById(R.id.subjectCode);
        teacher1 = findViewById(R.id.teacher1);
        teacher2 = findViewById(R.id.teacher2);
        room = findViewById(R.id.room);
        roomCode = findViewById(R.id.roomCode);

        checkboxClass = findViewById(R.id.checkboxClass);
        checkboxLab = findViewById(R.id.checkboxLab);

        fabSunday = findViewById(R.id.fabSunday);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("Sunday");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getUid();
        document_ref = db.collection("Routine2").document();


        checkboxSettings();

        getTime();


        fabSunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRoutine();
            }
        });


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

    private void getTime() {

        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);

                timepickerdialog = new TimePickerDialog(AddClassSunday.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                orderInHour = hourOfDay;
                                orderInMinute = minute;
                                if (hourOfDay == 0) {
                                    hourOfDay = hourOfDay + 12;
                                    format = "AM";
                                    am_pm = "AM";
                                } else if (hourOfDay == 12) {
                                    format = "PM";
                                    am_pm = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay = hourOfDay - 12;
                                    format = "PM";
                                    am_pm = "PM";
                                } else {
                                    format = "AM";
                                    am_pm = "AM";
                                }
                                startTime.setText(String.format("%02d:%02d", hourOfDay, minute) + " " + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();
            }
        });


        btnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);

                timepickerdialog = new TimePickerDialog(AddClassSunday.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if (hourOfDay == 0) {
                                    hourOfDay = hourOfDay + 12;
                                    format = "AM";
                                } else if (hourOfDay == 12) {
                                    format = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay = hourOfDay - 12;
                                    format = "PM";
                                } else {
                                    format = "AM";
                                }
                                endTime.setText(String.format("%02d:%02d", hourOfDay, minute) + " " + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();
            }
        });
    }

    private void getRoutine() {

        String StartTime = startTime.getText().toString();
        String EndTime = endTime.getText().toString();
        String Day = toolbarTitle.getText().toString();
        String Semester = semester.getText().toString().trim();
        String Section = section.getText().toString().toUpperCase().trim();
        String Group = group.getText().toString().toUpperCase().trim();
        String Subject = subject.getText().toString().toUpperCase().trim();
        String SubjectCode = subjectCode.getText().toString().trim();
        String Teacher1 = teacher1.getText().toString().trim();
        String Teacher2 = teacher2.getText().toString().trim();
        String Room = room.getText().toString().trim();
        String RoomCode = roomCode.getText().toString().trim();

        int OrderHour = orderInHour;
        int OrderMinute = orderInMinute;
        String OrderAmPm = am_pm;

        final String id = document_ref.getId();
        Map<String, Object> userMap = new HashMap<>();

        userMap.put("startTime", StartTime);
        userMap.put("endTime", EndTime);
        userMap.put("day", Day);
        userMap.put("semester", Semester);
        userMap.put("section", Section);
        userMap.put("group", Group);
        userMap.put("subject", Subject);
        userMap.put("subjectCode", SubjectCode);
        userMap.put("teacher1", Teacher1);
        userMap.put("teacher2", Teacher2);
        userMap.put("room", Room);
        userMap.put("roomCode", RoomCode);
        userMap.put("orderHour", OrderHour);
        userMap.put("orderMinute", OrderMinute);
        userMap.put("am_pm", OrderAmPm);
        userMap.put("userId", userID);
        userMap.put("id", id);
        userMap.put("timestamp", FieldValue.serverTimestamp());

        document_ref.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddClassSunday.this, "Class Added", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddClassSunday.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
//        finish();
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }



}
