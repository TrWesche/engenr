package com.twesche.annotation;

import com.twesche.enums.EGRGenerateTo;
import com.twesche.enums.EGRTableAccess;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface EGREntity {
    EGRTableAccess access() default EGRTableAccess.READ;
    EGRGenerateTo generateTo() default EGRGenerateTo.VIEW;
}
