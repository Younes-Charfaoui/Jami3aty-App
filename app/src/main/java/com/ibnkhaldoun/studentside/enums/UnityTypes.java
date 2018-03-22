package com.ibnkhaldoun.studentside.enums;


import com.ibnkhaldoun.studentside.R;

/**
 * @definition this class will handle the Unity types
 * of the subjects, it has some method convert the type from
 * numbers to string and the inverse.
 */

public final class UnityTypes {


    //the constant that will represent the unities.
    private static final int FUNDAMENTAL = 1;
    private static final int METHODOLOGICAL = 2;
    private static final int TRAVERSAL = 3;


    //the method to convert from number to String.
    public static int getUnitType(int number) {
        switch (number) {
            case FUNDAMENTAL:
                return R.string.fundamental_unity;
            case METHODOLOGICAL:
                return R.string.methodological_unity;
            case TRAVERSAL:
                return R.string.traversal_unity;
            default:
                throw new IllegalStateException();
        }
    }


}
