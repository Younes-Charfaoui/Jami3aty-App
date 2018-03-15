package com.ibnkhaldoun.studentside.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.adapters.SubjectProfessorDialogAdapter;
import com.ibnkhaldoun.studentside.interfaces.ProfessorDialogInterface;
import com.ibnkhaldoun.studentside.models.Professor;

import java.util.ArrayList;
import java.util.List;


public class ProfessorListFragment extends DialogFragment {
    public ProfessorDialogInterface mInterface;

    public static ProfessorListFragment newInstance(List<Professor> list) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("Key", (ArrayList<? extends Parcelable>) list);
        ProfessorListFragment fragment = new ProfessorListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInterface = (ProfessorDialogInterface) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        assert getActivity() != null && getArguments() != null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        ArrayList<Professor> professorList = getArguments().getParcelableArrayList("Key");
        builder.setTitle("Choose a Professor");
        ArrayList<String> list = new ArrayList<>();
        for (Professor professor : professorList) {
            list.add(professor.getFullName());
        }
        assert getContext() != null;
        SubjectProfessorDialogAdapter adapter = new SubjectProfessorDialogAdapter(getContext(), R
                .layout.subject_professor_dialog_list_item, list);
        builder.setAdapter(adapter, (dialog, which) ->
                mInterface.onProfessorChosen(list.get(which),
                        professorList.get(which).getId()));
        return builder.create();
    }
}
