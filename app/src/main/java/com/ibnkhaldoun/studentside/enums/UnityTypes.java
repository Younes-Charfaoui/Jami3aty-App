package com.ibnkhaldoun.studentside.enums;


public enum UnityTypes {
    FUNDAMENTAL, METHODOLOGICAL, TRAVERSAL;

    public static UnityTypes getUnitType(int number) {
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

    public static int getUnitType(UnityTypes types) {
        switch (types) {
            case FUNDAMENTAL:
                return 0;
            case METHODOLOGICAL:
                return 1;
            case TRAVERSAL:
                return 2;
            default:
                throw new IllegalStateException();
        }
    }
}
