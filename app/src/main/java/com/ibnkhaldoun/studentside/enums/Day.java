package com.ibnkhaldoun.studentside.enums;


import com.ibnkhaldoun.studentside.R;

public final  class Day {
    public static final int SUNDAY = 1;
    public static final int MONDAY = 2;
    public static final int TUESDAY = 3;
    public static final int WEDNESDAY = 4;
    public static final int THURSDAY = 5;

    public static int getDay(int day) {
        switch (day) {
            case SUNDAY:
                return R.string.sunday;
            case MONDAY:
                return R.string.monday;
            case TUESDAY:
                return R.string.tuesday;
            case WEDNESDAY:
                return R.string.wednesday;
            case THURSDAY:
                return R.string.thursday;
            default:
                return R.string.sunday;
        }
    }

    public static int getShortDay(int day) {
        switch (day) {
            case SUNDAY:
                return R.string.sunday_string;
            case MONDAY:
                return R.string.monday_string;
            case TUESDAY:
                return R.string.tuesday_string;
            case WEDNESDAY:
                return R.string.wednesday_string;
            case THURSDAY:
                return R.string.thursday_string;
            default:
                return R.string.sunday_string;
        }
    }
}
