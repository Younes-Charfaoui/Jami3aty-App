package com.ibnkhaldoun.studentside;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.Utilities.Utils;
import com.ibnkhaldoun.studentside.models.Display;


public class DisplayDetailActivity extends AppCompatActivity {

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
        circle.setColor(Utils.getCircleColor(display.getProfessor().getShortName().charAt(0), this));

        TextView professorNameTextView = findViewById(R.id.display_detail_professor_name_text_view);
        professorNameTextView.setText(display.getProfessor().getFullName());

        TextView dateTextView = findViewById(R.id.display_detail_date_and_time_text_view);
        dateTextView.setText(display.getDate());

        TextView textTextView = findViewById(R.id.display_detail_text_text_view);
        textTextView.setText(display.getText());

        TextView numberOfNoteTextView = findViewById(R.id.display_detail_number_of_notes);
        numberOfNoteTextView.setText("1 note");

        LinearLayout notesLinearLayout = findViewById(R.id.display_detail_notes);
        View noteView = LayoutInflater.from(this).inflate(R.layout.note_of_display_list_item, notesLinearLayout, false);
        notesLinearLayout.addView(noteView);
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
