package com.ibnkhaldoun.studentside.providers;

/**
 * @definition this class has the constant for the different
 * end point in the application.
 */

public final class EndPointsProvider {

    private static final String BASE = "http://192.168.1.2/";
    private final static String LOGIN_ENDPOINT = BASE + "jami3aty/auth/login/";
    private static final String SIGN_UP_ENDPOINT = BASE + "jami3aty/auth/register/";
    private final static String FORGET_PASSWORD_ENDPOINT = BASE + "jami3aty/users/forgotpass/";
    private final static String SUBJECT_ENDPOINT = BASE + "jami3aty/modules/";
    private final static String MARK_ENDPOINT = BASE + "jami3aty/users/marks/";
    private final static String MAIL_ENDPOINT = BASE + "jami3aty/users/mails/";
    private final static String SAVED_ENDPOINT = BASE + "jami3aty/saved/";
    private final static String EXAM_ENDPOINT = BASE + "jami3aty/exams/";
    private final static String SCHEDULE_ENDPOINT = BASE + "jami3aty/schedules/";

    public static String getScheduleAllEndpoint() {
        return SCHEDULE_ENDPOINT + "all/";
    }

    public static String getSubjectAllEndpoint() {
        return SUBJECT_ENDPOINT + "all/";
    }

    public static String getSignUpEndpoint() {
        return SIGN_UP_ENDPOINT;
    }

    public static String getForgetPasswordEndpoint() {
        return FORGET_PASSWORD_ENDPOINT;
    }

    public static String getLoginEndpoint() {
        return LOGIN_ENDPOINT;
    }

    public static String getMarksEndPoint() {
        return MARK_ENDPOINT;
    }

    public static String getMailEndPoint() {
        return MAIL_ENDPOINT;
    }

    public static String getSavedEndPoint() {
        return SAVED_ENDPOINT;
    }

    public static String getExamScheduleEndpoint() {
        return EXAM_ENDPOINT;
    }
}
