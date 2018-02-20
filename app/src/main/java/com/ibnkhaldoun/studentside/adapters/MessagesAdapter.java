package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.models.Message;

import java.util.List;


public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    private Context mContext;
    private List<Message> mMessagesList;

    public MessagesAdapter(Context mContext, List<Message> mMessagesList) {
        this.mContext = mContext;
        this.mMessagesList = mMessagesList;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.message_list_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message = mMessagesList.get(position);
        holder.mInOutImageView.setImageResource(R.drawable.ic_email);
        holder.mSubjectTextView.setText(message.getSubject());
        holder.mTextTextView.setText(message.getText());
        holder.mDateTextView.setText(message.getDate());
    }

    @Override
    public int getItemCount() {
        return mMessagesList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        public ImageView mInOutImageView;
        public TextView mSubjectTextView, mTextTextView, mDateTextView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            mInOutImageView = itemView.findViewById(R.id.in_out_message_image_view);
            mSubjectTextView = itemView.findViewById(R.id.message_subject);
            mTextTextView = itemView.findViewById(R.id.message_text);
            mDateTextView = itemView.findViewById(R.id.message_date);
        }
    }
}
