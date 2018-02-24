package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.Utils;
import com.ibnkhaldoun.studentside.models.Mark;

import java.util.ArrayList;
import java.util.List;


public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.MarksViewHolder> {

    private List<Mark> mMarkList = new ArrayList<>();
    private Context mContext;

    public MarksAdapter(List<Mark> dataList, Context context) {
        this.mMarkList = dataList;
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
        Mark mark = mMarkList.get(position);
        holder.mShortSubjectTextView.setText(mark.getShortSubjectName());
        holder.mLinearSubject.setBackgroundColor(Utils.getCircleColor(mark.getSubjectName().charAt(0), mContext));

        holder.mExamTextView.setText(String.valueOf(mark.getExam()));
        GradientDrawable examCircle = (GradientDrawable) holder.mExamTextView.getBackground();
        examCircle.setColor(Utils.getMarkColor(mark.getExam(), mContext));

        if (mark.getTD() != -1) {

            holder.mTdTextView.setText(mark.getTD() == (int) mark.getTD() ? String.valueOf((int) mark.getTD()) : String.valueOf(mark.getTD()));
            GradientDrawable tdCircle = (GradientDrawable) holder.mTdTextView.getBackground();
            tdCircle.setColor(Utils.getMarkColor(mark.getTD(), mContext));
        } else {
            holder.mLinearTd.setVisibility(View.INVISIBLE);
        }

        if (mark.getTP() != -1) {
            holder.mTpTextView.setText(String.valueOf(mark.getTP()));
            GradientDrawable tpCircle = (GradientDrawable) holder.mTpTextView.getBackground();
            tpCircle.setColor(Utils.getMarkColor(mark.getTP(), mContext));
        } else {
            holder.mLinearTp.setVisibility(View.INVISIBLE);
        }

        holder.mAverageTextView.setText("19");
        holder.mAverageTextView.setTextColor(Utils.getMarkColor(19, mContext));

    }


    @Override
    public int getItemCount() {
        return mMarkList.size();
    }

    class MarksViewHolder extends RecyclerView.ViewHolder {

        final TextView mShortSubjectTextView,
                mTpTextView,
                mTdTextView,
                mExamTextView, mAverageTextView;
        final LinearLayout mLinearSubject, mLinearExam, mLinearTp, mLinearTd;

        MarksViewHolder(View itemView) {
            super(itemView);

            mShortSubjectTextView = itemView.findViewById(R.id.mark_subject_text_view);
            mTpTextView = itemView.findViewById(R.id.mark_tp_mark_text_view);
            mTdTextView = itemView.findViewById(R.id.mark_td_mark_text_view);
            mExamTextView = itemView.findViewById(R.id.mark_exam_mark_text_view);

            itemView.setOnClickListener(v -> {
                //todo code to launch the detail activity of the subject
                String display = mMarkList.get(getAdapterPosition()).getSubjectName();
                Toast.makeText(mContext, display, Toast.LENGTH_SHORT).show();
            });
            mLinearTd = itemView.findViewById(R.id.mark_linear_TD);
            mLinearSubject = itemView.findViewById(R.id.mark_linear_subject);
            mLinearExam = itemView.findViewById(R.id.mark_linear_exam);
            mLinearTp = itemView.findViewById(R.id.mark_linear_TP);
            mAverageTextView = itemView.findViewById(R.id.mark_average_text_view);
        }
    }
}
