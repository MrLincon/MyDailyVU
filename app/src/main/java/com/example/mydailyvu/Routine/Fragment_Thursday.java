package com.example.mydailyvu.Routine;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mydailyvu.Models.Routine;
import com.example.mydailyvu.Models.RoutineAdapter;
import com.example.mydailyvu.R;
import com.example.mydailyvu.Models.RoutineRecyclerDecoration;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Fragment_Thursday extends Fragment {

    View view;

    RecyclerView recyclerView;
    LottieAnimationView anim_thursday;

    private static final String PREF_NAME = "pref_name";
    private static final String PREF_TEACHERS_NAME = "pref_teacherName";
    private static final String PREF_SEMESTER = "pref_semester";
    private static final String PREF_SEC = "pref_sec";
    private static final String PREF_ROUTINE_TYPE = "pref_routineType";
    private static final String PREF_DEPT = "pref_dept";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private RoutineAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thursday,container,false);

        anim_thursday = view.findViewById(R.id.anim_thursday);

        recyclerView = view.findViewById(R.id.recyclerView);
        int topPadding = getResources().getDimensionPixelSize(R.dimen.topPadding);
        int bottomPadding = getResources().getDimensionPixelSize(R.dimen.bottomPadding);
        int sidePadding = getResources().getDimensionPixelSize(R.dimen.sidePadding);
        recyclerView.addItemDecoration(new RoutineRecyclerDecoration(topPadding,sidePadding,bottomPadding));


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        getRoutine();

        return view;
    }

    private void getRoutine() {

        anim_thursday.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        String ROUTINE = sharedPreferences.getString(PREF_ROUTINE_TYPE, "");
        String DEPARTMENT = sharedPreferences.getString(PREF_DEPT, "");
        String SEMESTER = sharedPreferences.getString(PREF_SEMESTER, "");
        String SECTION = sharedPreferences.getString(PREF_SEC, "");

        if (ROUTINE.equals("Student") && DEPARTMENT.equals("CSE") && SEMESTER.equals("9th") && SECTION.equals("A")  ){
            CollectionReference thursday = db.collection("CSE").document("9A").collection("Thursday");
            Query query = thursday.orderBy("order", Query.Direction.ASCENDING);
            FirestoreRecyclerOptions<Routine> options = new FirestoreRecyclerOptions.Builder<Routine>()
                    .setQuery(query, Routine.class)
                    .build();

            adapter = new RoutineAdapter(options);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            anim_thursday.setVisibility(View.GONE);
            adapter.startListening();
        }else if (ROUTINE.equals("Student") && DEPARTMENT.equals("CSE") && SEMESTER.equals("9th") && SECTION.equals("B")  ){
            CollectionReference thursday = db.collection("CSE").document("9B").collection("Thursday");
            Query query = thursday.orderBy("order", Query.Direction.ASCENDING);
            FirestoreRecyclerOptions<Routine> options = new FirestoreRecyclerOptions.Builder<Routine>()
                    .setQuery(query, Routine.class)
                    .build();

            adapter = new RoutineAdapter(options);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            anim_thursday.setVisibility(View.GONE);
            adapter.startListening();
        }else if (ROUTINE.equals("Student") && DEPARTMENT.equals("CSE") && SEMESTER.equals("9th") && SECTION.equals("C")  ){
            CollectionReference thursday = db.collection("CSE").document("9C").collection("Thursday");
            Query query = thursday.orderBy("order", Query.Direction.ASCENDING);
            FirestoreRecyclerOptions<Routine> options = new FirestoreRecyclerOptions.Builder<Routine>()
                    .setQuery(query, Routine.class)
                    .build();

            adapter = new RoutineAdapter(options);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            anim_thursday.setVisibility(View.GONE);
            adapter.startListening();
        }else {
            try {
                adapter.stopListening();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getRoutine();
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            adapter.stopListening();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
