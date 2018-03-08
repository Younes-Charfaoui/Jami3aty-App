package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.Utils;
import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.enums.UnityTypes;
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
        holder.mShortTitleTextView.setBackgroundColor(Utils.getCircleColor(subject.getTitle().charAt(0), mContext));
    }

    public void swapCursor(Cursor cursor) {
        mSubjectList = new ArrayList<>();
        if (cursor != null) {
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
                int level = cursor.getInt(cursor.
                        getColumnIndex(DatabaseContract.SubjectEntry.COLUMN_LEVEL));
                int unityType = cursor.getInt(cursor.
                        getColumnIndex(DatabaseContract.SubjectEntry.COLUMN_UNITY_TYPE));
                mSubjectList.add(new Subject(title, shortTitle,
                        summary, tableContent, credit, coefficient,
                        UnityTypes.getUnitType(unityType), level));
            }
            notifyDataSetChanged();
        }
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
        }
    }
}