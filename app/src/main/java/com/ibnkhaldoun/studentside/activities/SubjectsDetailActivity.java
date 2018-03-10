package com.ibnkhaldoun.studentside.activities;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.ActivityUtilities;
import com.ibnkhaldoun.studentside.Utilities.Utils;
import com.ibnkhaldoun.studentside.models.Subject;


public class SubjectsDetailActivity extends AppCompatActivity {

    public static final String SUBJECT_KEY = "subjectKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Subject subject = getIntent().getParcelableExtra(SUBJECT_KEY);
        ActivityUtilities.changeStatusBarColor(getWindow(),
                Utils.getCircleColor(subject.getTitle().charAt(0), this));
        
        TextView subjectTitleTv = findViewById(R.id.subject_title);
        subjectTitleTv.setText(subject.getTitle());

        TextView coefficientTv = findViewById(R.id.subject_coefficient);
        coefficientTv.setText(subject.getCoefficient());
        GradientDrawable backCircle = (GradientDrawable) coefficientTv.getBackground();
        backCircle.setColor(Utils.getCircleColor(subject.getTitle().charAt(0), this));

        TextView subjectUtility = findViewById(R.id.subject_utility);
        subjectUtility.setText(subject.getSummary());

        TextView courseProfessorTv = findViewById(R.id.subject_course_professor);
        courseProfessorTv.setText(subject.getCourseProfessor().getFullName());

        if (subject.itHasTd()) {
            findViewById(R.id.subject_td_professor_title).setVisibility(View.VISIBLE);
            TextView tdProfessorTv = findViewById(R.id.subject_td_professor);
            tdProfessorTv.setText(subject.getTdProfessor().getFullName());
        }

        if (subject.itHasTp()) {
            findViewById(R.id.subject_tp_professor_title).setVisibility(View.VISIBLE);
            TextView tpProfessorTv = findViewById(R.id.subject_tp_professor);
            tpProfessorTv.setText(subject.getTpProfessor().getFullName());
        }

        LinearLayout scheduleLinearLayout = findViewById(R.id.subject_schedule_linear_layout);
        for (int i = 0; i < subject.getScheduleList().size(); i++) {
            TextView schedule = new TextView(this);
            schedule.setText(subject.getScheduleList().get(i).toString());
            scheduleLinearLayout.addView(schedule);
        }

        TextView contentTv = findViewById(R.id.subject_overview_text_view);
        contentTv.setText(subject.getContent());
    }

}
