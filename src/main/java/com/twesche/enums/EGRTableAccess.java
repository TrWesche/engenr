package com.twesche.enums;

public enum EGRTableAccess {
    NONE(0b00000000),
    READ(0b00000001),
    CREATE(0b00000011),
    UPDATE(0b00000101),
    DELETE(0b00001001),
    CREATE_UPDATE(0b00000111),
    CREATE_DELETE(0b00001011),
    UPDATE_DELETE(0b00001101),
    CREATE_UPDATE_DELETE(0b00001111);

    private final Integer value;

    EGRTableAccess(Integer value) {
        this.value = value;
    }

    public Integer getValue()
    {
        return this.value;
    }
}
