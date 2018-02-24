package com.ibnkhaldoun.studentside.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.models.ScheduleItem;

import java.util.List;


public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private Context mContext;
    private List<ScheduleItem> mScheduleList;

    public ScheduleAdapter(Context mContext, List<ScheduleItem> mScheduleList) {
        this.mContext = mContext;
        this.mScheduleList = mScheduleList;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.schedule_list_item, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        ScheduleItem scheduleItem = mScheduleList.get(position);
        holder.mTimeTextView.setText(scheduleItem.getTime());
        holder.mScheduleSubject.setText(scheduleItem.getSubject());
        String placeAndProfessor = scheduleItem.getLocation() + ", " + scheduleItem.getProfessor().getFullName();
        holder.mScheduleLocationAndProfessor.setText(placeAndProfessor);
    }

    @Override
    public int getItemCount() {
        return mScheduleList.size();
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private TextView mTimeTextView, mScheduleSubject, mScheduleLocationAndProfessor;
        private ImageView mCircleImage;
        private View mSeparatorView;

        ScheduleViewHolder(View itemView) {
            super(itemView);
            mTimeTextView = itemView.findViewById(R.id.schedule_time);
            mScheduleSubject = itemView.findViewById(R.id.schedule_subject);
            mScheduleLocationAndProfessor = itemView.findViewById(R.id.schedule_location_and_professor);
            mCircleImage = itemView.findViewById(R.id.schedule_circle);
            mSeparatorView = itemView.findViewById(R.id.schedule_separator);
        }
    }
}
