package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ibnkhaldoun.studentside.models.Display;
import com.ibnkhaldoun.studentside.models.Notification;

import java.util.List;

public class DisplaysAdapter extends RecyclerView.Adapter<DisplaysAdapter.DisplayViewHolder> {


    private Context mContext;
    private List<Display> mDataList;

    public DisplaysAdapter(Context mContext, List<Display> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }

    @Override
    public DisplayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(DisplayViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class DisplayViewHolder extends RecyclerView.ViewHolder{

        public DisplayViewHolder(View itemView) {
            super(itemView);
        }
    }
}
