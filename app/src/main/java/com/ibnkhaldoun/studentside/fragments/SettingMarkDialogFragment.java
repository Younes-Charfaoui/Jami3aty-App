package com.ibnkhaldoun.studentside.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

import com.ibnkhaldoun.studentside.R;


public class SettingMarkDialogFragment extends DialogFragment implements SeekBar.OnSeekBarChangeListener {

    public static final String KEY_EXAM = "exam";
    public static final String KEY_TD = "td";
    public static final String KEY_TP = "tp";
    private SeekBar tpSeek;
    private SeekBar tdSeek;
    private SeekBar examSeek;

    public static SettingMarkDialogFragment newInstanceFull(float course
            , float td, float tp) {

        Bundle args = new Bundle();
        args.putFloat(KEY_EXAM, course);
        args.putFloat(KEY_TD, td);
        args.putFloat(KEY_TP, tp);
        SettingMarkDialogFragment fragment = new SettingMarkDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static SettingMarkDialogFragment newInstanceSemi(float course
            , float tdOrTp) {

        Bundle args = new Bundle();
        args.putFloat(KEY_EXAM, course);
        args.putFloat(KEY_TD, tdOrTp);

        SettingMarkDialogFragment fragment = new SettingMarkDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static SettingMarkDialogFragment newInstanceExam(float course) {
        Bundle args = new Bundle();
        args.putFloat(KEY_EXAM, course);
        SettingMarkDialogFragment fragment = new SettingMarkDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View changingView = inflater.
                inflate(R.layout.changing_mark_fragment_layout, null);

        tpSeek = changingView.findViewById(R.id.tp_seek);
        tdSeek = changingView.findViewById(R.id.td_seek);
        examSeek = changingView.findViewById(R.id.exam_seek);



        tpSeek.setOnSeekBarChangeListener(this);
        examSeek.setOnSeekBarChangeListener(this);
        tdSeek.setOnSeekBarChangeListener(this);

        builder.setTitle(R.string.change_to_see)
                .setPositiveButton(android.R.string.yes, null)
                .setNegativeButton(android.R.string.no, null);

        builder.setView(changingView);
        return builder.create();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


}
