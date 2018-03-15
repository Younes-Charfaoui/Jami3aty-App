package com.ibnkhaldoun.studentside.networking.models;


public class Response {

    public static final int RESPONSE_SUCCESS = 200;
    public static final int RESPONSE_EMAIL_ERROR = 400;
    public static final int RESPONSE_ACCOUNT_EXISTS = 401;
    public static final int RESPONSE_PASSWORD_ERROR = 402;
    public static final int RESPONSE_AVERAGE_ERROR = 403;

    private int status;

    Response(int status) {
        this.status = status;
    }

    Response() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
