package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.ibnkhaldoun.studentside.activities.SavedDetailActivity;
import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.interfaces.UnsaveListener;
import com.ibnkhaldoun.studentside.models.Saved;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;
import com.ibnkhaldoun.studentside.services.LoadDataService;

import java.util.ArrayList;
import java.util.List;

import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_POST_ID;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_POST_ID2;


public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.SavedViewHolder> {

    private Context mContext;
    private List<Saved> mSavedList;
    private UnsaveListener mUnSaveListener;

    public SavedAdapter(Context context, UnsaveListener listener) {
        this.mContext = context;
        this.mUnSaveListener = listener;
    }

    @Override
    public SavedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.saved_list_item, parent, false);
        return new SavedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SavedViewHolder holder, int position) {
        Saved saved = mSavedList.get(position);
        holder.mProfessorShortNameTv.setText(Utilities.getProfessorShortName(saved.getProfessor()));
        GradientDrawable circleImage = (GradientDrawable) holder.mProfessorShortNameTv.getBackground();
        circleImage.setColor(Utilities.getCircleColor(mContext));
        holder.mProfessorNameTv.setText(saved.getProfessor());
        holder.mDateTimeTv.setText(Utilities.getDateFormat(saved.getDate()));
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


    public long getIdAt(int position) {
        return mSavedList.get(position).getId();
    }

    public void removeSaveAt(int position) {
        mSavedList.remove(position);
        notifyItemRemoved(position);
        if (mSavedList.size() == 0) mUnSaveListener.OnUnsavedEmpty();
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


                            if (NetworkUtilities.isConnected(mContext)) {
                                long id = mSavedList.get(getAdapterPosition()).getId();
                                int position = getAdapterPosition();
                                mUnSaveListener.OnUnsaveStarted(id, position);
                            } else {
                                Toast.makeText(mContext,
                                        R.string.no_internet_connection_string,
                                        Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        });
                        popup.show();
                    }
            );

            itemView.setOnClickListener(v -> {
                if (NetworkUtilities.isConnected(mContext)) {
                    RequestPackage requestPackage = new RequestPackage.Builder()
                            .addEndPoint(EndPointsProvider.getCommentsEndpoint())
                            .addMethod(RequestPackage.POST)
                            .addParams(JSON_POST_ID2, String.valueOf(mSavedList.get(
                                    getAdapterPosition()).getId()))
                            .create();

                    Intent intentService = new Intent(mContext, LoadDataService.class);
                    intentService.putExtra(LoadDataService.KEY_REQUEST, requestPackage);
                    intentService.putExtra(LoadDataService.KEY_ACTION, LoadDataService.COMMENT_TYPE);
                    mContext.startService(intentService);

                    mContext.startActivity(new Intent(mContext,
                            SavedDetailActivity.class)
                            .putExtra(SavedDetailActivity.KEY_SAVED
                                    , mSavedList.get(getAdapterPosition())));
                } else {
                    Toast.makeText(mContext,
                            R.string.no_internet_connection_string,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
