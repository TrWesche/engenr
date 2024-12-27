package com.twesche.model;

import com.twesche.enums.EGRGenerate;
import com.twesche.enums.EGRTypeAccess;

import javax.lang.model.element.TypeElement;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

public class EGRTypeData implements Serializable {
    TypeElement type;

    Set<EGRFieldData> fields;

    EnumSet<EGRTypeAccess> access;

    EnumSet<EGRGenerate> generate;

    public TypeElement getType() {
        return type;
    }

    public void setType(TypeElement type) {
        this.type = type;
    }

    public Set<EGRFieldData> getFields() {
        return fields;
    }

    public void setFields(Set<EGRFieldData> fields) {
        this.fields = fields;
    }

    public EnumSet<EGRTypeAccess> getAccess() {
        return access;
    }

    public void setAccess(EnumSet<EGRTypeAccess> access) {
        this.access = access;
    }

    public EnumSet<EGRGenerate> getGenerate() {
        return generate;
    }

    public void setGenerate(EnumSet<EGRGenerate> generate) {
        this.generate = generate;
    }

    @Override
    public String toString() {
        return "EGRTypeData{" +
                "type=" + type +
                ", fields=" + fields +
                ", access=" + access +
                ", generate=" + generate +
                '}';
    }
}
