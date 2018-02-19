package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.activities.MessagesActivity;
import com.ibnkhaldoun.studentside.models.Mail;

import java.util.List;

public class MailAdapter extends RecyclerView.Adapter<MailAdapter.MailViewHolder> {

    private Context mContext;
    private List<Mail> mDataList;

    public MailAdapter(Context mContext, List<Mail> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
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
        Mail mail = mDataList.get(position);
        holder.mProfessorShortNameTextView.setText(mail.getProfessor().getShortName());
        holder.mProfessorNameTextView.setText(mail.getProfessor().getFullName());
        holder.mSubjectTextView.setText(mail.getMessages().get(mail.getMessages().size() - 1).getSubject());
        holder.mDateTextView.setText(mail.getMessages().get(mail.getMessages().size() - 1).getDate());
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MailViewHolder extends RecyclerView.ViewHolder {

        private TextView mProfessorShortNameTextView,
                mProfessorNameTextView, mSubjectTextView,
                mDateTextView;

        MailViewHolder(View itemView) {
            super(itemView);
            mProfessorShortNameTextView = itemView.findViewById(R.id.mail_professor_name_short_text_view);
            mProfessorNameTextView = itemView.findViewById(R.id.mail_professor_name_text_view);
            mSubjectTextView = itemView.findViewById(R.id.mail_last_subject_text_view);
            mDateTextView = itemView.findViewById(R.id.mail_date_of_mail_text_view);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, MessagesActivity.class);
                intent.putExtra("Key", mDataList.get(getAdapterPosition()));
                mContext.startActivity(intent);
            });
        }
    }
}
