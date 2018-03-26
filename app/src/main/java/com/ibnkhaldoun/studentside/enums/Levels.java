package com.ibnkhaldoun.studentside.enums;

/**
 * @definition this enum wil have the
 * definition of the level that a student can
 * be found
 */
public final  class Levels {

    public static final int LICENCE_ONE = 1;
    public static final int LICENCE_TWO = 2;
    public static final int LICENCE_THREE = 3;

    public static final int MASTER_GL_ONE = 4;
    public static final int MASTER_GL_TWO = 5;
    public static final int MASTER_GI_ONE = 6;
    public static final int MASTER_GI_TWO = 7;
    public static final int MASTER_RT_ONE = 8;
    public static final int MASTER_RT_TWO = 9;


    public static int getLevel(int level) {
        switch (level) {
            case 1:
            case 2:
                return LICENCE_ONE;
            case 3:
            case 4:
                return LICENCE_TWO;
            case 5:
            case 6:
                return LICENCE_THREE;
            //First master software
            case 7:
            case 8:
                return MASTER_GL_ONE;
            //First master networking
            case 9:
            case 10:
                return MASTER_RT_ONE;
            //first master computer sicience
            case 11:
            case 12:
                return MASTER_GI_ONE;
            //Second master software
            case 13:
            case 14:
                return MASTER_GL_TWO;
            //Second master networking
            case 15:
            case 16:
                return MASTER_RT_TWO;
            //Second master computer sicience
            case 17:
            case 18:
                return MASTER_GI_TWO;
            default:
                throw new IllegalStateException();
        }
    }

    public static String getLevelString(int level) {
        switch (level) {
            case LICENCE_ONE:
                return "L1 Computer Science";
            case LICENCE_TWO:
                return "L2 Computer Science";
            case LICENCE_THREE:
                return "L3 Computer Science";
            case MASTER_GI_ONE:
                return "M1 GI Computer Science";
            case MASTER_GI_TWO:
                return "M2 GI Computer Science";
            case MASTER_GL_ONE:
                return "M1 GL Computer Science";
            case MASTER_GL_TWO:
                return "M2 GL Computer Science";
            case MASTER_RT_ONE:
                return "M1 RT Computer Science";
            case MASTER_RT_TWO:
                return "M2 RT Computer Science";
            default:
                return "L1 Computer Science";
        }
    }
}
