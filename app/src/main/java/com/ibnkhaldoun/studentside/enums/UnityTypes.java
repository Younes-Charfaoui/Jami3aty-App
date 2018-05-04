/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

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
    private static final int DISCOVERY = 4;

    //the method to convert from number to String.
    public static int getUnitType(int number) {
        switch (number) {
            case FUNDAMENTAL:
                return R.string.fundamental_unity;
            case METHODOLOGICAL:
                return R.string.methodological_unity;
            case TRAVERSAL:
                return R.string.traversal_unity;
            case DISCOVERY:
                return R.string.discovery_unity;
            default:
                return R.string.methodological_unity;
        }
    }


}
