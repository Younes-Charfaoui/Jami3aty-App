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
import com.ibnkhaldoun.studentside.activities.NoteEditActivity;
import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private Context mContext;
    private List<Note> mNoteList;

    public NotesAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setNotes(List<Note> noteList) {
        this.mNoteList = noteList;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.note_list_item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        Note note = mNoteList.get(position);
        holder.itemView.setTag(note.getId());
        holder.mStarterView.setBackgroundColor(Utilities.getCircleColor(note.getSubject().charAt(0), mContext));
        holder.mSubjectTextView.setText(Character.toString(note.getSubject().charAt(0)).toUpperCase());
        GradientDrawable drawable = (GradientDrawable) holder.mSubjectTextView.getBackground();
        drawable.setColor(Utilities.getCircleColor(note.getSubject().charAt(0), mContext));
        holder.mNoteTextView.setText(note.getNote());
    }

    @Override
    public int getItemCount() {
        if (mNoteList != null) return mNoteList.size();
        return 0;
    }

    public void swapCursor(Cursor cursor) {
        mNoteList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.NoteEntry.COLUMN_NOTE_ID));
                String subject = cursor.getString(cursor.getColumnIndex(DatabaseContract.NoteEntry.COLUMN_NOTE_SUBJECT));
                String note = cursor.getString(cursor.getColumnIndex(DatabaseContract.NoteEntry.COLUMN_NOTE_TEXT));
                mNoteList.add(new Note(id, subject, note));
            }
        }
        notifyDataSetChanged();
    }

    public Note removeItem(int position) {
        Note note = mNoteList.get(position);
        mNoteList.remove(position);
        notifyItemRemoved(position);
        return note;
    }

    public void restoreItem(int position, Note note) {
        mNoteList.add(position, note);
        notifyItemInserted(position);
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private View mStarterView;
        private TextView mNoteTextView, mSubjectTextView;

        NotesViewHolder(View itemView) {
            super(itemView);
            mStarterView = itemView.findViewById(R.id.note_list_item_starter_view);
            mSubjectTextView = itemView.findViewById(R.id.note_list_item_subject);
            mNoteTextView = itemView.findViewById(R.id.note_list_item_text);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, NoteEditActivity.class);
                intent.putExtra(NoteEditActivity.KEY_ID, itemView.getTag().toString());
                intent.putExtra(NoteEditActivity.KEY_SENDER, NoteEditActivity.KEY_MODIFY);
                mContext.startActivity(intent);
            });
        }
    }
}
