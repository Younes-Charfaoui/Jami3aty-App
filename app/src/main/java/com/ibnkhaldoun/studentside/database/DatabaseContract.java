package com.ibnkhaldoun.studentside.database;

import android.net.Uri;
import android.provider.BaseColumns;


public class DatabaseContract implements BaseColumns {

    public static final String AUTHORITY = "com.ibnkhaldoun.studentside";

    public static final String PATH_NOTES = NoteEntry.TABLE_NAME;
    public static final String PATH_SAVED = SavedEntry.TABLE_NAME;
    public static final String PATH_NOTE_DISPLAY = NoteOfDisplaysEntry.TABLE_NAME;
    public static final String PATH_DISPLAY = DisplayEntry.TABLE_NAME;
    public static final String PATH_MARK = MarkEntry.TABLE_NAME;
    public static final String PATH_SCHEDULE = ScheduleEntry.TABLE_NAME;
    public static final String PATH_NOTIFICATION = NotificationEntry.TABLE_NAME;
    public static final String PATH_MAIL = MailEntry.TABLE_NAME;
    public static final String PATH_SUBJECT = SubjectEntry.TABLE_NAME;
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    //todo the Path of the tables

    public static final class NoteEntry {
        public static final Uri CONTENT_NOTE_URI = BASE_URI.buildUpon().appendPath(PATH_NOTES).build();
        public static final String COLUMN_NOTE_TEXT = "note";
        public static final String COLUMN_NOTE_SUBJECT = "subject";
        public static final String COLUMN_NOTE_ID = _ID;
        public static final String[] ALL_COLUMN =
                {COLUMN_NOTE_ID, COLUMN_NOTE_TEXT, COLUMN_NOTE_SUBJECT};
        static final String TABLE_NAME = "notes";
    }

    public static final class SavedEntry {
        public static final Uri CONTENT_SAVED_URI = BASE_URI.buildUpon().appendPath(PATH_SAVED).build();
        public static final String COLUMN_NAME = "professor_name";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_DISPLAY_TEXT = "display";
        public static final String COLUMN_ID = _ID;
        static final String TABLE_NAME = "saved";
    }

    public static final class NoteOfDisplaysEntry {
        public static final Uri CONTENT_NOTE_DISPLAY_URI = BASE_URI.buildUpon().appendPath(PATH_NOTE_DISPLAY).build();
        public static final String TABLE_NAME = "note_of_display";
        public static final String COLUMN_ID = _ID;
        public static final String COLUMN_ID_DISPLAY = "_ID_display";
        public static final String COLUMN_TEXT = "note_text";
        public static final String COLUMN_NAME = "name";
    }

    public static final class SubjectEntry {
        public static final Uri CONTENT_SUBJECT_URI = BASE_URI.buildUpon().appendPath(PATH_SUBJECT).build();
        public static final String TABLE_NAME = "subjects";
        public static final String COLUMN_ID = _ID;
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_SHORT_TITLE = "shortTitle";
        public static final String COLUMN_COEFFICIENT = "coefficient";
        public static final String COLUMN_CREDIT = "credit";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_TABLE_CONTENT = "content";
        public static final String COLUMN_LEVEL = "level";
        public static final String COLUMN_UNITY_TYPE = "unityType";
        public static final String COLUMN_TD_PROFESSOR = "tdProfessor";
        public static final String COLUMN_TP_PROFESSOR = "tpProfessor";
        public static final String COLUMN_COURSE_PROFESSOR = "courseProfessor";
    }

    public static class MarkEntry {
        public static final Uri CONTENT_MARK_URI = BASE_URI.buildUpon()
                .appendEncodedPath(PATH_MARK)
                .build();
        public static final String TABLE_NAME = "marks";
        public static final String COLUMN_ID = _ID;
        public static final String COLUMN_TITLE_SUBJECT = "subjectTitle";
        public static final String COLUMN_SHORT_TITLE = "subjectShortTitle";
        public static final String COLUMN_TD_MARK = "tdMark";
        public static final String COLUMN_TP_MARK = "tpMark";
        public static final String COLUMN_EXAM_MARK = "examMark";
        public static final String COLUMN_SUBJECT_ID = "subjectId";
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

    public class ScheduleEntry {
        public static final String TABLE_NAME = "schedules";
        //todo list of the table constant
    }
}
