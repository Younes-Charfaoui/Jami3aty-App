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

    public static Levels getLevel(int level){
        return LICENCE_ONE;
    }
}
