package com.prince.framework.v2.aop;

/**
 * @author Prince
 * @date 2020/6/6 17:11
 */
public interface AopProxy {

        Object getProxy();


        Object getProxy(ClassLoader classLoader);

}
