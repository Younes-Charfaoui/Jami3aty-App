package com.ibnkhaldoun.studentside.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.SavedViewHolder> {


    @Override
    public SavedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SavedViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SavedViewHolder extends RecyclerView.ViewHolder{
        public SavedViewHolder(View itemView) {
            super(itemView);
        }
    }
}
