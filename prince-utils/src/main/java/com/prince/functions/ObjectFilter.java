package com.prince.functions;

/**
 * 对象过滤接口
 *
 * @param <T>
 */
public interface ObjectFilter<T> {
    boolean filter(T t);
}
