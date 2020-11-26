package com.lidan.Sercurity.Config;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProjectAuthToken {
    String value() default "";
}
