package com.twesche.annotation;

import com.twesche.enums.EGRGenerate;
import com.twesche.enums.EGRTypeAccess;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface EGRType {
    EGRTypeAccess[] access() default EGRTypeAccess.READ;
    EGRGenerate[] generate() default EGRGenerate.VIEW;
}
