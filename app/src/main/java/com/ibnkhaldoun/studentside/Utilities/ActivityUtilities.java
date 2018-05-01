package com.ibnkhaldoun.studentside.Utilities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.IntDef;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ibnkhaldoun.studentside.R;
import com.ibnkhaldoun.studentside.database.DatabaseContract;

import static com.ibnkhaldoun.studentside.activities.MessageDetailActivity.PROFESSOR;
import static com.ibnkhaldoun.studentside.activities.MessageDetailActivity.STUDENT;


public final class ActivityUtilities {


    //this addMethod makes the bar transparent
    public static void changeStatusBarColorToTransparent(Window windows) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            windows.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            windows.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void changeStatusBarColor(Window windows, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            windows.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            windows.setStatusBarColor(color);
        }
    }

    public static int whoIsUsing(Context context) {
        if (new PreferencesManager(context, PreferencesManager.PROFESSOR).isLogin()) {
            return PROFESSOR;
        } else {
            return STUDENT;
        }
    }

    public static void logOut(Context context) {
        new PreferencesManager(context, PreferencesManager.STUDENT)
                .removePreference();
        new PreferencesManager(context, PreferencesManager.PROFESSOR)
                .removePreference();
        new PreferencesManager(context, PreferencesManager.SUBJECT)
                .removePreference();
        context.getContentResolver()
                .delete(DatabaseContract.ScheduleEntry.CONTENT_SCHEDULE_URI
                        , null, null);
        context.getContentResolver()
                .delete(DatabaseContract.SubjectEntry.CONTENT_SUBJECT_URI
                        , null, null);
        context.getContentResolver()
                .delete(DatabaseContract.MailEntry.CONTENT_MAIL_URI
                        , null, null);

    }

    public static View createNoteView(Context context, String shortName, String text, String name, String date, ViewGroup parent) {
        View noteView = LayoutInflater.from(context).inflate(R.layout.note_of_display_list_item, parent, false);

        TextView noteShortNameTextView = noteView.findViewById(R.id.note_of_display_person_short_name_text_view);
        noteShortNameTextView.setText(shortName);

        GradientDrawable circleNoteShortName = (GradientDrawable) noteShortNameTextView.getBackground();
        circleNoteShortName.setColor(Utilities.getCircleColor(context));

        TextView noteNamePersonTextView = noteView.findViewById(R.id.note_of_display_person_name_text_view);
        noteNamePersonTextView.setText(name);

        TextView noteTextTextView = noteView.findViewById(R.id.note_of_display_note);
        noteTextTextView.setText(text);

        TextView noteDateTextView = noteView.findViewById(R.id.note_of_display_date);
        noteDateTextView.setText(date);

        return noteView;
    }

}
