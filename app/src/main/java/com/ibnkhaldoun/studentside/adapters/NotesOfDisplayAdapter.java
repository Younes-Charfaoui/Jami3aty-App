/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.models.Comment;

import java.util.List;

public class NotesOfDisplayAdapter extends RecyclerView.Adapter<NotesOfDisplayAdapter.NotesOfDisplayViewHolder> {

    private List<Comment> mCommentList;
    private Context mContext;
    private long idPerson;
    private INoteOfDisplayMore mInterface;


    public NotesOfDisplayAdapter(Context mContext, INoteOfDisplayMore Interface) {
        this.mContext = mContext;
        this.mInterface = Interface;
        idPerson = Long.parseLong(new PreferencesManager(mContext, PreferencesManager.STUDENT).getIdUser());
    }


    @Override
    public NotesOfDisplayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.note_of_display_list_item, parent, false);
        return new NotesOfDisplayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesOfDisplayViewHolder holder, int position) {
        Comment comment = mCommentList.get(position);

        if (comment.getIdCommenter() == idPerson) holder.itemView.setOnLongClickListener(v -> {
            mInterface.OnLongClick(comment.getIdComment(), comment.getComment());
            return true;
        });
        else holder.itemView.setOnLongClickListener(null);

        holder.mShortNameTv.setText(Utilities.getStudentShortName(comment.getName()));
        GradientDrawable background = (GradientDrawable) holder.mShortNameTv.getBackground();
        background.setColor(Utilities.getCircleColor(mContext));

        holder.mDateTv.setText(Utilities.getDateFormat(comment.getDate()));

        holder.mNameTv.setText(comment.getName());

        holder.mNoteTv.setText(comment.getComment());
    }

    public void swapList(List<Comment> list) {
        this.mCommentList = list;
        notifyDataSetChanged();
    }

    public void removeElement(long id) {
        for (int i = 0; i < mCommentList.size(); i++) {
            if (mCommentList.get(i).getIdComment() == id) {
                mCommentList.remove(i);
                notifyItemRemoved(i);
                i = mCommentList.size() + 1;
            }
        }
    }

    public void updateElement(String note, long id) {
        for (int i = 0; i < mCommentList.size(); i++) {
            if (mCommentList.get(i).getIdComment() == id) {
                mCommentList.get(i).setComment(note);
                notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mCommentList != null) return mCommentList.size();
        return 0;
    }

    public interface INoteOfDisplayMore {
        void OnLongClick(long idComment, String comment);
    }

    class NotesOfDisplayViewHolder extends RecyclerView.ViewHolder {

        private TextView mShortNameTv, mNameTv, mNoteTv, mDateTv;

        NotesOfDisplayViewHolder(View itemView) {
            super(itemView);
            mShortNameTv = itemView.findViewById(R.id.note_of_display_person_short_name_text_view);
            mNameTv = itemView.findViewById(R.id.note_of_display_person_name_text_view);
            mNoteTv = itemView.findViewById(R.id.note_of_display_note);
            mDateTv = itemView.findViewById(R.id.note_of_display_date);

        }
    }
}
