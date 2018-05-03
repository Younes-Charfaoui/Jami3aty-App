/*
 * Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 */

package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class GroupSpinnerAdapter extends ArrayAdapter<Integer> {

    private List<Integer> mInfoList;

    public GroupSpinnerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void setList(List<Integer> list) {
        this.mInfoList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mInfoList == null ? 0 : mInfoList.size();
    }

    @Nullable
    @Override
    public Integer getItem(int position) {
        return mInfoList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return mInfoList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        String text;
        int value = mInfoList.get(position);
        if (value == 0) text = "All";
        else text = "Group " + value;
        view.setText(text);
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        String text;
        int value = mInfoList.get(position);
        if (value == 0) text = "All";
        else text = "Group " + value;
        view.setText(text);
        return view;
    }
}
