package com.twesche.annotation;

import com.twesche.enums.EGRFieldAccess;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
public @interface EGRField {
    String fieldName() default "";
    String fieldDescription() default "";
    boolean viewAddToModel() default true;
    boolean uiAddToModel() default true;
    boolean uiHide() default false;

    EGRFieldAccess access() default EGRFieldAccess.READ_ONLY;
}
