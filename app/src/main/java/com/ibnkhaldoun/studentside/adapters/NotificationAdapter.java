package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.activities.MarkDetailActivity;
import com.ibnkhaldoun.studentside.enums.PostTypes;
import com.ibnkhaldoun.studentside.models.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private Context mContext;
    private List<Notification> mNotificationList;

    public NotificationAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.notification_list_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification notification = mNotificationList.get(position);
        Log.i("Notification", "onBindViewHolder: " + notification.isSeen());
        if (!notification.isSeen())
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.seen_notification));
        holder.mShortTextView.setText(Utilities.getProfessorShortName(notification.getProfessor()));
        GradientDrawable circle = (GradientDrawable) holder.mShortTextView.getBackground();
        circle.setColor(Utilities.getCircleColor(mContext));
        holder.mDateTextView.setText(Utilities.getDateFormat(notification.getDate()));
        holder.mTextTextView.setText(notification.createText(mContext));
        holder.mTypeTextView.setText(PostTypes.getType(notification.getType()));
    }


    @Override
    public int getItemCount() {
        if (mNotificationList != null) return mNotificationList.size();
        return 0;
    }

    public void swapList(List<Notification> notificationList) {
        this.mNotificationList = notificationList;
        notifyDataSetChanged();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView mShortTextView, mTextTextView, mDateTextView, mTypeTextView;


        public NotificationViewHolder(View itemView) {
            super(itemView);
            mShortTextView = itemView.findViewById(R.id.notification_short_text);
            mTextTextView = itemView.findViewById(R.id.notification_text);
            mTypeTextView = itemView.findViewById(R.id.notification_type);
            mDateTextView = itemView.findViewById(R.id.notification_date);

            itemView.setOnClickListener(v -> {
                switch (mNotificationList.get(getAdapterPosition()).getType()) {
                    case PostTypes.AVIS_TYPE:
                        //mContext.startActivity(new Intent(mContext,));
                        break;
                    case PostTypes.MARK_TYPE:
                        //mContext.startActivity(new Intent(mContext, MarkDetailActivity.class));
                        break;
                    case PostTypes.CONSULTATION_TYPE:
                        //mContext.startActivity(new Intent(mContext, MarkDetailActivity.class));
                        break;
                }
            });
        }
    }
}
