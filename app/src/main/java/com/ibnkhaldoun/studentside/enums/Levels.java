package com.ibnkhaldoun.studentside.enums;

/**
 * @definition this enum wil have the
 * definition of the level that a student can
 * be found
 */
public enum Levels {

    LICENCE_THREE,
    LICENCE_TWO,
    LICENCE_ONE,
    MASTER_GL_ONE,
    MASTER_GL_TWO,
    MASTER_GI_ONE,
    MASTER_GI_TWO,
    MASTER_RT_ONE,
    MASTER_RT_TWO;

    public static Levels getLevel(int level) {
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

    public static String getLevelString(Levels level) {
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
                throw new IllegalStateException();
        }
    }
}
