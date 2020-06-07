package com.prince.mybatis.v2.annotation;

import java.lang.annotation.*;

/**
 * @author Prince
 * @date 2020/6/7 17:50
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Select {
    String value() default "";
}
