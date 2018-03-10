package com.ibnkhaldoun.studentside.enums;


public enum ClassTypes {
    TD, TP, COURSE;

    public static String getStringFormat(ClassTypes types) {
        switch (types) {

            case TD:
                return "TD";
            case TP:
                return "TP";
            case COURSE:
                return "Course";
            default:
                return null;
        }
    }
}
