package com.ibnkhaldoun.studentside.networking.utilities;

import android.util.Log;
import android.util.SparseArray;

import com.ibnkhaldoun.studentside.models.Mail;
import com.ibnkhaldoun.studentside.models.Mark;
import com.ibnkhaldoun.studentside.models.Professor;
import com.ibnkhaldoun.studentside.models.Saved;
import com.ibnkhaldoun.studentside.models.Schedule;
import com.ibnkhaldoun.studentside.models.ScheduleItem;
import com.ibnkhaldoun.studentside.models.Student;
import com.ibnkhaldoun.studentside.models.Subject;
import com.ibnkhaldoun.studentside.networking.models.ForgetPasswordResponse;
import com.ibnkhaldoun.studentside.networking.models.LoginResponse;
import com.ibnkhaldoun.studentside.networking.models.Response;
import com.ibnkhaldoun.studentside.networking.models.SignUpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.ibnkhaldoun.studentside.networking.models.Response.JSON_EXCEPTION;
import static com.ibnkhaldoun.studentside.networking.models.Response.RESPONSE_SUCCESS;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_DAY_SCHEDULE;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_HOUR_SCHEDULE;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_NAME_SCHEDULE;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_PLACE_SCHEDULE;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_POST_DATE;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_POST_FILE;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_POST_ID;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_POST_PROFESSOR;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_POST_SUBJECT;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_POST_TEXT;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_POST_TYPE;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_PROFESSOR_DEGREE;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_PROFESSOR_EMAIL;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_PROFESSOR_FIRST_NAME;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_PROFESSOR_ID;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_PROFESSOR_LAST_NAME;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_AVERAGE;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_CONFIRMED;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_EMAIL;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_FIRST_NAME;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_GROUP;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_ID;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_LAST_NAME;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_LEVEL;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_STUDENT_SECTION;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_SUBJECT_COEFFICIENT;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_SUBJECT_CONTENT;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_SUBJECT_CREDIT;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_SUBJECT_DATA;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_SUBJECT_LEVEL;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_SUBJECT_PROFESSOR_COURSE;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_SUBJECT_PROFESSOR_TD;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_SUBJECT_PROFESSOR_TP;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_SUBJECT_SHORT_TITLE;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_SUBJECT_SUMMARY;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_SUBJECT_TITLE;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_SUBJECT_UNITY_TYPE;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_TITLE_SCHEDULE;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.JSON_TYPE_SCHEDULE;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.KEY_JSON_DATA;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.KEY_JSON_STATUS;

/**
 * this class will make the role of doing the parse
 * process for all the responses.
 */

public class JsonUtilities {

    private static final String TAG = "json";

    public static LoginResponse getLoginResponse(String jsonText) {
        try {
            Log.i(TAG, "getLoginResponse: response " + jsonText);
            JSONObject root = new JSONObject(jsonText);

            int status = root.getInt(KEY_JSON_STATUS);

            LoginResponse response = new LoginResponse(status);

            if (status == RESPONSE_SUCCESS) {

                JSONObject data = root.getJSONObject(KEY_JSON_DATA);

                if (data.has(JSON_STUDENT_ID)) {
                    Student student = new Student();
                    student.setId(data.getString(JSON_STUDENT_ID));
                    student.setFirstName(data.getString(JSON_STUDENT_FIRST_NAME));
                    student.setLastName(data.getString(JSON_STUDENT_LAST_NAME));
                    student.setAverage(Float.parseFloat(data.getString(JSON_STUDENT_AVERAGE)));
                    student.setEmail(data.getString(JSON_STUDENT_EMAIL));
                    student.setLevel(Integer.parseInt(data.getString(JSON_STUDENT_LEVEL)));
                    student.setGroup(Integer.parseInt(data.getString(JSON_STUDENT_GROUP)));
                    student.setSection(Integer.parseInt(data.getString(JSON_STUDENT_SECTION)));
                    student.setConfirmed(data.getString(JSON_STUDENT_CONFIRMED).equals("1"));
                    response.setIsStudent(true);
                    response.setStudent(student);
                } else {
                    Professor professor = new Professor();
                    professor.setId(data.getString(JSON_PROFESSOR_ID));
                    professor.setFirstName(data.getString(JSON_PROFESSOR_FIRST_NAME));
                    professor.setLastName(data.getString(JSON_PROFESSOR_LAST_NAME));
                    professor.setEmail(data.getString(JSON_PROFESSOR_EMAIL));
                    professor.setDegree(data.getString(JSON_PROFESSOR_DEGREE));
                    response.setProfessor(professor);
                    response.setIsStudent(false);
                }
            }
            return response;
        } catch (JSONException e) {
            Log.i(TAG, "getLoginResponse: JSON Exception");
            e.printStackTrace();
            return new LoginResponse(JSON_EXCEPTION);
        }

    }

    public static SignUpResponse getSignUpResponse(String jsonText) {
        try {

            JSONObject root = new JSONObject(jsonText);
            int status = root.getInt(KEY_JSON_STATUS);

            return new SignUpResponse(status);
        } catch (JSONException e) {

            e.printStackTrace();
            return new SignUpResponse(JSON_EXCEPTION);
        }
    }

    public static ArrayList<Subject> getSubjectList(String response) {
        try {

            JSONObject root = new JSONObject(response);
            int status = root.getInt(KEY_JSON_STATUS);
            if (status != 200) return null;
            JSONArray subjects = root.getJSONArray(JSON_SUBJECT_DATA);
            return getSubjectFromJsonArray(subjects);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ForgetPasswordResponse getForgetPasswordResponse(String response) {

        try {
            return new ForgetPasswordResponse(Integer.
                    parseInt(new JSONObject(response)
                            .getString(KEY_JSON_STATUS)));
        } catch (JSONException e) {

            return new ForgetPasswordResponse(Response.JSON_EXCEPTION);
        }

    }

    public static ArrayList<Mark> getMarkList(String response) {
        // TODO: 14/03/2018 add the code to parse the mark response
        return null;
    }

    public static ArrayList<Mail> getMailList(String response) {
        // TODO: 15/03/2018 add the code to parse the mail response
        return null;
    }


    private static ArrayList<Subject> getSubjectFromJsonArray(JSONArray array) throws JSONException {
        ArrayList<Subject> subjects = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            String title = array.getJSONObject(i).getString(JSON_SUBJECT_TITLE);
            String short_title = array.getJSONObject(i).getString(JSON_SUBJECT_SHORT_TITLE);
            String coefficient = array.getJSONObject(i).getString(JSON_SUBJECT_COEFFICIENT);

            String content = array.getJSONObject(i).getString(JSON_SUBJECT_CONTENT);
            String summary = array.getJSONObject(i).getString(JSON_SUBJECT_SUMMARY);
            String credit = array.getJSONObject(i).getString(JSON_SUBJECT_CREDIT);
            int unityType = array.getJSONObject(i).getInt(JSON_SUBJECT_UNITY_TYPE);

            int level = array.getJSONObject(i).getInt(JSON_SUBJECT_LEVEL);
            Subject subject = new Subject(title, short_title, summary,
                    content, credit, coefficient, unityType, level);
            if (array.getJSONObject(i).has(JSON_SUBJECT_PROFESSOR_COURSE)) {
                subject.setCourseProfessor(array.getJSONObject(i).getString(JSON_SUBJECT_PROFESSOR_COURSE));
            }
            if (array.getJSONObject(i).has(JSON_SUBJECT_PROFESSOR_TD)) {
                subject.setTdProfessor(array.getJSONObject(i).getString(JSON_SUBJECT_PROFESSOR_TD));
            }
            if (array.getJSONObject(i).has(JSON_SUBJECT_PROFESSOR_TP)) {
                subject.setTpProfessor(array.getJSONObject(i).getString(JSON_SUBJECT_PROFESSOR_TP));
            }
            subjects.add(subject);
        }
        return subjects;
    }

    public static ArrayList<Saved> getSavedList(String response) throws JSONException {
        JSONObject root = new JSONObject(response);
        if (root.getInt(KEY_JSON_STATUS) != 200) throw new JSONException("Error in code");

        ArrayList<Saved> saves = new ArrayList<>();
        JSONArray data = root.getJSONArray(KEY_JSON_DATA);
        for (int i = 0; i < data.length(); i++) {
            JSONObject saved = data.getJSONObject(i);
            long id = saved.getLong(JSON_POST_ID);
            String date = saved.getString(JSON_POST_DATE);
            String text = saved.getString(JSON_POST_TEXT);
            String professor = saved.getString(JSON_POST_PROFESSOR);
            String file = saved.getString(JSON_POST_FILE);
            String subject = saved.getString(JSON_POST_SUBJECT);
            Log.i(TAG, "getSavedList: " + subject);
            int type = saved.getInt(JSON_POST_TYPE);
            Saved save = new Saved(id, professor, text, date);
            save.setFilePath(file);
            save.setSubjectTitle(subject);
            save.setType(type);
            saves.add(save);
        }
        return saves;
    }

    public static SparseArray<Schedule> getSchedulesList(String response) throws JSONException {
        SparseArray<Schedule> schedules = new SparseArray<>();
        JSONObject root = new JSONObject(response);

        int status = root.getInt(KEY_JSON_STATUS);
        if (status != 200) throw new JSONException("Code was not 200");
        JSONArray data = root.getJSONArray(JSON_SUBJECT_DATA);
        int level = root.getInt(JSON_STUDENT_LEVEL),
                section = root.getInt(JSON_STUDENT_SECTION),
                group = root.getInt(JSON_STUDENT_GROUP);
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);
            int day = object.getInt(JSON_DAY_SCHEDULE);
            Schedule schedule = schedules.get(day, null) != null ? schedules.get(day)
                    : new Schedule(day, level, section, group);
            int time = object.getInt(JSON_HOUR_SCHEDULE);
            String place = object.getString(JSON_PLACE_SCHEDULE);
            String subject = object.getString(JSON_TITLE_SCHEDULE);
            String professor = object.getString(JSON_NAME_SCHEDULE);
            int type = object.getInt(JSON_TYPE_SCHEDULE);
            ScheduleItem item = new ScheduleItem(time, place, subject, professor, type);
            schedule.getScheduleItemList().add(item);
            schedules.put(day, schedule);
        }
        return schedules;
    }
}
