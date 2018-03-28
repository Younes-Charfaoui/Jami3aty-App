package com.ibnkhaldoun.studentside.Utilities;


/**
 * @definition this class will have the methods
 * to calculate the average of the student in
 * each module.
 */

public final class AverageCalculus {

    public static double getTwoMarkAverage(int course, int tdOrTp, int type) {
        if (type == 1) {
            return course * 0.66 + tdOrTp * 0.34;
        } else {
            return course * 0.6 + tdOrTp * 0.4;
        }
    }

    public static double getFullMarkAverage(float course, float td, float tp) {

        return course * 0.5 + td * 0.25 + tp * 0.25;

    }
}
