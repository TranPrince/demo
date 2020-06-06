package com.prince.framework.v2.beans;

/**
 * Bean包装
 *
 * @author Prince
 * @date 2020/5/24 17:37
 */
public class BeanWrapper {
    private Object wrapperInstance;
    private Class<?> wrapperClass;

    public BeanWrapper(Object instance) {
        this.wrapperInstance = instance;
        this.wrapperClass = instance.getClass();
    }

    public Object getWrapperInstance() {
        return wrapperInstance;
    }

    public Class<?> getWrapperClass() {
        return wrapperClass;
    }
}
