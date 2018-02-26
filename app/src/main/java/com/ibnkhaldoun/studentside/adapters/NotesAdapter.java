package com.ibnkhaldoun.studentside.adapters;


import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.Utils;
import com.ibnkhaldoun.studentside.database.DatabaseContract;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public NotesAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.note_list_item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String subject = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.NoteEntry.COLUMN_NOTE_SUBJECT));
        String note = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.NoteEntry.COLUMN_NOTE_TEXT));
        holder.mStarterView.setBackgroundColor(subject.charAt(0));
        holder.mSubjectTextView.setText(subject);
        GradientDrawable drawable = (GradientDrawable) holder.mSubjectTextView.getBackground();
        drawable.setColor(Utils.getCircleColor(subject.charAt(0), mContext));
        holder.mNoteTextView.setText(note);
    }

    @Override
    public int getItemCount() {
        if (mCursor != null) return mCursor.getCount();
        return 0;
    }

    public void swapCursor(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private View mStarterView;
        private TextView mNoteTextView, mSubjectTextView;

        NotesViewHolder(View itemView) {
            super(itemView);
            mStarterView = itemView.findViewById(R.id.note_list_item_starter_view);
            mNoteTextView = itemView.findViewById(R.id.note_list_item_text);
            mSubjectTextView = itemView.findViewById(R.id.note_list_item_subject);
        }
    }
}
