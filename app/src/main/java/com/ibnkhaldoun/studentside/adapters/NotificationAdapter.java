package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.models.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private Context mContext;
    private List<Notification> mNotificationList;

    public NotificationAdapter(Context mContext, List<Notification> mDataList) {
        this.mContext = mContext;
        this.mNotificationList = mDataList;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.notification_list_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification notification = mNotificationList.get(position);
        holder.mShortTextView.setText(notification.getProfessor().getShortName());
        GradientDrawable circle = (GradientDrawable) holder.mShortTextView.getBackground();
        circle.setColor(Utilities.getCircleColor(notification.getProfessor().getShortName().charAt(0), mContext));
        holder.mTextTextView.setText(notification.getText());
        switch (notification.getType()) {
            case AVIS:
                holder.mImageImageView.setImageResource(R.drawable.ic_mail_outline);
                break;
            case MARKS:
                holder.mImageImageView.setImageResource(R.drawable.ic_lock_outline);
                break;
            case CONSULTATION:
                holder.mImageImageView.setImageResource(R.drawable.ic_calendar_new);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mNotificationList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView mShortTextView, mTextTextView;
        ImageView mImageImageView;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            mShortTextView = itemView.findViewById(R.id.notification_short_text);
            mTextTextView = itemView.findViewById(R.id.notification_text);
            mImageImageView = itemView.findViewById(R.id.notification_image);
        }
    }
}
