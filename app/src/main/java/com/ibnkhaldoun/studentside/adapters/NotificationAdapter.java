package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ibnkhaldoun.studentside.models.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private Context mContext;
    private List<Notification> mDataList;

    public NotificationAdapter(Context mContext, List<Notification> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        public NotificationViewHolder(View itemView) {
            super(itemView);
        }
    }
}
