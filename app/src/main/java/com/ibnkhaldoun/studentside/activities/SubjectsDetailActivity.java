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
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.models.Subject;

/**
 * @definition this activity will show the detail of the subject
 * to let the student to take some information about the subject
 * and do some basic search of i content.
 */
public class SubjectsDetailActivity extends AppCompatActivity {

    // the key with which we had take out the subject from the Intent.
    public static final String SUBJECT_KEY = "subjectKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //taking out the subject to display it's information on the screen.
        Subject subject = getIntent().getParcelableExtra(SUBJECT_KEY);

        // setting up the title of the tool bar and the Action bar.
        toolbar.setTitle(subject.getShortTitle());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(subject.getShortTitle());
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        //coloring the circle of the subject with a color
        int colorOfSubject = Utilities.getCircleColor(subject.getTitle().charAt(0), this);
        ActivityUtilities.changeStatusBarColor(getWindow(), colorOfSubject);

        //getting reference to the text view and setting up it's color and it text
        ((TextView) findViewById(R.id.subject_title_title)).setTextColor(colorOfSubject);
        ((TextView) findViewById(R.id.subject_title)).setText(subject.getTitle());

        //getting reference to the text view and setting up it's text
        ((TextView) findViewById(R.id.subject_coefficient_title)).setTextColor(colorOfSubject);

        //getting reference to the text view and setting up it's color and it text
        TextView coefficientTv = findViewById(R.id.subject_coefficient);
        coefficientTv.setText(subject.getCoefficient());
        GradientDrawable backCircle = (GradientDrawable) coefficientTv.getBackground();
        backCircle.setColor(colorOfSubject);

        //getting reference to the text view and setting up it's color and it text
        ((TextView) findViewById(R.id.subject_utility_title)).setTextColor(colorOfSubject);
        ((TextView) findViewById(R.id.subject_utility)).setText(subject.getSummary());

        //getting reference to the text view and setting up it's color and it text
        ((TextView) findViewById(R.id.subject_course_professor_title)).setTextColor(colorOfSubject);
        ((TextView) findViewById(R.id.subject_course_professor)).setText(subject.getCourseProfessor());

        //see if the subject has a td so we must display the td text view
        if (subject.itHasTd()) {
            findViewById(R.id.subject_td_professor_title).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.subject_td_professor)).setText(subject.getTdProfessor());
        }

        //see if the subject has a tp so we must display the td text view
        if (subject.itHasTp()) {
            findViewById(R.id.subject_tp_professor_title).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.subject_tp_professor)).setText(subject.getTpProfessor());
        }

        //setting up the overview of the subject
        ((TextView) findViewById(R.id.subject_overview)).setText(subject.getContent());
    }
}