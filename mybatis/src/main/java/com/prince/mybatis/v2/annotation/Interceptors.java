package com.prince.mybatis.v2.annotation;

import java.lang.annotation.*;

/**
 * @author Prince
 * @date 2020/6/7 17:49
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Interceptors {
    String value() default "";
}
