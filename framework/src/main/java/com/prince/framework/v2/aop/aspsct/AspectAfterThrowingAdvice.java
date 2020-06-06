package com.prince.framework.v2.aop.aspsct;


import com.prince.framework.v2.aop.intercept.MethodInterceptor;
import com.prince.framework.v2.aop.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author Prince
 * @date 2020/5/25 18:07
 */
public class AspectAfterThrowingAdvice extends AbstractAspectAdvice implements MethodInterceptor {

    private String throwName;

    public AspectAfterThrowingAdvice(Object aspect, Method adviceMethod) {
        super(aspect, adviceMethod);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        }
        catch (Throwable ex) {
            invokeAdviceMethod(mi, null, ex.getCause());
            throw ex;
        }
    }

    public void setThrowName(String throwName) {
        this.throwName = throwName;
    }
}
