package com.ibnkhaldoun.studentside.database;

import android.net.Uri;
import android.provider.BaseColumns;


public class DatabaseContract implements BaseColumns {

    public static final String AUTHORITY = "com.ibnkhaldoun.studentside";

    public static final String PATH_NOTES = NoteEntry.TABLE_NAME;
    public static final String PATH_SAVED = SavedEntry.TABLE_NAME;
    public static final String PATH_DISPLAY = DisplayEntry.TABLE_NAME;
    public static final String PATH_MARK = MarkEntry.TABLE_NAME;
    public static final String PATH_SCHEDULE = ScheduleEntry.TABLE_NAME;
    public static final String PATH_NOTIFICATION = NotificationEntry.TABLE_NAME;
    public static final String PATH_MAIL = MailEntry.TABLE_NAME;
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    //todo the Path of the tables

    public static final class NoteEntry {
        //todo list of the table constant
        public static final Uri CONTENT_NOTE_URI = BASE_URI.buildUpon().appendPath(PATH_NOTES).build();

        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NOTE_TEXT = "note";
        public static final String COLUMN_NOTE_ID = _ID;
        public static final String COLUMN_NOTE_SUBJECT = "subject";
        public static final String[] ALL_COLUMN =
                {COLUMN_NOTE_ID, COLUMN_NOTE_TEXT, COLUMN_NOTE_SUBJECT};
    }

    public class SavedEntry {
        public static final String TABLE_NAME = "saved";
        //todo list of the table constant
    }

    public class DisplayEntry {
        public static final String TABLE_NAME = "displays";
        //todo list of the table constant
    }

    public class MailEntry {
        public static final String TABLE_NAME = "mails";
        //todo list of the table constant
    }

    public class NotificationEntry {
        public static final String TABLE_NAME = "notifications";
        //todo list of the table constant
    }

    public class MarkEntry {
        public static final String TABLE_NAME = "marks";
        //todo list of the table constant
    }

    public class ScheduleEntry {
        public static final String TABLE_NAME = "schedules";
        //todo list of the table constant
    }
}
