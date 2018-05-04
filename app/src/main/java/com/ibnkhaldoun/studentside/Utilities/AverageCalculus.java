/*------------------------------------------------------------------------------
 - Copyright (c) 2018. This code was created by Younes Charfaoui in the process of Graduation Project for the year of  2018 , which is about creating a platform  for students and professors to help them in the communication and the get known of the university information and so on.
 -----------------------------------------------------------------------------*/

package com.ibnkhaldoun.studentside.Utilities;


/**
 * @definition this class will have the methods
 * to calculate the average of the student in
 * each module.
 */

public final class AverageCalculus {

    //method for calculating the marks with Course , TD or TP
    public static double getTwoMarkAverage(int course, int tdOrTp, int type) {
        if (type == 1) {
            return course * 0.66 + tdOrTp * 0.34;
        } else {
            return course * 0.6 + tdOrTp * 0.4;
        }
    }

    //method for calculating the marks with Course , TD and TP
    public static double getFullMarkAverage(float course, float td, float tp) {

        return course * 0.5 + td * 0.25 + tp * 0.25;

    }
}
