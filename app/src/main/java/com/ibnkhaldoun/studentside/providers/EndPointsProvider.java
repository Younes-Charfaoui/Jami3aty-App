package com.ibnkhaldoun.studentside.providers;

/**
 * @definition this class has the constant for the different
 * end point in the application.
 */

public final class EndPointsProvider {

    private static final String BASE = "http://192.168.33.2/";
    private final static String LOGIN_ENDPOINT = BASE + "jami3aty/users/login";
    private static final String SIGN_UP_ENDPOINT = BASE + "jami3aty/users/register";
    private final static String FORGET_PASSWORD_ENDPOINT = BASE + "jami3aty/users/forgotpass";
    private final static String SUBJECT_ENDPOINT = BASE + "jami3aty/pages/modules";
    private final static String MARK_ENDPOINT = BASE + "jami3aty/users/marks";
    private final static String MAIL_ENDPOINT = BASE + "jami3aty/users/mails";


    public static String getSubjectEndpoint() {
        return SUBJECT_ENDPOINT;
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
}
