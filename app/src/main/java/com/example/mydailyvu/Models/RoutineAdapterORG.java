package com.example.mydailyvu.Models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydailyvu.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RoutineAdapterORG extends FirestoreRecyclerAdapter<RoutineORG, RoutineAdapterORG.NoteHolder> {

    public RoutineAdapterORG(@NonNull FirestoreRecyclerOptions<RoutineORG> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull RoutineORG model) {
        holder.time.setText(model.getTime());
        holder.subject.setText(model.getSub());
        holder.teacher.setText(model.getTeacher());
        holder.routine.setText(model.getRoutine());
        holder.room.setText(model.getAp());

    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_layout,
                parent, false);
        return new NoteHolder(view);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView subject;
        TextView teacher;
        TextView routine;
        TextView room;

        public NoteHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            subject = itemView.findViewById(R.id.subject);
            teacher = itemView.findViewById(R.id.teacher);
            routine = itemView.findViewById(R.id.routine);
            room = itemView.findViewById(R.id.room);

        }
    }
}
