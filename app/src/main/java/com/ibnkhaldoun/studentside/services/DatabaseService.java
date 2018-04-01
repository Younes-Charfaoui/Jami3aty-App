package com.ibnkhaldoun.studentside.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ibnkhaldoun.studentside.database.DatabaseContract;
import com.ibnkhaldoun.studentside.models.Mark;
import com.ibnkhaldoun.studentside.models.Saved;
import com.ibnkhaldoun.studentside.models.Schedule;
import com.ibnkhaldoun.studentside.models.Subject;
import com.ibnkhaldoun.studentside.networking.utilities.JsonUtilities;
import com.ibnkhaldoun.studentside.providers.KeyDataProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.ibnkhaldoun.studentside.services.LoadDataService.MARK_TYPE;
import static com.ibnkhaldoun.studentside.services.LoadDataService.SAVED_TYPE;
import static com.ibnkhaldoun.studentside.services.LoadDataService.SCHEDULE_TYPE;
import static com.ibnkhaldoun.studentside.services.LoadDataService.SUBJECT_TYPE;

/**
 * @definition this service will do the operation of
 * adding some data in the database , like that we are doing this
 * operation in the background thread.
 */

public class DatabaseService extends IntentService {

    public static final String KEY_CONTENT_DATA = "keyData";
    private static final String TAG = "databaseS";

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
            case SAVED_TYPE:
                insertSaved(intent.getParcelableArrayListExtra(KEY_CONTENT_DATA));
                break;
            case SCHEDULE_TYPE:
                insertSchedules(intent.getStringExtra(KEY_CONTENT_DATA));
                break;
        }
    }

    private void insertSchedules(String stringExtra) {
        try {
            ArrayList<Schedule> schedules = Schedule.
                    getScheduleList(JsonUtilities.getSchedulesList(stringExtra));
            int group = new JSONObject(stringExtra).getInt(KeyDataProvider.JSON_STUDENT_GROUP);
            int level = new JSONObject(stringExtra).getInt(KeyDataProvider.JSON_STUDENT_LEVEL);
            int section = new JSONObject(stringExtra).getInt(KeyDataProvider.JSON_STUDENT_SECTION);
            Uri pathToSchedule = DatabaseContract.ScheduleEntry.CONTENT_SCHEDULE_URI.
                    buildUpon()
                    .appendPath(String.valueOf(level))
                    .appendPath(String.valueOf(section))
                    .appendPath(String.valueOf(group))
                    .build();
            Log.i(TAG, "insertSchedules: " + pathToSchedule.toString());
            int data = getContentResolver().delete(pathToSchedule,
                    null, null);
            Log.i(TAG, "insertSchedules: the removed data was " + data);
            int dataIn = getContentResolver().bulkInsert(DatabaseContract.ScheduleEntry.CONTENT_SCHEDULE_URI,
                    getScheduleValues(schedules));
            Log.i(TAG, "insertSchedules: the inserted data was " + dataIn);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ContentValues[] getScheduleValues(ArrayList<Schedule> schedules) {
        ArrayList<ContentValues> values = new ArrayList<>();

        for (int i = 0; i < schedules.size(); i++) {
            Schedule schedule = schedules.get(i);
            for (int j = 0; j < schedule.getScheduleItemList().size(); j++) {
                ContentValues value = new ContentValues();

                value.put(DatabaseContract.ScheduleEntry.COLUMN_DAY,
                        schedule.getDayOfSchedule());
                value.put(DatabaseContract.ScheduleEntry.COLUMN_GROUP,
                        schedule.getGroup());
                value.put(DatabaseContract.ScheduleEntry.COLUMN_SECTION,
                        schedule.getSection());
                value.put(DatabaseContract.ScheduleEntry.COLUMN_LEVEL,
                        schedule.getLevel());
                value.put(DatabaseContract.ScheduleEntry.COLUMN_HOUR,
                        schedule.getScheduleItemList().get(j).getTime());
                value.put(DatabaseContract.ScheduleEntry.COLUMN_PLACE,
                        schedule.getScheduleItemList().get(j).getLocation());
                value.put(DatabaseContract.ScheduleEntry.COLUMN_PROFESSOR,
                        schedule.getScheduleItemList().get(j).getProfessor());
                value.put(DatabaseContract.ScheduleEntry.COLUMN_SUBJECT,
                        schedule.getScheduleItemList().get(j).getSubject());
                value.put(DatabaseContract.ScheduleEntry.COLUMN_TYPE,
                        schedule.getScheduleItemList().get(j).getType());
                values.add(value);
            }

        }

        return values.toArray(new ContentValues[values.size()]);
    }

    private void insertSaved(ArrayList<Saved> savedList) {
        Log.i(TAG, "insertSaved: the list was " + savedList.size());
        getContentResolver().delete(DatabaseContract.SavedEntry.CONTENT_SAVED_URI,
                null, null);
        int data = getContentResolver().bulkInsert(DatabaseContract.SavedEntry.CONTENT_SAVED_URI,
                getSavedContentValue(savedList));

        Log.i(TAG, "insertSaved: the data was " + data);
    }

    private ContentValues[] getSavedContentValue(ArrayList<Saved> savedList) {
        ContentValues[] values = new ContentValues[savedList.size()];
        for (int i = 0; i < savedList.size(); i++) {
            values[i] = new ContentValues();
            values[i].put(DatabaseContract.SavedEntry.COLUMN_ID, savedList.get(i).getId());
            values[i].put(DatabaseContract.SavedEntry.COLUMN_DATE, savedList.get(i).getDate());
            values[i].put(DatabaseContract.SavedEntry.COLUMN_NAME, savedList.get(i).getProfessor());
            values[i].put(DatabaseContract.SavedEntry.COLUMN_FILE, savedList.get(i).getFilePath());
            values[i].put(DatabaseContract.SavedEntry.COLUMN_SUBJECT, savedList.get(i).getSubjectTitle());
            Log.i(TAG, "getSavedContentValue: " + savedList.get(i).getSubjectTitle());
            values[i].put(DatabaseContract.SavedEntry.COLUMN_DISPLAY_TEXT, savedList.get(i).getText());
        }
        return values;
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
        for (int i = 0; i < values.length; i++) {

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
            values[i] = new ContentValues();
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
                    list.get(i).getUnityTypes());
            Log.i(TAG, "getSubjectContentValues: " + list.get(i).getUnityTypes());
        }
        return values;
    }
}
