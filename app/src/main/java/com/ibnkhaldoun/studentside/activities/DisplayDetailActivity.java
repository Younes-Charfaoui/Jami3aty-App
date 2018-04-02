package com.ibnkhaldoun.studentside.activities;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.ActivityUtilities;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.models.Comment;
import com.ibnkhaldoun.studentside.models.Display;


public class DisplayDetailActivity extends AppCompatActivity {

    public static final String SENDER = "sender";
    public static final String DATA = "data";

    private LinearLayout notesLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Display display = getIntent().getParcelableExtra("Key");

        TextView professorShortNameTextView = findViewById(R.id.display_detail_professor_short_name_text_view);
        professorShortNameTextView.setText(display.getProfessor().getShortName());

        GradientDrawable circle = (GradientDrawable) professorShortNameTextView.getBackground();
        circle.setColor(Utilities.getCircleColor(display.getProfessor().getShortName().charAt(0), this));

        TextView professorNameTextView = findViewById(R.id.display_detail_professor_name_text_view);
        professorNameTextView.setText(display.getProfessor().getFullName());

        TextView dateTextView = findViewById(R.id.display_detail_date_and_time_text_view);
        dateTextView.setText(display.getDate());

        TextView textTextView = findViewById(R.id.display_detail_text_text_view);
        textTextView.setText(display.getText());

        TextView numberOfNoteTextView = findViewById(R.id.display_detail_number_of_notes);
        if (display.getCommentList().size() != 0) {
            numberOfNoteTextView.setText(display.getCommentList().size() + " note");
        } else {
            numberOfNoteTextView.setText("No note");
        }
        View separatorView = findViewById(R.id.display_detail_notes_separator);

        notesLinearLayout = findViewById(R.id.display_detail_notes);
        if (display.getCommentList().size() != 0) {
            separatorView.setVisibility(View.VISIBLE);
            for (int i = 0; i < display.getCommentList().size(); i++) {
                Comment comment = display.getCommentList().get(i);
                notesLinearLayout.addView(
                        ActivityUtilities.createNoteView(this, Utilities.getShortName(comment.getCommenter()),
                                comment.getComment(),
                                comment.getCommenter(),
                                comment.getDate(), notesLinearLayout));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}