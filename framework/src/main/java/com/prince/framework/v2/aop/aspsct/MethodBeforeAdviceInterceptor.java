package com.prince.framework.v2.aop.aspsct;

import com.prince.framework.v2.aop.intercept.MethodInterceptor;
import com.prince.framework.v2.aop.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author Prince
 * @date 2020/5/25 18:07
 */
public class MethodBeforeAdviceInterceptor extends AbstractAspectJAdvice implements MethodInterceptor {

    private JoinPoint jp;
    public MethodBeforeAdviceInterceptor(Object aspect, Method adviceMethod) {
        super(aspect, adviceMethod);
    }

    public void before(Method method, Object[] args, Object target) throws Throwable {
        this.invokeAdviceMethod(this.jp,null,null);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        jp = mi;
        this.before(mi.getMethod(), mi.getArguments(), mi.getThis());
        return mi.proceed();
    }
}
