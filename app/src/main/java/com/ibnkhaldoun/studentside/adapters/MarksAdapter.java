package com.ibnkhaldoun.studentside.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.MarksViewHolder> {
    @Override
    public MarksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MarksViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 0;
    }

    public  class MarksViewHolder extends RecyclerView.ViewHolder {

        public MarksViewHolder(View itemView) {
            super(itemView);
        }
    }
}
