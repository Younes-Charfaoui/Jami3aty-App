package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.models.Saved;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.SavedViewHolder> {

    private Context mContext;
    private List<Saved> mSavedList;

    public SavedAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public SavedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.saved_list_item, parent, false);
        return new SavedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SavedViewHolder holder, int position) {
        Saved saved = mSavedList.get(position);
        holder.mProfessorShortNameTv.setText(Utilities.getShortName(saved.getProfessor()));
        GradientDrawable circleImage = (GradientDrawable) holder.mProfessorShortNameTv.getBackground();
        circleImage.setColor(Utilities.getCircleColor(mContext));
        holder.mProfessorNameTv.setText(saved.getProfessor());
        holder.mDateTimeTv.setText(getDateFormat(saved.getDate()));
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
                String subject = cursor.getString(cursor.getColumnIndex(DatabaseContract.SavedEntry.COLUMN_SUBJECT));
                String file = cursor.getString(cursor.getColumnIndex(DatabaseContract.SavedEntry.COLUMN_FILE));
                Saved save = new Saved(id, professor, text, date);
                save.setSubjectTitle(subject);
                save.setFilePath(file);
                mSavedList.add(save);
            }
        }
        notifyDataSetChanged();
    }

    public void swapList(ArrayList<Saved> savedList) {
        this.mSavedList = savedList;
        notifyDataSetChanged();
    }

    private String getDateFormat(String date) {

        long time = Long.parseLong(date);
        Date dateB = new Date(time);

        SimpleDateFormat format = new SimpleDateFormat("dd MMM 'at' HH:mm", Locale.getDefault());

        return format.format(dateB);
    }

    class SavedViewHolder extends RecyclerView.ViewHolder {

        TextView mProfessorShortNameTv,
                mProfessorNameTv, mDateTimeTv,
                mTextTv;

        ImageView mPopUpMenu;

        SavedViewHolder(View itemView) {
            super(itemView);
            mProfessorShortNameTv = itemView.findViewById(R.id.saved_professor_short_name_text_view);
            mProfessorNameTv = itemView.findViewById(R.id.saved_professor_name_text_view);
            mDateTimeTv = itemView.findViewById(R.id.saved_date_and_time_text_view);
            mTextTv = itemView.findViewById(R.id.saved_text_text_view);
            mPopUpMenu = itemView.findViewById(R.id.saved_pop_up);

            mPopUpMenu.setOnClickListener(v -> {
                        PopupMenu popup = new PopupMenu(mContext, mPopUpMenu);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.menu_saved_item, popup.getMenu());
                        popup.setOnMenuItemClickListener(item -> {
                            long id = mSavedList.get(getAdapterPosition()).getId();
                            //todo add the code to handle the unsave process
                            Toast.makeText(mContext, "The item was with id " + id, Toast.LENGTH_LONG).show();
                            return true;
                        });
                        popup.show();
                    }
            );

            itemView.setOnClickListener(v -> {
                //todo add the click handler
            });


        }


    }
}
