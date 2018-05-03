package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.activities.DisplayDetailActivity;
import com.ibnkhaldoun.studentside.asyncTask.ResponseAsyncTask;
import com.ibnkhaldoun.studentside.enums.PostTypes;
import com.ibnkhaldoun.studentside.fragments.NoteOfDisplayDialog;
import com.ibnkhaldoun.studentside.models.Display;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.models.Response;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;

import java.util.List;

import static android.view.View.GONE;
import static com.ibnkhaldoun.studentside.activities.DisplayDetailActivity.DATA;
import static com.ibnkhaldoun.studentside.networking.models.RequestPackage.POST;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_POST_ID2;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_SAVE_ACTION;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_ID;

public class DisplaysAdapter extends RecyclerView.Adapter<DisplaysAdapter.DisplayViewHolder> {


    private int type;
    private Context mContext;
    private List<Display> mDataList;

    private FragmentManager manager;

    public DisplaysAdapter(Context mContext, FragmentManager manager) {
        this.mContext = mContext;
        this.manager = manager;
    }

    public DisplaysAdapter(Context mContext, FragmentManager manager, int type) {
        this.mContext = mContext;
        this.manager = manager;
        this.type = type;
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
        if (type != 1) {
            holder.mProfessorShortNameTv.setText(Utilities.getProfessorShortName(display.getProfessor()));
            GradientDrawable circleImage = (GradientDrawable) holder.mProfessorShortNameTv.getBackground();
            if (display.isSaved()) holder.mSaveButton.setEnabled(false);
            else holder.mSaveButton.setEnabled(true);
            circleImage.setColor(Utilities.getCircleColor(mContext));
            holder.mProfessorNameTv.setText(display.getProfessor());
            String subjectAndDate = display.getDate() + ". \n" + display.getSubject();
            holder.mDateTimeTv.setText(subjectAndDate);
            holder.mTextTv.setText(display.getText());
        } else {
            holder.mProfessorShortNameTv.setText(String.valueOf(display.getSubject().charAt(0)));
            GradientDrawable circleImage = (GradientDrawable) holder.mProfessorShortNameTv.getBackground();
            if (display.isSaved()) holder.mSaveButton.setEnabled(false);
            else holder.mSaveButton.setEnabled(true);
            circleImage.setColor(Utilities.getCircleColor(mContext));
            holder.mProfessorNameTv.setText(display.getSubject());
            holder.mDateTimeTv.setText(display.getDate());
            holder.mTextTv.setText(display.getText());
        }
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
                mTextTv;
        Button mSaveButton, mNoteButton;

        DisplayViewHolder(View itemView) {
            super(itemView);
            mProfessorShortNameTv = itemView.findViewById(R.id.display_professor_short_name_text_view);
            mProfessorNameTv = itemView.findViewById(R.id.display_professor_name_text_view);
            mDateTimeTv = itemView.findViewById(R.id.display_date_and_time_text_view);
            mTextTv = itemView.findViewById(R.id.display_text_text_view);
            mSaveButton = itemView.findViewById(R.id.display_save_button);
            mNoteButton = itemView.findViewById(R.id.display_note_button);
            if (type == 1)
                itemView.findViewById(R.id.display_bottom_control_layout).setVisibility(GONE);
            mSaveButton.setOnClickListener(v -> {
                if (NetworkUtilities.isConnected(mContext)) {
                    ResponseAsyncTask task = new ResponseAsyncTask(response -> {
                        if (response.getStatus() != Response.RESPONSE_SUCCESS) {
                            Toast.makeText(mContext, R.string.try_later_problem_connecting, Toast.LENGTH_SHORT).show();
                        } else {
                            mSaveButton.setEnabled(false);
                        }
                    });
                    RequestPackage request = new RequestPackage.Builder().addEndPoint(EndPointsProvider.getUnsaveEndpoint())
                            .addMethod(POST)
                            .addParams(JSON_STUDENT_ID,
                                    new PreferencesManager(mContext, PreferencesManager.STUDENT).getIdUser())
                            .addParams(JSON_POST_ID2, String.valueOf(mDataList.get(getAdapterPosition()).getId()))
                            .addParams(JSON_SAVE_ACTION, String.valueOf(1))
                            .create();
                    task.execute(request);
                } else {
                    Toast.makeText(mContext, R.string.no_internet_connection_string,
                            Toast.LENGTH_SHORT).show();
                }
            });

            mNoteButton.setOnClickListener(v -> {
                if (NetworkUtilities.isConnected(mContext)) {
                    NoteOfDisplayDialog dialog =
                            NoteOfDisplayDialog.newInstance(mDataList.get(getAdapterPosition()).getId(), NoteOfDisplayDialog.ADD);
                    dialog.show(manager, "Tag");
                } else {
                    Toast.makeText(mContext, R.string.no_internet_connection_string,
                            Toast.LENGTH_SHORT).show();
                }
            });

            if (type != 1) {
                itemView.setOnClickListener(v -> {
                    if (mDataList.get(getAdapterPosition()).getType() == PostTypes.MARK_TYPE) {

                    } else {
                        Intent intent = new Intent(mContext, DisplayDetailActivity.class);
                        intent.putExtra(DATA, mDataList.get(getAdapterPosition()));
                        mContext.startActivity(intent);
                    }
                });
            }
        }
    }
}
