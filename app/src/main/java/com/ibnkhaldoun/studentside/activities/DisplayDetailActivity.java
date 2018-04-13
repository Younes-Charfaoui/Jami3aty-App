package com.ibnkhaldoun.studentside.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.Utilities.PreferencesManager;
import com.ibnkhaldoun.studentside.Utilities.Utilities;
import com.ibnkhaldoun.studentside.asyncTask.ResponseAsyncTask;
import com.ibnkhaldoun.studentside.fragments.NoteOfDisplayDialog;
import com.ibnkhaldoun.studentside.models.Display;
import com.ibnkhaldoun.studentside.networking.models.RequestPackage;
import com.ibnkhaldoun.studentside.networking.models.Response;
import com.ibnkhaldoun.studentside.networking.utilities.NetworkUtilities;
import com.ibnkhaldoun.studentside.providers.EndPointsProvider;
import com.ibnkhaldoun.studentside.providers.KeyDataProvider;

import static com.ibnkhaldoun.studentside.Utilities.PreferencesManager.STUDENT;
import static com.ibnkhaldoun.studentside.networking.models.RequestPackage.POST;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_POST_ID2;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_SAVE_ACTION;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_ID;
import static com.ibnkhaldoun.studentside.services.LoadDataService.COMMENTS_ACTION;


public class DisplayDetailActivity extends AppCompatActivity implements NoteOfDisplayDialog.INoteOfDisplay, ResponseAsyncTask.IResponseListener {

    public static final String SENDER = "sender";
    public static final String DATA = "data";

    private LinearLayout notesLinearLayout;

    private BroadcastReceiver mCommentsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("Detail", "onReceive: I had receive !");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Display display = getIntent().getParcelableExtra(DATA);

        TextView professorShortNameTextView = findViewById(R.id.display_detail_professor_short_name_text_view);
        professorShortNameTextView.setText(Utilities.getProfessorShortName(display.getProfessor()));

        GradientDrawable circle = (GradientDrawable) professorShortNameTextView.getBackground();
        circle.setColor(Utilities.getCircleColor(this));

        TextView professorNameTextView = findViewById(R.id.display_detail_professor_name_text_view);
        professorNameTextView.setText(display.getProfessor());

        TextView dateTextView = findViewById(R.id.display_detail_date_and_time_text_view);
        dateTextView.setText(display.getDate());

        TextView textTextView = findViewById(R.id.display_detail_text_text_view);
        textTextView.setText(display.getText());


        View separatorView = findViewById(R.id.display_detail_notes_separator);

        if (display.isSaved())
            findViewById(R.id.display_detail_save_button).setEnabled(false);
        else
            findViewById(R.id.display_detail_save_button).setEnabled(true);

        findViewById(R.id.display_detail_save_button).setOnClickListener(v -> {
            if (NetworkUtilities.isConnected(this)) {
                ResponseAsyncTask task = new ResponseAsyncTask(response -> {
                    if (response.getStatus() != Response.RESPONSE_SUCCESS) {
                        Toast.makeText(this, "Try later , problem with connecting", Toast.LENGTH_SHORT).show();
                    } else {
                        v.setEnabled(false);
                    }
                });
                RequestPackage request = new RequestPackage.Builder().addEndPoint(EndPointsProvider.getUnsaveEndpoint())
                        .addMethod(POST)
                        .addParams(JSON_STUDENT_ID,
                                new PreferencesManager(this, PreferencesManager.STUDENT).getId())
                        .addParams(JSON_POST_ID2, String.valueOf(display.getId()))
                        .addParams(JSON_SAVE_ACTION, String.valueOf(1))
                        .create();
                task.execute(request);
            }
        });

        findViewById(R.id.display_detail_note_button).setOnClickListener(v -> {
            if (NetworkUtilities.isConnected(this)) {
                NoteOfDisplayDialog dialog =
                        NoteOfDisplayDialog.newInstance(display.getId());
                dialog.show(getSupportFragmentManager(), "Tag");
            } else {
                Toast.makeText(this, R.string.no_internet_connection_string,
                        Toast.LENGTH_SHORT).show();
            }
        });


        notesLinearLayout = findViewById(R.id.display_detail_notes);

        LocalBroadcastManager.getInstance(this).registerReceiver(mCommentsReceiver, new IntentFilter(COMMENTS_ACTION));
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

    @Override
    public void OnFinishNoting(String note, long id) {
        Toast.makeText(this, note + " " + id, Toast.LENGTH_SHORT).show();
        RequestPackage request = new RequestPackage.Builder()
                .addEndPoint(EndPointsProvider.getAddCommentsEndpoint())
                .addMethod(POST)
                .addParams(KeyDataProvider.KEY_NOTE, note)
                .addParams(KeyDataProvider.JSON_POST_ID, String.valueOf(id))
                .addParams(KeyDataProvider.JSON_STUDENT_ID, new PreferencesManager(this, STUDENT).getId())
                .create();
        ResponseAsyncTask task = new ResponseAsyncTask(this);
        task.execute(request);
    }

    @Override
    public void onGetResponse(Response response) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mCommentsReceiver);
    }
}