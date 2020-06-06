package com.prince.framework.v2.aop;

/**
 * Cglib动态代理
 *
 * @author Prince
 * @date 2020/6/6 17:12
 */
public class CglibDynamicAopProxy implements AopProxy {
    @Override
    public Object getProxy() {
        return getProxy(this.getClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
