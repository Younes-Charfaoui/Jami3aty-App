package com.ibnkhaldoun.studentside.enums;


public final class UnityTypes {

    public static final int FUNDAMENTAL = 1;
    public static final int METHODOLOGICAL = 2;
    public static final int TRAVERSAL = 3;


    public static int getUnitType(int number) {
        switch (number) {
            case 0:
                return FUNDAMENTAL;
            case 1:
                return METHODOLOGICAL;
            case 2:
                return TRAVERSAL;
            default:
                throw new IllegalStateException();
        }
    }


}
