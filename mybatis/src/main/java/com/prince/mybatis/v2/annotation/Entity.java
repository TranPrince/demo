package com.prince.mybatis.v2.annotation;

import java.lang.annotation.*;

/**
 * @author Prince
 * @date 2020/6/7 17:48
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {
    Class<?> value();
}
