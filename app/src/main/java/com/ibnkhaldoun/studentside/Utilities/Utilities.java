package com.ibnkhaldoun.studentside.Utilities;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.ibnkhaldoun.studentside.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


public final class Utilities {

    public static int getMarkColor(float mark, Context context) {
        if (mark >= 10) return ContextCompat.getColor(context, R.color.deep_green);
        else return ContextCompat.getColor(context, R.color.deep_red);
    }

    public static int getCircleColor(char firstChar, Context context) {
        char mChar = Character.toLowerCase(firstChar);
        int color;

        switch (mChar) {
            case 'a':
                color = R.color.circle_19;
                break;
            case 'z':
                color = R.color.circle_1;
                break;
            case 'b':
            case 'y':
                color = R.color.circle_2;
                break;
            case 'c':
            case 'x':
                color = R.color.circle_3;
                break;
            case 'd':
                color = R.color.circle_18;
                break;
            case 'w':
                color = R.color.circle_4;
                break;
            case 'e':
                color = R.color.circle_17;
                break;
            case 'v':
                color = R.color.circle_5;
                break;
            case 'f':
            case 'u':
                color = R.color.circle_6;
                break;
            case 'g':
            case 't':
                color = R.color.circle_7;
                break;
            case 'h':
                color = R.color.circle_16;
                break;
            case 's':
                color = R.color.circle_8;
                break;
            case 'i':
            case 'r':
                color = R.color.circle_9;
                break;
            case 'j':
            case 'q':
                color = R.color.circle_10;
                break;
            case 'k':
                color = R.color.circle_15;
                break;
            case 'p':
                color = R.color.circle_11;
                break;
            case 'l':
            case 'o':
                color = R.color.circle_12;
                break;
            case 'm':
                color = R.color.circle_13;
                break;
            case 'n':
                color = R.color.circle_14;
                break;
            default:
                color = R.color.circle_15;
        }
        return ContextCompat.getColor(context, color);
    }

    public static int getCircleColor(Context context) {
        char mChar = (char) (new Random().nextInt(122 - 97 + 1) + 97);
        int color;

        switch (mChar) {
            case 'a':
                color = R.color.circle_19;
                break;
            case 'z':
                color = R.color.circle_1;
                break;
            case 'b':
            case 'y':
                color = R.color.circle_2;
                break;
            case 'c':
            case 'x':
                color = R.color.circle_3;
                break;
            case 'd':
                color = R.color.circle_18;
                break;
            case 'w':
                color = R.color.circle_4;
                break;
            case 'e':
                color = R.color.circle_17;
                break;
            case 'v':
                color = R.color.circle_5;
                break;
            case 'f':
            case 'u':
                color = R.color.circle_6;
                break;
            case 'g':
            case 't':
                color = R.color.circle_7;
                break;
            case 'h':
                color = R.color.circle_16;
                break;
            case 's':
                color = R.color.circle_8;
                break;
            case 'i':
            case 'r':
                color = R.color.circle_9;
                break;
            case 'j':
            case 'q':
                color = R.color.circle_10;
                break;
            case 'k':
                color = R.color.circle_15;
                break;
            case 'p':
                color = R.color.circle_11;
                break;
            case 'l':
            case 'o':
                color = R.color.circle_12;
                break;
            case 'm':
                color = R.color.circle_13;
                break;
            case 'n':
                color = R.color.circle_14;
                break;
            default:
                color = R.color.circle_15;
        }
        return ContextCompat.getColor(context, color);
    }

    public static String getProfessorShortName(String name) {
        String[] s = name.split(" ");
        return Character.toString(s[1].charAt(0)) + Character.toString(s[2].charAt(0));
    }

    public static String getStudentShortName(String name) {
        String[] s = name.split(" ");
        return Character.toString(s[0].charAt(0)) + Character.toString(s[1].charAt(0));
    }

    public static String getFirstName(String fullName) throws ArrayIndexOutOfBoundsException {
        if (fullName.contains(" ")) {
            String[] names = fullName.split(" ");
            return names[0];
        } else {
            return fullName;
        }
    }

    public static String getLastName(String fullName) throws ArrayIndexOutOfBoundsException {
        if (fullName.contains(" ")) {
            String[] names = fullName.split(" ");
            return names[1];
        } else {
            return fullName;
        }
    }


    public static String getDateFormat(String date) {

        long time = Long.parseLong(date);
        Date dateB = new Date(time);

        SimpleDateFormat format = new SimpleDateFormat("dd MMM  HH:mm", Locale.getDefault());

        return format.format(dateB);
    }
}
