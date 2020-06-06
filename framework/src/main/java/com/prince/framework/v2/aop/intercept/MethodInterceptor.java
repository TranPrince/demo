package com.prince.framework.v2.aop.intercept;

/**
 * @author Prince
 * @date 2020/5/25 18:07
 */
public interface MethodInterceptor {

    Object invoke(MethodInvocation invocation) throws Throwable;
}
