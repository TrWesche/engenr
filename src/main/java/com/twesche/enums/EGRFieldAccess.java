package com.twesche.enums;

public enum EGRFieldAccess {
    NONE(0b00000000),
    READ_ONLY(0b00000001),
    READ_WRITE(0b00000011);

    private final Integer value;

    EGRFieldAccess(Integer value) {
        this.value = value;
    }

    public Integer getValue()
    {
        return this.value;
    }
}
