package com.example.mydailyvu.Models;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydailyvu.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class ProfileAdapter_Teacher extends FirestoreRecyclerAdapter<Teacher, ProfileAdapter_Teacher.ProfileHolder_Teacher> {

    ClipboardManager clipboardManagerT;
    ClipData clipDataT;

    private Context mContextT;

    public ProfileAdapter_Teacher(@NonNull FirestoreRecyclerOptions<Teacher> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProfileHolder_Teacher holder, int position, @NonNull final Teacher model) {
        Picasso.get().load(model.getImageUrl()).error(R.drawable.user_default).into(holder.imageView);
        holder.name.setText(model.getName());
        holder.dept.setText(model.getDept());
        holder.desg.setText(model.getDesg());
        holder.code.setText(model.getCode());
        holder.room.setText(model.getRoom());
        holder.email.setText(model.getEmail());
        holder.contact.setText(model.getContact());

        clipboardManagerT = (ClipboardManager) mContextT.getSystemService(Context.CLIPBOARD_SERVICE);

        holder.copy_emailT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = model.getEmail();

                clipDataT = ClipData.newPlainText("email",email);
                clipboardManagerT.setPrimaryClip(clipDataT);

                Toast.makeText(mContextT,"copied: "+email, Toast.LENGTH_SHORT).show();
            }
        });

        holder.copy_contactT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String contact = model.getContact();
                clipDataT = ClipData.newPlainText("contact",contact);
                clipboardManagerT.setPrimaryClip(clipDataT);

                Toast.makeText(mContextT,"copied: "+contact, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public ProfileHolder_Teacher onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_list_cardview,
                parent, false);
        return new ProfileHolder_Teacher(view);
    }

    class ProfileHolder_Teacher extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView dept;
        private TextView desg;
        private TextView code;
        private TextView room;
        private TextView email;
        private TextView contact;
        private ImageView imageView;

        private CardView copy_emailT,copy_contactT;

        public ProfileHolder_Teacher(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.teacher_list_name);
            dept = itemView.findViewById(R.id.teacher_list_department);
            desg = itemView.findViewById(R.id.teacher_list_dsgn);
            code = itemView.findViewById(R.id.teacher_list_code);
            room = itemView.findViewById(R.id.teacher_list_room);
            email = itemView.findViewById(R.id.teacher_list_email);
            contact = itemView.findViewById(R.id.teacher_list_contact);
            imageView = itemView.findViewById(R.id.TeacherImageView_list);

            copy_emailT = itemView.findViewById(R.id.copy_emailT);
            copy_contactT = itemView.findViewById(R.id.copy_contactT);

            mContextT = itemView.getContext();

        }
    }
}