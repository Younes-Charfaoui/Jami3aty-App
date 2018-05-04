/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.adapters;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.models.ScheduleItem;

import java.util.List;

import static com.ibnkhaldoun.studentside.R.color.colorPrimary;


public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private Context mContext;
    private List<ScheduleItem> mScheduleList;

    public ScheduleAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.schedule_list_item, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        ScheduleItem scheduleItem = mScheduleList.get(position);
        holder.mTimeTextView.setText(getTime(scheduleItem.getTime()));
        if (Integer.parseInt(Character.toString(getTime(scheduleItem.getTime()).charAt(0))) > 12) {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.active_schedule));
        }
        holder.mScheduleSubject.setText(scheduleItem.getSubject());
        String placeAndProfessor = scheduleItem.getLocation() + ", " + scheduleItem.getProfessor();
        holder.mScheduleLocationAndProfessor.setText(placeAndProfessor);
        holder.mCircleImage.setColorFilter(ContextCompat.getColor(mContext, colorPrimary));
        holder.mTypeScheduleTextView.setText(getType(scheduleItem.getType()));
    }

    @Override
    public int getItemCount() {
        if (mScheduleList != null) return mScheduleList.size();
        return 0;
    }

    public void setList(List<ScheduleItem> list) {
        this.mScheduleList = list;
        notifyDataSetChanged();
    }

    private String getTime(int time) {
        switch (time) {
            case 1:
                return "8:00";
            case 2:
                return "9:30";
            case 3:
                return "11:00";
            case 4:
                return "12:30";
            case 5:
                return "14:00";
            case 6:
                return "15:30";
            default:
                return "8:00";
        }
    }

    private String getType(int type) {
        switch (type) {
            case 1:
                return "Course";
            case 2:
                return "TD";
            case 3:
                return "TP";
            default:
                return "Course";
        }
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private TextView mTimeTextView, mScheduleSubject,
                mScheduleLocationAndProfessor, mTypeScheduleTextView;
        private ImageView mCircleImage;


        ScheduleViewHolder(View itemView) {
            super(itemView);
            mTimeTextView = itemView.findViewById(R.id.schedule_time);
            mScheduleSubject = itemView.findViewById(R.id.schedule_subject);
            mScheduleLocationAndProfessor = itemView.findViewById(R.id.schedule_location_and_professor);
            mCircleImage = itemView.findViewById(R.id.schedule_circle);
            mTypeScheduleTextView = itemView.findViewById(R.id.schedule_type);
        }
    }
}
