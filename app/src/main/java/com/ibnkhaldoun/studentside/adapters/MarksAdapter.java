package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.Utils;
import com.ibnkhaldoun.studentside.models.Mark;

import java.util.ArrayList;
import java.util.List;


public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.MarksViewHolder> {

    private List<Mark> mDataList = new ArrayList<>();
    private Context mContext;

    public MarksAdapter(List<Mark> dataList, Context context) {
        this.mDataList = dataList;
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
        Mark mark = mDataList.get(position);
        holder.mShortSubjectTextView.setText(mark.getShortSubjectName());
        GradientDrawable circleDrawable = (GradientDrawable) holder.mShortSubjectTextView.getBackground();
        circleDrawable.setColor(Utils.getCircleColor(mark.getShortSubjectName().charAt(0), mContext));
        holder.mExamTextView.setText(String.valueOf(mark.getExam()));
        holder.mTdTextView.setText(String.valueOf(mark.getTD()));
        holder.mTpTextView.setText(String.valueOf(mark.getTP()));

    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MarksViewHolder extends RecyclerView.ViewHolder {

        final TextView mShortSubjectTextView,

        mTpTextView,
                mTdTextView,
                mExamTextView;

        MarksViewHolder(View itemView) {
            super(itemView);

            mShortSubjectTextView = itemView.findViewById(R.id.mark_subject_short_text);
            mTpTextView = itemView.findViewById(R.id.tp_note);
            mTdTextView = itemView.findViewById(R.id.td_note);
            mExamTextView = itemView.findViewById(R.id.exam_note);
            itemView.setOnClickListener(v -> {
                //todo code to launch the detail activity of the subject
                String display = mDataList.get(getAdapterPosition()).getSubjectName();
                Toast.makeText(mContext, display, Toast.LENGTH_SHORT).show();
            });
        }
    }
}
