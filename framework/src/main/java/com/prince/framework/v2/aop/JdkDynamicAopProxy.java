package com.prince.framework.v2.aop;

import com.prince.framework.v2.aop.aspsct.Advice;
import com.prince.framework.v2.aop.intercept.MethodInvocation;
import com.prince.framework.v2.aop.support.AdviceSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * JDK代理类
 *
 * @author Prince
 * @date 2020/5/25 18:11
 */
public class JdkDynamicAopProxy implements InvocationHandler, AopProxy {

    private AdviceSupport advised;

    public JdkDynamicAopProxy(AdviceSupport config) {
        this.advised = config;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> chain = advised.getInterceptorsAndDynamicInterceptionAdvice(method,this.advised.getTargetClass());
        MethodInvocation invocation = new MethodInvocation(proxy, this.advised.getTarget(), method, args, this.advised.getTargetClass(), chain);
        return invocation.proceed();
    }

    @Override
    public Object getProxy() {
        return getProxy(this.getClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader,this.advised.getTargetClass().getInterfaces(),this);
    }
}
