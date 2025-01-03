package com.twesche.enums;

public enum EGRFieldAccess {
    READ(1),
    WRITE(2);

    private final Integer value;

    EGRFieldAccess(Integer value) {
        this.value = value;
    }

    public Integer getValue()
    {
        return this.value;
    }
}
