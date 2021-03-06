/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.activities.SubjectsDetailActivity;
import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.models.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * @definition this class is the adapter for the subject recycler view
 * it has the appropriate method to define the adapter structure.
 */

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.SubjectViewHolder> {

    private Context mContext;
    private List<Subject> mSubjectList;

    public SubjectsAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.subject_list_item, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubjectViewHolder holder, int position) {
        Subject subject = mSubjectList.get(position);
        holder.mTitleTextView.setText(subject.getTitle());
        holder.mShortTitleTextView.setText(subject.getShortTitle());
        GradientDrawable circle = (GradientDrawable) holder.mShortTitleTextView.getBackground();
        circle.setColor(Utilities.getCircleColor(subject.getTitle().charAt(0), mContext));
    }

    public void swapCursor(Cursor cursor) {

        if (cursor != null) {
            mSubjectList = new ArrayList<>();
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.
                        getColumnIndex(DatabaseContract.SubjectEntry.COLUMN_TITLE));
                String shortTitle = cursor.getString(cursor.
                        getColumnIndex(DatabaseContract.SubjectEntry.COLUMN_SHORT_TITLE));
                String coefficient = cursor.getString(cursor.
                        getColumnIndex(DatabaseContract.SubjectEntry.COLUMN_COEFFICIENT));
                String credit = cursor.getString(cursor.
                        getColumnIndex(DatabaseContract.SubjectEntry.COLUMN_CREDIT));
                String summary = cursor.getString(cursor.
                        getColumnIndex(DatabaseContract.SubjectEntry.COLUMN_SUMMARY));
                String tableContent = cursor.getString(cursor.
                        getColumnIndex(DatabaseContract.SubjectEntry.COLUMN_TABLE_CONTENT));
                String tdProfessor = cursor.getString(cursor.
                        getColumnIndex(DatabaseContract.SubjectEntry.COLUMN_TD_PROFESSOR));
                String tpProfessor = cursor.getString(cursor.
                        getColumnIndex(DatabaseContract.SubjectEntry.COLUMN_TP_PROFESSOR));
                String courseProfessor = cursor.getString(cursor.
                        getColumnIndex(DatabaseContract.SubjectEntry.COLUMN_COURSE_PROFESSOR));
                int level = cursor.getInt(cursor.
                        getColumnIndex(DatabaseContract.SubjectEntry.COLUMN_LEVEL));
                int unityType = cursor.getInt(cursor.
                        getColumnIndex(DatabaseContract.SubjectEntry.COLUMN_UNITY_TYPE));
                mSubjectList.add(new Subject(title, shortTitle,
                        summary, tableContent, credit, coefficient,
                        tdProfessor, tpProfessor, courseProfessor, unityType, level));
            }
            notifyDataSetChanged();
        }
    }

    public void swapList(List<Subject> list) {
        this.mSubjectList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mSubjectList != null) return mSubjectList.size();
        return 0;
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder {

        private TextView mShortTitleTextView, mTitleTextView;

        SubjectViewHolder(View itemView) {
            super(itemView);
            mShortTitleTextView = itemView.findViewById(R.id.subject_list_item_short_title);
            mTitleTextView = itemView.findViewById(R.id.subject_list_item_title);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, SubjectsDetailActivity.class);
                intent.putExtra(SubjectsDetailActivity.SUBJECT_KEY,
                        mSubjectList.get(getAdapterPosition()));
                mContext.startActivity(intent);
            });
        }
    }
}
