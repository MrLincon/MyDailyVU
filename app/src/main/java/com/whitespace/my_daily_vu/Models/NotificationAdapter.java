package com.whitespace.my_daily_vu.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.whitespace.my_daily_vu.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NotificationAdapter extends FirestoreRecyclerAdapter<Notification, NotificationAdapter.NotificationHolder> {


    private Context mContext;

    public NotificationAdapter(@NonNull FirestoreRecyclerOptions<Notification> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NotificationAdapter.NotificationHolder holder, int position, @NonNull final Notification model) {
        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getDesc());
        holder.date.setText(model.getDate());
        holder.order.setText(model.getOrder());
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card,
                parent, false);
        return new NotificationAdapter.NotificationHolder(view);
    }

    class NotificationHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView date;
        private TextView desc;
        private TextView order;


        public NotificationHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notf_title);
            date = itemView.findViewById(R.id.notf_date);
            order = itemView.findViewById(R.id.notf_order);
            desc = itemView.findViewById(R.id.notf_desc);

            mContext = itemView.getContext();

        }
    }
}
