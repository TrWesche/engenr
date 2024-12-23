package com.twesche.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface EGREntity {
    boolean canCreate() default false;
    boolean canRead() default true;
    boolean canUpdate() default false;
    boolean canDelete() default false;

    boolean createView() default true;
    boolean createController() default true;
    boolean createWebComponent() default true;
}
