package com.twesche.enums;

public enum EGRGenerateTo {
    NONE(0b00000000),
    VIEW(0b00000001),
    SERVICE(0b00000011),
    CONTROLLER(0b00000111),
    JSMODEL(0b00001111),
    JSSERVICE(0b00011111),
    JSTABLE(0b00111111);

    private final Integer value;

    EGRGenerateTo(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
