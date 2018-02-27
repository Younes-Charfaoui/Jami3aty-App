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


public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.SavedViewHolder> {



    private Cursor mCursor;
    private Context mContext;

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
        mCursor.moveToPosition(position);
        String professor = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.SavedEntry.COLUMN_NAME));
        holder.mProfessorShortNameTv.setText(Utils.getShortName(professor));
        GradientDrawable circleImage = (GradientDrawable) holder.mProfessorShortNameTv.getBackground();
        circleImage.setColor(Utils.getCircleColor(professor.charAt(0), mContext));
        holder.mProfessorNameTv.setText(professor);
        holder.mDateTimeTv.setText(mCursor.getString(mCursor.getColumnIndex(DatabaseContract.SavedEntry.COLUMN_DATE)));
        holder.mTextTv.setText(mCursor.getString(mCursor.getColumnIndex(DatabaseContract.SavedEntry.COLUMN_DISPLAY_TEXT)));
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

    class SavedViewHolder extends RecyclerView.ViewHolder {

        TextView mProfessorShortNameTv,
                mProfessorNameTv, mDateTimeTv,
                mTextTv;

        SavedViewHolder(View itemView) {
            super(itemView);
            mProfessorShortNameTv = itemView.findViewById(R.id.display_professor_short_name_text_view);
            mProfessorNameTv = itemView.findViewById(R.id.display_professor_name_text_view);
            mDateTimeTv = itemView.findViewById(R.id.display_date_and_time_text_view);
            mTextTv = itemView.findViewById(R.id.display_text_text_view);
            itemView.findViewById(R.id.display_separator).setVisibility(View.GONE);
            itemView.findViewById(R.id.display_bottom_control_layout).setVisibility(View.GONE);

            itemView.setOnClickListener(v -> {
                //Intent intent = new Intent(mContext, DisplayDetailActivity.class);
                //mCursor.moveToPosition(getAdapterPosition());
                //intent.putExtra(SENDER,mCursor.getInt(mCursor.getColumnIndex(DatabaseContract.SavedEntry.COLUMN_ID)));
                //  mContext.startActivity(intent);
            });
        }


    }
}
