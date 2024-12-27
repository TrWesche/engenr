package com.twesche.model;

import com.twesche.enums.EGRFieldAccess;

import javax.lang.model.element.VariableElement;

public class EGRFieldData {
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

    String fieldName; // Name to be assigned to the field in the view and the UI Model
    String fieldDescription; // Field description

    // ==== Java View Configuration
    Boolean viewAddToModel; // Adds value to Java View

    // ==== UI Configuration
    Boolean uiAddToModel; // Adds value to UI Model
    Boolean uiHide; // Sets the Field to be hidden in the UI by default

    public VariableElement getElement() {
        return Element;
    }

    public void setElement(VariableElement element) {
        Element = element;
    }

    public EGRFieldAccess getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(EGRFieldAccess accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public Boolean getViewAddToModel() {
        return viewAddToModel;
    }

    public void setViewAddToModel(Boolean viewAddToModel) {
        this.viewAddToModel = viewAddToModel;
    }

    public Boolean getUiAddToModel() {
        return uiAddToModel;
    }

    public void setUiAddToModel(Boolean uiAddToModel) {
        this.uiAddToModel = uiAddToModel;
    }

    public Boolean getUiHide() {
        return uiHide;
    }

    public void setUiHide(Boolean uiHide) {
        this.uiHide = uiHide;
    }
}
