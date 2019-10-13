package com.example.mydailyvu.Profile_Teacher;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydailyvu.Models.ProfileAdapter_Teacher;
import com.example.mydailyvu.Models.Teacher;
import com.example.mydailyvu.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class TeacherListActivity extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView recyclerView;
    ProgressBar progressBar;

    CardView copy_email,copy_contact;

    private FirebaseAuth mAuth;
    private String userID;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userCollection = db.collection("UsersT");

    private ProfileAdapter_Teacher adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Teacher");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        copy_email = findViewById(R.id.copy_email);
        copy_contact = findViewById(R.id.copy_contact);


//        copy_email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(CrListActivity.this, "E-mail", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        copy_contact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(CrListActivity.this, "Contact", Toast.LENGTH_SHORT).show();
//            }
//        });

        recyclerView = findViewById(R.id.recyclerviewTeacher);
//        progressBar = findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getUid();
//
        Query query = userCollection.orderBy("name", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Teacher> options = new FirestoreRecyclerOptions.Builder<Teacher>()
                .setQuery(query,Teacher.class)
                .build();

        adapter = new ProfileAdapter_Teacher(options);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
