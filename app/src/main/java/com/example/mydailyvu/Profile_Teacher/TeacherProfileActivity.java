package com.example.mydailyvu.Profile_Teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydailyvu.Activity.RoutineActivity;
import com.example.mydailyvu.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class TeacherProfileActivity extends AppCompatActivity {

    Toolbar toolbar;

    private TextView nameTeacher;
    private TextView departmentTeacher;
    private TextView designationTeacher;
    private TextView codeTeacher;
    private TextView roomTeacher;
    private TextView contactTeacher;
    private TextView emailTeacher;
    private CircularImageView circularImageView_teacher;

    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private String userID;

    private FirebaseFirestore db;
    private DocumentReference document_referenceT;

    private static final String KEY_T_NAME = "name";
    private static final String KEY_T_DEPARTMENT = "dept";
    private static final String KEY_T_DESIGNATION = "desg";
    private static final String KEY_T_CODE = "code";
    private static final String KEY_T_ROOM = "room";
    private static final String KEY_T_CONTACT = "contact";
    private static final String KEY_T_EMAIL = "email";
    private static final String KEY_T_IMAGE = "imageUrl";

    public static final String EXTRA_T_NAME = "com.example.firebaseprofile.EXTRA_T_NAME";
    public static final String EXTRA_T_DEPARTMENT = "com.example.firebaseprofile.EXTRA_T_DEPARTMENT";
    public static final String EXTRA_T_DESIGNATION = "com.example.firebaseprofile.EXTRA__T_DESIGNATION";
    public static final String EXTRA_T_CODE = "com.example.firebaseprofile.EXTRA_T_CODE";
    public static final String EXTRA_T_ROOM = "com.example.firebaseprofile.EXTRA_T_ROOM";
    public static final String EXTRA_T_CONTACT = "com.example.firebaseprofile.EXTRA_T_CONTACT";
    public static final String EXTRA_T_EMAIL = "com.example.firebaseprofile.EXTRA_T_EMAIL";
    public static final String EXTRA_T_IMAGE = "com.example.firebaseprofile.EXTRA_T_IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getUid();

        nameTeacher = findViewById(R.id.tv_teacher_name);
        departmentTeacher = findViewById(R.id.tv_teacher_department);
        designationTeacher = findViewById(R.id.tv_teacher_designation);
        codeTeacher = findViewById(R.id.tv_teacher_tc);
        roomTeacher = findViewById(R.id.tv_teacher_room);
        emailTeacher = findViewById(R.id.tv_teacher_email);
        contactTeacher = findViewById(R.id.tv_teacher_contact);

        circularImageView_teacher = findViewById(R.id.circularImageView_teacher);

        progressBar = findViewById(R.id.progress_loadingT);
        progressBar.setVisibility(View.GONE);

        db = FirebaseFirestore.getInstance();
        document_referenceT = db.collection("UsersT").document(userID);

        loadData();

    }

    public void loadData(){

        document_referenceT.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    progressBar.setVisibility(View.VISIBLE);

                    String nameT = documentSnapshot.getString(KEY_T_NAME);
                    String departmentT = documentSnapshot.getString(KEY_T_DEPARTMENT);
                    String designationT = documentSnapshot.getString(KEY_T_DESIGNATION);
                    String codeT = documentSnapshot.getString(KEY_T_CODE);
                    String roomT = documentSnapshot.getString(KEY_T_ROOM);
                    String emailT = documentSnapshot.getString(KEY_T_EMAIL);
                    String contactT = documentSnapshot.getString(KEY_T_CONTACT);
                    String url = documentSnapshot.getString(KEY_T_IMAGE);

                    Picasso.get().load(url).error(R.drawable.user_default).into(circularImageView_teacher);
                    nameTeacher.setText(nameT);
                    departmentTeacher.setText(departmentT);
                    designationTeacher.setText(designationT);
                    codeTeacher.setText(codeT);
                    roomTeacher.setText(roomT);
                    emailTeacher.setText(emailT);
                    contactTeacher.setText(contactT);

                } else {
                    Toast.makeText(TeacherProfileActivity.this, "Information does not exist!", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Picasso.get().load(R.drawable.user_default).into(circularImageView_teacher);
            }
        });

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_teacher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_teacher:

                String name_teacher = nameTeacher.getText().toString().trim();
                String department_teacher = departmentTeacher.getText().toString().trim();
                String designation_teacher = designationTeacher.getText().toString().trim();
                String code_teacher = codeTeacher.getText().toString().trim();
                String room_teacher = roomTeacher.getText().toString().trim();
                String email_teacher = emailTeacher.getText().toString().trim();
                String contact_teacher = contactTeacher.getText().toString().trim();

                Intent edit = new Intent(TeacherProfileActivity.this, EditTeacherProfileActivity.class);
                edit.putExtra(EXTRA_T_NAME, name_teacher);
                edit.putExtra(EXTRA_T_DEPARTMENT, department_teacher);
                edit.putExtra(EXTRA_T_DESIGNATION, designation_teacher);
                edit.putExtra(EXTRA_T_CODE, code_teacher);
                edit.putExtra(EXTRA_T_ROOM, room_teacher);
                edit.putExtra(EXTRA_T_EMAIL, email_teacher);
                edit.putExtra(EXTRA_T_CONTACT, contact_teacher);

                startActivity(edit);
                return true;
            case R.id.delete_profile_teacher:
                openDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TeacherProfileActivity.this);
        builder.setTitle("Are you sure?")
                .setMessage("If you delete this profile, it  will no longer be available in CR's list!")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        document_referenceT.delete();
                        Picasso.get().load(R.drawable.user_default).into(circularImageView_teacher);
                        nameTeacher.setText("");
                        departmentTeacher.setText("");
                        designationTeacher.setText("");
                        codeTeacher.setText("");
                        roomTeacher.setText("");
                        emailTeacher.setText("");
                        contactTeacher.setText("");

                        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TeacherProfileActivity.this, RoutineActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
