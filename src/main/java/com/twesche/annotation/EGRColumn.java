package com.twesche.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
public @interface EGRColumn {
    boolean viewEligible() default true;
    boolean uiHideColumn() default false;

    boolean canReadValue() default true;
    boolean canWriteValue() default false;
}
