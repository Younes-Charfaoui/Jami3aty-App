package com.ibnkhaldoun.studentside.providers;

/**
 * @definition this class has the constant for the different
 * end point in the application.
 */

public final class EndPointsProvider {

    private static final String BASE = "http://192.168.33.2/";
    private final static String LOGIN_ENDPOINT = BASE + "jami3aty/auth/login/";
    private static final String SIGN_UP_ENDPOINT = BASE + "jami3aty/auth/register/";
    private final static String FORGET_PASSWORD_ENDPOINT = BASE + "jami3aty/users/forgotpass/";
    private final static String SUBJECT_ENDPOINT = BASE + "jami3aty/modules/";
    private final static String MARK_ENDPOINT = BASE + "jami3aty/users/marks/";
    private final static String MAIL_ENDPOINT = BASE + "jami3aty/users/mails/";
    private final static String SAVED_ENDPOINT = BASE + "jami3aty/saved/";
    private final static String EXAM_ENDPOINT = BASE + "jami3aty/exams/";
    private final static String SCHEDULE_ENDPOINT = BASE + "jami3aty/schedules/";
    private final static String UNSAVE_ENDPOINT = BASE + "jami3aty/saved/state/";
    private final static String COMMENTS_ENDPOINT = BASE + "jami3aty/comments/";
    private static final String NOTIFICATION_ENDPOINT = BASE + "jami3aty/notifications/all";
    private static final String DISPLAY_ENDPOINT_ALL = BASE + "jami3aty/posts/all";
    private static final String DISPLAY_ENDPOINT = BASE + "jami3aty/posts/get";

    public static String getAddCommentsEndpoint() {
        return COMMENTS_ENDPOINT + "add";
    }

    public static String getModifyCommentsEndpoint() {
        return COMMENTS_ENDPOINT + "edit";
    }

    public static String getAllCommentsEndpoint() {
        return COMMENTS_ENDPOINT + "all";
    }

    public static String getRemoveCommentsEndpoint() {
        return COMMENTS_ENDPOINT + "remove";
    }

    private static final String ADD_COMMENTS_ENDPOINT = BASE + "jami3aty/posts/get";

    public static String getCommentsEndpoint() {
        return COMMENTS_ENDPOINT;
    }


    public static String getUnsaveEndpoint() {
        return UNSAVE_ENDPOINT;
    }

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

    public static String getNotifications() {
        return NOTIFICATION_ENDPOINT;
    }

    public static String getDisplays() {
        return DISPLAY_ENDPOINT_ALL;
    }
}
