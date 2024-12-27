package com.twesche.enums;

public enum EGRGenerate {
    VIEW(1),
    SERVICE(2),
    CONTROLLER(4),
    JSMODEL(8),
    JSSERVICE(16),
    JSTABLE(32);

    private final int value;

    EGRGenerate(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
