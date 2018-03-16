package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.models.Saved;

import java.util.List;


public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.SavedViewHolder> {

    private Context mContext;
    private List<Saved> mSavedList;

    public SavedAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public SavedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.display_list_item, parent, false);
        return new SavedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SavedViewHolder holder, int position) {
        Saved saved = mSavedList.get(position);
        holder.mProfessorShortNameTv.setText(Utilities.getShortName(saved.getProfessor()));
        GradientDrawable circleImage = (GradientDrawable) holder.mProfessorShortNameTv.getBackground();
        circleImage.setColor(Utilities.getCircleColor(saved.getProfessor().charAt(0), mContext));
        holder.mProfessorNameTv.setText(saved.getProfessor());
        holder.mDateTimeTv.setText(saved.getDate());
        holder.mTextTv.setText(saved.getText());
    }

    @Override
    public int getItemCount() {
        if (mSavedList != null) return mSavedList.size();
        return 0;
    }

    public void swapCursor(Cursor cursor) {
        mSavedList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.SavedEntry.COLUMN_ID));
                String professor = cursor.getString(cursor.getColumnIndex(DatabaseContract.SavedEntry.COLUMN_NAME));
                String text = cursor.getString(cursor.getColumnIndex(DatabaseContract.SavedEntry.COLUMN_DISPLAY_TEXT));
                String date = cursor.getString(cursor.getColumnIndex(DatabaseContract.SavedEntry.COLUMN_DATE));
                mSavedList.add(new Saved(id, professor, text, date));
            }
        }
        notifyDataSetChanged();
    }

    class SavedViewHolder extends RecyclerView.ViewHolder {

        TextView mProfessorShortNameTv,
                mProfessorNameTv, mDateTimeTv,
                mTextTv, mNumberOfNoteTv;

        Button mUnSaveButton;

        SavedViewHolder(View itemView) {
            super(itemView);
            mProfessorShortNameTv = itemView.findViewById(R.id.display_professor_short_name_text_view);
            mProfessorNameTv = itemView.findViewById(R.id.display_professor_name_text_view);
            mDateTimeTv = itemView.findViewById(R.id.display_date_and_time_text_view);
            mTextTv = itemView.findViewById(R.id.display_text_text_view);
            mUnSaveButton = itemView.findViewById(R.id.display_save_button);
            mNumberOfNoteTv = itemView.findViewById(R.id.display_number_of_notes);
            itemView.findViewById(R.id.display_note_button).setVisibility(View.GONE);
            itemView.setOnClickListener(v -> {
                //Intent intent = new Intent(mContext, DisplayDetailActivity.class);
                //mCursor.moveToPosition(getAdapterPosition());
                //intent.putExtra(SENDER,mCursor.getInt(mCursor.getColumnIndex(DatabaseContract.SavedEntry.COLUMN_ID)));
                //  mContext.startActivity(intent);
            });
        }


    }
}
