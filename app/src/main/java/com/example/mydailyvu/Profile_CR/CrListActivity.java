package com.example.mydailyvu.Profile_CR;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mydailyvu.Models.CR;
import com.example.mydailyvu.Models.CrRecyclerDecoration;
import com.example.mydailyvu.Models.ProfileAdapter_CR;
import com.example.mydailyvu.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CrListActivity extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    LottieAnimationView cr_list;

    CardView copy_email,copy_contact;

    private FirebaseAuth mAuth;
    private String userID;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userCollection = db.collection("CR_CSE");

    private ProfileAdapter_CR adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cr_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("CR");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        copy_email = findViewById(R.id.copy_email);
        copy_contact = findViewById(R.id.copy_contact);
        cr_list = findViewById(R.id.anim_cr_list);

        cr_list.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.recyclerviewCr);
        int padding = getResources().getDimensionPixelSize(R.dimen.CrListPadding);
        recyclerView.addItemDecoration(new CrRecyclerDecoration(padding));
//        progressBar = findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getUid();
//
        Query query = userCollection.orderBy("semester", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<CR> options = new FirestoreRecyclerOptions.Builder<CR>()
                .setQuery(query,CR.class)
                .build();

        adapter = new ProfileAdapter_CR(options);

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
