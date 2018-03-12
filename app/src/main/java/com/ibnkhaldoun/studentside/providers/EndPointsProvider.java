package com.ibnkhaldoun.studentside.providers;

/**
 * @definition this class has the constant for the different
 * end point in the application
 */

public final class EndPointsProvider {

    private static final String BASE = "http://192.168.43.90/";
    private final static String LOGIN_ENDPOINT = BASE + "jami3aty/users/login";
    private static final String SIGN_UP_ENDPOINT = BASE + "jami3aty/users/register";
    private final static String FORGET_PASSWORD_ENDPOINT = BASE + "jami3aty/users/forgetpassword";
    private final static String SUBJECT_ENDPOINT = BASE + "jami3aty/users/subjects";

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
}
