package com.ibnkhaldoun.studentside.networking.models;


public class Response {

    public static final int RESPONSE_SUCCESS = 200;
    public static final int RESPONSE_EMAIL_ERROR = 401;
    public static final int RESPONSE_PASSWORD_ERROR = 403;
    public static final int RESPONSE_EMAIL_NOT_EXIST = 406;
    public static final int RESPONSE_AVERAGE_ERROR = 407;
    public static final int RESPONSE_EMAIL_MAILER_ERROR = 411;
    public static final int RESPONSE_CARD_NOT_EXIST = 412;
    public static final int RESPONSE_CARD_EXIST = 413;


    public static final int IO_EXCEPTION = 501;
    public static final int JSON_EXCEPTION = 502;

    private int status;
    private String data;

    public Response(int status) {
        this.status = status;
    }

    public void setData(String data){
        this.data = data;
    }

    Response() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }
}
