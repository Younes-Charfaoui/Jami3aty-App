package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.activities.DisplayDetailActivity;
import com.ibnkhaldoun.studentside.models.Display;

import java.util.List;

public class DisplaysAdapter extends RecyclerView.Adapter<DisplaysAdapter.DisplayViewHolder> {


    private Context mContext;
    private List<Display> mDataList;

    public DisplaysAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public DisplayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.display_list_item, parent, false);
        return new DisplayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DisplayViewHolder holder, int position) {
        Display display = mDataList.get(position);
        holder.mProfessorShortNameTv.setText(display.getProfessor().getShortName());
        GradientDrawable circleImage = (GradientDrawable) holder.mProfessorShortNameTv.getBackground();
        circleImage.setColor(Utilities.getCircleColor(display.getProfessor().getShortName().charAt(0), mContext));
        holder.mProfessorNameTv.setText(display.getProfessor().getFullName());
        holder.mDateTimeTv.setText(display.getDate());
        holder.mTextTv.setText(display.getText());
        holder.mNumberOfNoteTextView.setText(String.valueOf(display.getCommentList().size() + " note"));
    }

    @Override
    public int getItemCount() {
        if (mDataList != null) return mDataList.size();
        return 0;
    }

    public void swapList(List<Display> displayList) {
        this.mDataList = displayList;
        notifyDataSetChanged();
    }

    class DisplayViewHolder extends RecyclerView.ViewHolder {

        TextView mProfessorShortNameTv,
                mProfessorNameTv, mDateTimeTv,
                mTextTv, mNumberOfNoteTextView;
        Button mSaveButton, mNoteButton;

        DisplayViewHolder(View itemView) {
            super(itemView);
            mProfessorShortNameTv = itemView.findViewById(R.id.display_professor_short_name_text_view);
            mProfessorNameTv = itemView.findViewById(R.id.display_professor_name_text_view);
            mDateTimeTv = itemView.findViewById(R.id.display_date_and_time_text_view);
            mTextTv = itemView.findViewById(R.id.display_text_text_view);
            mSaveButton = itemView.findViewById(R.id.display_save_button);
            mNoteButton = itemView.findViewById(R.id.display_note_button);
            mNumberOfNoteTextView = itemView.findViewById(R.id.display_number_of_notes);
            mSaveButton.setOnClickListener(v -> {
                //todo , put the code we need to save the publication in the storage
                Toast.makeText(mContext, "Save Button", Toast.LENGTH_SHORT).show();
            });

            mNoteButton.setOnClickListener(v -> {
                //todo , put the code we need to send a note to this publication
                Toast.makeText(mContext, "Note Button", Toast.LENGTH_SHORT).show();
            });

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, DisplayDetailActivity.class);
                intent.putExtra("Key", mDataList.get(getAdapterPosition()));
                mContext.startActivity(intent);
            });
        }
    }
}
