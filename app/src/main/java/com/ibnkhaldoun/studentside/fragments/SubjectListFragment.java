package com.ibnkhaldoun.studentside.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.SubjectProfessorDialogAdapter;
import com.ibnkhaldoun.studentside.interfaces.SubjectDialogInterface;

import java.util.ArrayList;


public class SubjectListFragment extends DialogFragment {

    public SubjectDialogInterface mInterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInterface = (SubjectDialogInterface) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        assert getActivity() != null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Choose a Subject");
        ArrayList<String> list = new ArrayList<>();
        list.add("Software Engerning");
        list.add("Compilation");
        list.add("Linear programming");
        list.add("Logical Programming");
        list.add("Operating System");
        list.add("Probability");
        list.add("IHM");
        list.add("English");
        assert getContext() != null;
        SubjectProfessorDialogAdapter adapter = new SubjectProfessorDialogAdapter(getContext(), R
                .layout.subject_professor_dialog_list_item, list);
        builder.setAdapter(adapter, (dialog, which) -> mInterface.onSubjectChosen(list.get(which)));
        return builder.create();
    }
}
