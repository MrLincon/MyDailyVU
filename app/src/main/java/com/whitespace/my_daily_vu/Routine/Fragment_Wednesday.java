package com.whitespace.my_daily_vu.Routine;


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
import com.whitespace.my_daily_vu.Models.Routine;
import com.whitespace.my_daily_vu.Models.RoutineAdapter;
import com.whitespace.my_daily_vu.R;
import com.whitespace.my_daily_vu.Models.RoutineRecyclerDecoration;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Fragment_Wednesday extends Fragment {

    View view;

    RecyclerView recyclerView;
    LottieAnimationView anim_wednesday;

    private static final String PREF_TEACHERS_NAME = "pref_teacherName";
    private static final String PREF_SEMESTER = "pref_semester";
    private static final String PREF_SEC = "pref_sec";
    private static final String PREF_ROUTINE_TYPE = "pref_routineType";
    private static final String PREF_DEPT = "pref_dept";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference routine = db.collection("Routine");
    private RoutineAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wednesday,container,false);

        anim_wednesday = view.findViewById(R.id.anim_wednesday);

        recyclerView = view.findViewById(R.id.recyclerView);
        int topPadding = getResources().getDimensionPixelSize(R.dimen.topPadding);
        int bottomPadding = getResources().getDimensionPixelSize(R.dimen.bottomPadding);
        int sidePadding = getResources().getDimensionPixelSize(R.dimen.sidePadding);
        recyclerView.addItemDecoration(new RoutineRecyclerDecoration(topPadding,sidePadding,bottomPadding));

        getRoutine();

        return view;
    }

    private void getRoutine() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        anim_wednesday.setVisibility(View.VISIBLE);

        String ROUTINE = sharedPreferences.getString(PREF_ROUTINE_TYPE, "");
        String DEPARTMENT = sharedPreferences.getString(PREF_DEPT, "");
        String SEMESTER = sharedPreferences.getString(PREF_SEMESTER, "");
        String SECTION = sharedPreferences.getString(PREF_SEC, "");
        String TEACHERS_NAME = sharedPreferences.getString(PREF_TEACHERS_NAME, "");

        String SEM = null;

        if (SEMESTER.equals("1st")){
            SEM = "1";
        }else if(SEMESTER.equals("2nd")){
            SEM = "2";
        }else if(SEMESTER.equals("3rd")){
            SEM = "3";
        }else if(SEMESTER.equals("4th")){
            SEM = "4";
        }else if(SEMESTER.equals("5th")){
            SEM = "5";
        }else if(SEMESTER.equals("6th")){
            SEM = "6";
        }else if(SEMESTER.equals("7th")){
            SEM = "7";
        }else if(SEMESTER.equals("8th")){
            SEM = "8";
        }else if(SEMESTER.equals("9th")){
            SEM = "9";
        }else if(SEMESTER.equals("10th")){
            SEM = "10";
        }else if(SEMESTER.equals("11th")){
            SEM = "11";
        }else if(SEMESTER.equals("12th")){
            SEM = "12";
        }

        if (ROUTINE.equals("Student")){
            Query query = routine.whereEqualTo("semester",SEM)
                    .whereEqualTo("section",SECTION)
                    .whereEqualTo("department",DEPARTMENT)
                    .whereEqualTo("day","Wednesday")
                    .orderBy("orderHour", Query.Direction.ASCENDING)
                    .orderBy("orderMinute", Query.Direction.ASCENDING);
            FirestoreRecyclerOptions<Routine> options = new FirestoreRecyclerOptions.Builder<Routine>()
                    .setQuery(query, Routine.class)
                    .build();

            adapter = new RoutineAdapter(options);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            anim_wednesday.setVisibility(View.GONE);
            adapter.startListening();
        } else if (ROUTINE.equals("Teacher")){
            Query query = routine.whereArrayContains("teachers",TEACHERS_NAME)
                    .whereEqualTo("day","Wednesday")
                    .whereEqualTo("department",DEPARTMENT)
                    .orderBy("orderHour", Query.Direction.ASCENDING)
                    .orderBy("orderMinute", Query.Direction.ASCENDING);
            FirestoreRecyclerOptions<Routine> options = new FirestoreRecyclerOptions.Builder<Routine>()
                    .setQuery(query, Routine.class)
                    .build();

            adapter = new RoutineAdapter(options);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            anim_wednesday.setVisibility(View.GONE);
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
