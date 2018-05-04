/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.models.ProfessorInfo;

import java.util.List;

public class SubjectSpinnerAdapter extends ArrayAdapter<ProfessorInfo> {

    private List<ProfessorInfo> mInfoList;

    public SubjectSpinnerAdapter(@NonNull Context context, int resource, List<ProfessorInfo> list) {
        super(context, resource);
        this.mInfoList = list;
    }

    @Override
    public int getCount() {
        return mInfoList.size();
    }

    @Nullable
    @Override
    public ProfessorInfo getItem(int position) {
        return mInfoList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return Long.parseLong(mInfoList.get(position).getSubjectId());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setText(mInfoList.get(position).getSubjectTitle());
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setText(mInfoList.get(position).getSubjectTitle());
        return view;
    }
}
