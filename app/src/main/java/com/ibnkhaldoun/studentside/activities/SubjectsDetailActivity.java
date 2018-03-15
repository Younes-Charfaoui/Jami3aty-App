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

        toolbar.setTitle(subject.getShortTitle());
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(subject.getShortTitle());
        getSupportActionBar().setHomeButtonEnabled(true);

        int colorOfSubject = Utils.getCircleColor(subject.getTitle().charAt(0), this);
        ActivityUtilities.changeStatusBarColor(getWindow(), colorOfSubject);

        ((TextView) findViewById(R.id.subject_title_title)).setTextColor(colorOfSubject);
        ((TextView) findViewById(R.id.subject_title)).setText(subject.getTitle());

        ((TextView) findViewById(R.id.subject_coefficient_title)).setTextColor(colorOfSubject);

        TextView coefficientTv = findViewById(R.id.subject_coefficient);
        coefficientTv.setText(subject.getCoefficient());
        GradientDrawable backCircle = (GradientDrawable) coefficientTv.getBackground();
        backCircle.setColor(colorOfSubject);

        ((TextView) findViewById(R.id.subject_utility_title)).setTextColor(colorOfSubject);
        ((TextView) findViewById(R.id.subject_utility)).setText(subject.getSummary());

        ((TextView) findViewById(R.id.subject_course_professor_title)).setTextColor(colorOfSubject);
        ((TextView) findViewById(R.id.subject_course_professor)).setText(subject.getCourseProfessor());

        if (subject.itHasTd()) {
            findViewById(R.id.subject_td_professor_title).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.subject_td_professor)).setText(subject.getTdProfessor());
        }

        if (subject.itHasTp()) {
            findViewById(R.id.subject_tp_professor_title).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.subject_tp_professor)).setText(subject.getTpProfessor());
        }

        LinearLayout scheduleLinearLayout = findViewById(R.id.subject_schedule_linear_layout);
        for (int i = 0; i < subject.getScheduleList().size(); i++) {
            TextView schedule = new TextView(this);
            schedule.setText(subject.getScheduleList().get(i).toString());
            scheduleLinearLayout.addView(schedule);
        }

        ((TextView) findViewById(R.id.subject_overview)).setText(subject.getContent());
    }

}
