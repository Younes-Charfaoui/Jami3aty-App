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
}
