package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.activities.MessageDetailActivity;
import com.ibnkhaldoun.studentside.activities.MessagesActivity;
import com.ibnkhaldoun.studentside.models.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MailViewHolder> {

    private Context mContext;
    private List<Message> mMailList;

    public MessageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.mail_list_item, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MailViewHolder holder, int position) {
        Message mail = mMailList.get(position);

        if (mail.isIn()) {
            holder.mArrowIndicatorImage.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),
                    R.drawable.ic_arrow_downward_white_24dp));
        } else {
            holder.mArrowIndicatorImage.setImageBitmap(BitmapFactory
                    .decodeResource(mContext.getResources(),
                            R.drawable.ic_arrow_upward_white_24dp));
        }

        int color = Utilities.getCircleColor(mContext);
        holder.mBackGroundImage.setBackgroundColor(color);
        holder.mProfessorNameTextView.setText(mail.getProfessor());
        holder.mSubjectTextView.setText(mail.getSubject());
        holder.mDateTextView.setText(mail.getDate());
        holder.mStartView.setBackgroundColor(color);
    }


    @Override
    public int getItemCount() {
        if (mMailList != null) return mMailList.size();
        return 0;
    }

    public void setMailList(List<Message> list) {
        this.mMailList = list;
        notifyDataSetChanged();
    }

    class MailViewHolder extends RecyclerView.ViewHolder {

        private TextView
                mProfessorNameTextView, mSubjectTextView,
                mDateTextView;
        private View mBackGroundImage;
        private ImageView mArrowIndicatorImage;

        private View mStartView;

        MailViewHolder(View itemView) {
            super(itemView);
            mBackGroundImage = itemView.findViewById(R.id.mail_background_circle);
            mArrowIndicatorImage = itemView.findViewById(R.id.mail_arrow_image);
            mProfessorNameTextView = itemView.findViewById(R.id.mail_professor_name_text_view);
            mSubjectTextView = itemView.findViewById(R.id.mail_last_subject_text_view);
            mDateTextView = itemView.findViewById(R.id.mail_date_of_mail_text_view);
            mStartView = itemView.findViewById(R.id.mail_start_view);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, MessageDetailActivity.class);
                intent.putExtra(MessagesActivity.KEY_MESSAGES, mMailList.get(getAdapterPosition()));
                mContext.startActivity(intent);
            });
        }
    }
}
