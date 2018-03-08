package com.ibnkhaldoun.studentside.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.SubjectViewHolder> {

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SubjectViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder{

        public SubjectViewHolder(View itemView) {
            super(itemView);
        }
    }
}
