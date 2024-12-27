package com.twesche.enums;

public enum EGRTypeAccess {
    READ(1),
    CREATE(2),
    UPDATE(4),
    DELETE(8);

    private final Integer value;

    EGRTypeAccess(Integer value) {
        this.value = value;
    }

    public Integer getValue()
    {
        return this.value;
    }
}
