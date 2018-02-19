package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ibnkhaldoun.studentside.models.Mail;
import com.ibnkhaldoun.studentside.models.Notification;

import java.util.List;

public class MailAdapter extends RecyclerView.Adapter<MailAdapter.MailViewHolder> {

    private Context mContext;
    private List<Mail> mDataList;

    public MailAdapter(Context mContext, List<Mail> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }

    @Override
    public MailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MailViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class MailViewHolder extends RecyclerView.ViewHolder{

        public MailViewHolder(View itemView) {
            super(itemView);
        }
    }
}
