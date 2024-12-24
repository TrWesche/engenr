package com.twesche.model;

import com.twesche.enums.EGRFieldAccess;

import javax.lang.model.element.VariableElement;

public class EGRField {
    // Element Reference
    VariableElement Element;

    // ==== Field Access
    // ---- Access Level
    //      0b0000 0001 - Read Access
    //      This element is included in the view but cannot be set by the user.
    //      The controller generated will prevent an update to this column during data merge.
    //          For newly created records the value we be set to null.  For updated records the original value will be taken.
    //      0b0000 0011 - Read Write Access
    //      This element is included in the view and can be set by the user.
    //      The controller generated will take the value applied to this element during data merge.
    EGRFieldAccess accessLevel;


}
