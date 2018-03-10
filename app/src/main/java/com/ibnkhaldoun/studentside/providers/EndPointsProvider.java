package com.ibnkhaldoun.studentside.providers;

/**
 * @definition this class has the constant for the different
 * end point in the application
 */

public final class EndPointsProvider {

    private final static String LOGIN_ENDPOINT = "";
    private static final String SIGN_UP_ENDPOINT = "";
    private final static String FORGET_PASSWORD_ENDPOINT = "";

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
