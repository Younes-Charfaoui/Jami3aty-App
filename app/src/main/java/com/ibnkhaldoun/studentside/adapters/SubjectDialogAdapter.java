package com.ibnkhaldoun.studentside.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.Utils;

import java.util.List;


public class SubjectDialogAdapter extends ArrayAdapter<String> {

    private List<String> mSubjectList;

    public SubjectDialogAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        mSubjectList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.subject_dialog_list_item, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.subject_dialog_title);
        textView.setText(mSubjectList.get(position));
        View circleView = convertView.findViewById(R.id.subject_dialog_circle);
        circleView.setBackgroundColor(Utils.getCircleColor(mSubjectList.get(position).charAt(0), getContext()));
        return convertView;
    }


}
