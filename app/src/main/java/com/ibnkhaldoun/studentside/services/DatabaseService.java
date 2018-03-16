package com.ibnkhaldoun.studentside.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.enums.UnityTypes;
import com.ibnkhaldoun.studentside.models.Mark;
import com.ibnkhaldoun.studentside.models.Subject;

import java.util.ArrayList;

import static com.ibnkhaldoun.studentside.services.LoadDataService.MARK_TYPE;
import static com.ibnkhaldoun.studentside.services.LoadDataService.SUBJECT_TYPE;

/**
 * @definition this service will do the operation of
 * adding some data in the database , like that we are doing this
 * operation in the background thread.
 */

public class DatabaseService extends IntentService {

    public static final String KEY_CONTENT_DATA = "keyData";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     * <p>
     * Used to name the worker thread, important only for debugging.
     */
    public DatabaseService() {
        super("Database Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        assert intent != null;
        int type = intent.getIntExtra(LoadDataService.KEY_ACTION, -1);
        switch (type) {
            case SUBJECT_TYPE:
                insertSubject(intent.getParcelableArrayListExtra(KEY_CONTENT_DATA));
                break;
            case MARK_TYPE:
                int data = insertMarks(intent.getParcelableArrayListExtra(KEY_CONTENT_DATA));
                break;
        }
    }

    private int insertMarks(ArrayList<Mark> markList) {
        if (markList.size() != 0) {
            getContentResolver().delete(DatabaseContract.MarkEntry.CONTENT_MARK_URI,
                    null, null);
            return getContentResolver().bulkInsert(DatabaseContract
                            .SubjectEntry.CONTENT_SUBJECT_URI,
                    getMarkContentValues(markList));
        }
        return 0;
    }

    private ContentValues[] getMarkContentValues(ArrayList<Mark> list) {
        ContentValues[] values = new ContentValues[list.size()];
        for (int i = 0; i < list.size(); i++) {

            values[i].put(DatabaseContract.MarkEntry.COLUMN_TITLE_SUBJECT,
                    list.get(i).getSubjectName());
            values[i].put(DatabaseContract.MarkEntry.COLUMN_SHORT_TITLE,
                    list.get(i).getShortSubjectName());
            values[i].put(DatabaseContract.MarkEntry.COLUMN_TD_MARK,
                    list.get(i).getTD());
            values[i].put(DatabaseContract.MarkEntry.COLUMN_TP_MARK,
                    list.get(i).getTP());
            values[i].put(DatabaseContract.MarkEntry.COLUMN_EXAM_MARK,
                    list.get(i).getExam());

            values[i].put(DatabaseContract.MarkEntry.COLUMN_SUBJECT_ID,
                    list.get(i).getSubjectId());
        }
        return values;

    }

    private void insertSubject(ArrayList<Subject> list) {

        if (list.size() != 0) {
            getContentResolver().delete(DatabaseContract.SubjectEntry.CONTENT_SUBJECT_URI,
                    null, null);
            getContentResolver().bulkInsert(DatabaseContract
                            .SubjectEntry.CONTENT_SUBJECT_URI,
                    getSubjectContentValues(list));
        }
    }

    private ContentValues[] getSubjectContentValues(ArrayList<Subject> list) {
        ContentValues[] values = new ContentValues[list.size()];
        for (int i = 0; i < list.size(); i++) {
            values[i].put(DatabaseContract.SubjectEntry.COLUMN_ID,
                    list.get(i).getId());
            values[i].put(DatabaseContract.SubjectEntry.COLUMN_COEFFICIENT,
                    list.get(i).getCoefficient());
            values[i].put(DatabaseContract.SubjectEntry.COLUMN_LEVEL,
                    list.get(i).getLevel());
            values[i].put(DatabaseContract.SubjectEntry.COLUMN_CREDIT,
                    list.get(i).getCredit());
            values[i].put(DatabaseContract.SubjectEntry.COLUMN_SHORT_TITLE,
                    list.get(i).getShortTitle());
            values[i].put(DatabaseContract.SubjectEntry.COLUMN_TITLE,
                    list.get(i).getTitle());
            values[i].put(DatabaseContract.SubjectEntry.COLUMN_TD_PROFESSOR,
                    list.get(i).getTdProfessor());
            values[i].put(DatabaseContract.SubjectEntry.COLUMN_TP_PROFESSOR,
                    list.get(i).getTpProfessor());
            values[i].put(DatabaseContract.SubjectEntry.COLUMN_COURSE_PROFESSOR,
                    list.get(i).getCourseProfessor());
            values[i].put(DatabaseContract.SubjectEntry.COLUMN_SUMMARY,
                    list.get(i).getSummary());
            values[i].put(DatabaseContract.SubjectEntry.COLUMN_TABLE_CONTENT,
                    list.get(i).getContent());
            values[i].put(DatabaseContract.SubjectEntry.COLUMN_UNITY_TYPE,
                    UnityTypes.getUnitType(list.get(i).getUnityTypes()));
        }
        return values;
    }
}
