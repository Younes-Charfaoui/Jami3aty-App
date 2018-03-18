package com.ibnkhaldoun.studentside.adapters;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.models.ScheduleItem;

import java.util.List;


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
        holder.mTimeTextView.setText(scheduleItem.getTime());
        if (Integer.parseInt(Character.toString(scheduleItem.getTime().charAt(0))) > 12) {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.active_schedule));
        }
        holder.mScheduleSubject.setText(scheduleItem.getSubject());
        String placeAndProfessor = scheduleItem.getLocation() + ", " + scheduleItem.getProfessor().getFullName();
        holder.mScheduleLocationAndProfessor.setText(placeAndProfessor);
        holder.mSeparatorView.setBackgroundColor(Utilities.getCircleColor(scheduleItem.getSubject().charAt(0), mContext));
        GradientDrawable circle = (GradientDrawable) holder.mCircleImage.getBackground();
        circle.setColor(Utilities.getCircleColor(scheduleItem.getProfessor().getFirstName().charAt(0), mContext));
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
