package com.twesche.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface EGRMethod {
    boolean viewEligible() default true;
    boolean uiHideColumn() default false;

    boolean canReadValue() default true;
    boolean canWriteValue() default false;
}
