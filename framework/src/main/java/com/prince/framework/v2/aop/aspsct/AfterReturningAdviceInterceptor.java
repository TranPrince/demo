package com.prince.framework.v2.aop.aspsct;

import com.prince.framework.v2.aop.intercept.MethodInterceptor;
import com.prince.framework.v2.aop.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author Prince
 * @date 2020/5/25 18:07
 */
public class AfterReturningAdviceInterceptor extends AbstractAspectAdvice implements MethodInterceptor {

    private JoinPoint jp;
    public AfterReturningAdviceInterceptor(Object aspect, Method adviceMethod) {
        super(aspect, adviceMethod);
    }

    public void afterReturning(Object returnValue, Method method, Object[] args,Object target) throws Throwable {
        invokeAdviceMethod(this.jp, returnValue, null);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        this.jp = mi;
        Object returnValue = mi.proceed();
        this.afterReturning(returnValue,mi.getMethod(),mi.getArguments(),mi.getThis());
        return returnValue;
    }
}
