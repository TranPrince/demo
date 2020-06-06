package com.prince.framework.v2.aop.aspsct;

import java.lang.reflect.Method;

/**
 * @author Prince
 * @date 2020/5/25 18:07
 */
public abstract class AbstractAspectJAdvice implements Advice {

    private Object aspect;
    private Method adviceMethod;
    private String throwName;

//    public GPAdvice(Object aspect, Method adviceMethod) {
//        this.aspect = aspect;
//        this.adviceMethod = adviceMethod;
//    }

    public AbstractAspectJAdvice(Object aspect, Method adviceMethod) {
        this.adviceMethod = adviceMethod;
        this.aspect = aspect;
    }

    protected Object invokeAdviceMethod(JoinPoint jp, Object returnValue, Throwable t) throws Throwable {

        //LogAspect.before(),LogAspect.after()  ...
        Class<?> [] paramTypes = this.adviceMethod.getParameterTypes();
        if(null == paramTypes || paramTypes.length == 0){
            return this.adviceMethod.invoke(this.aspect);
        }else{
            Object [] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                if(paramTypes[i] == JoinPoint.class){
                    args[i] = jp;
                }else if(paramTypes[i] == Throwable.class){
                    args[i] = t;
                }else if(paramTypes[i] == Object.class){
                    args[i] = returnValue;
                }
            }
            return this.adviceMethod.invoke(aspect,args);
        }

    }
}
