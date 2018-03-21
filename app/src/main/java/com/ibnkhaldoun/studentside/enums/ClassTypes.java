package com.ibnkhaldoun.studentside.enums;


public final  class ClassTypes {

    public static final int COURSE_NUMBER = 1;
    public static final String COURSE_STRING = "Course";
    public static final int TD_NUMBER = 2;
    public static final String TD_STRING = "TD";
    public static final int TP_NUMBER = 3;
    public static final String TP_STRING = "TP";

    public static String getStringFormat(int types) {
        switch (types) {
            case TD_NUMBER:
                return TD_STRING;
            case TP_NUMBER:
                return TP_STRING;
            case COURSE_NUMBER:
                return COURSE_STRING;
            default:
                return null;
        }
    }
}
