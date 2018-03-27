package com.ibnkhaldoun.studentside.Utilities;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.ibnkhaldoun.studentside.database.DatabaseContract;


public final class ActivityUtilities {

    //this method makes the bar transparent
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

    public static void logOut(Context context) {
        new PreferencesManager(context, PreferencesManager.STUDENT)
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
}
