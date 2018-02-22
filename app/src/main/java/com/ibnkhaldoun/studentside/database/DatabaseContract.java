package com.ibnkhaldoun.studentside.database;

import android.net.Uri;
import android.provider.BaseColumns;


public class DatabaseContract implements BaseColumns {

    private static final String AUTHORITY = "com.ibnkhaldoun.studentside";
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    //todo the Path of the tables

    public class SavedEntries {
        //todo list of the table constant
    }

    public class NoteEntries {
        //todo list of the table constant
    }

    public class DisplayEntries {
        //todo list of the table constant
    }

    public class MailEntries {
        //todo list of the table constant
    }

    public class NotificationEntries {
        //todo list of the table constant
    }

    public class MarkEntries {
        //todo list of the table constant
    }

    public class ScheduleEntries {
        //todo list of the table constant
    }
}
