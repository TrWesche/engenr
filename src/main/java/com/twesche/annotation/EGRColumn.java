package com.twesche.annotation;

import com.twesche.enums.EGRFieldAccess;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
public @interface EGRColumn {
    boolean viewEligible() default true;
    boolean uiHideColumn() default false;

    EGRFieldAccess access() default EGRFieldAccess.READ_ONLY;
}
