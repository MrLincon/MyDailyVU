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

public class ProfileAdapter_CR extends FirestoreRecyclerAdapter<CR, ProfileAdapter_CR.ProfileHolder_CR> {

    ClipboardManager clipboardManager;
    ClipData clipData;

    private Context mContext;

    public ProfileAdapter_CR(@NonNull FirestoreRecyclerOptions<CR> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProfileHolder_CR holder, int position, @NonNull final CR model) {
        Picasso.get().load(model.getImageUrl()).error(R.drawable.user_default).into(holder.imageView);
        holder.name.setText(model.getName());
        holder.section.setText(model.getSection());
        holder.email.setText(model.getEmail());
        holder.id.setText(model.getId());
        holder.semester.setText(model.getSemester());
        holder.contact.setText(model.getContact());

        clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);

        holder.copy_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = model.getEmail();

                clipData = ClipData.newPlainText("email",email);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(mContext,"copied: "+email, Toast.LENGTH_SHORT).show();
            }
        });

        holder.copy_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String contact = model.getContact();
                clipData = ClipData.newPlainText("contact",contact);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(mContext,"copied: "+contact, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public ProfileHolder_CR onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cr_list_cardview,
                parent, false);
        return new ProfileHolder_CR(view);
    }

    class ProfileHolder_CR extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView id;
        private TextView semester;
        private TextView section;
        private TextView contact;
        private TextView email;
        private ImageView imageView;

        private CardView copy_email,copy_contact;

        public ProfileHolder_CR(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_cr_name);
            id = itemView.findViewById(R.id.tv_cr_id);
            semester = itemView.findViewById(R.id.tv_cr_semester);
            section = itemView.findViewById(R.id.tv_cr_section);
            contact = itemView.findViewById(R.id.tv_cr_contact);
            email = itemView.findViewById(R.id.tv_cr_email);
            imageView = itemView.findViewById(R.id.circularImageView_list);

            copy_email = itemView.findViewById(R.id.copy_email);
            copy_contact = itemView.findViewById(R.id.copy_contact);

            mContext = itemView.getContext();

        }
    }

}
