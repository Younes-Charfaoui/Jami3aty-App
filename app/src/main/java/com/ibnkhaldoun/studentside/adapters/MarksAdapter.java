/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.activities.MarkDetailActivity;
import com.ibnkhaldoun.studentside.models.MarkItem;

import java.util.ArrayList;
import java.util.List;


public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.MarksViewHolder> {

    private List<MarkItem> mMarkList = new ArrayList<>();
    private Context mContext;

    public MarksAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public MarksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.marks_list_item, parent, false);

        return new MarksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MarksViewHolder holder, int position) {
        MarkItem mark = mMarkList.get(position);
        holder.mShortSubjectTextView.setText(mark.getShortSubjectName());
        holder.mLinearSubject.setBackgroundColor(Utilities.getCircleColor(mark.getSubjectName().charAt(0), mContext));
        holder.mShortSubjectTextView.setBackgroundColor(Utilities.getCircleColor(mark.getShortSubjectName().charAt(0), mContext));
        holder.mExamTextView.setText(String.valueOf(mark.getExam()));


        if (mark.getTD() != -1) {
            holder.mTdTextView.setText(mark.getTD() == (int) mark.getTD() ? String.valueOf((int) mark.getTD()) : String.valueOf(mark.getTD()));
        } else {
            holder.mLinearTd.setVisibility(View.GONE);
        }

        if (mark.getTP() != -1) {
            holder.mTpTextView.setText(String.valueOf(mark.getTP()));
        } else {
            holder.mLinearTp.setVisibility(View.GONE);
        }

        holder.mAverageTextView.setText("19");
    }


    @Override
    public int getItemCount() {
        return mMarkList.size();
    }


    public void swapList(List<MarkItem> markList) {
        this.mMarkList = markList;
        notifyDataSetChanged();
    }

    class MarksViewHolder extends RecyclerView.ViewHolder {

        final LinearLayout mLinearSubject, mLinearExam, mLinearTp, mLinearTd;
        TextView mShortSubjectTextView,
                mTpTextView,
                mTdTextView,
                mExamTextView, mAverageTextView;

        MarksViewHolder(View itemView) {
            super(itemView);

            mShortSubjectTextView = itemView.findViewById(R.id.mark_subject_text_view);
            mTpTextView = itemView.findViewById(R.id.mark_tp_mark_text_view);
            mTdTextView = itemView.findViewById(R.id.mark_td_mark_text_view);
            mExamTextView = itemView.findViewById(R.id.mark_exam_mark_text_view);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, MarkDetailActivity.class);
                intent.putExtra(MarkDetailActivity.KEY_MARK, mMarkList.get(getAdapterPosition()));
                mContext.startActivity(intent);
            });
            mLinearTd = itemView.findViewById(R.id.mark_linear_TD);
            mLinearSubject = itemView.findViewById(R.id.mark_linear_layout_marks);
            mLinearExam = itemView.findViewById(R.id.mark_linear_exam);
            mLinearTp = itemView.findViewById(R.id.mark_linear_TP);
            mAverageTextView = itemView.findViewById(R.id.mark_average_text_view);
            //mAverageTextView = itemView.findViewById(R.id.mark_average_text_view);
        }
    }
}
