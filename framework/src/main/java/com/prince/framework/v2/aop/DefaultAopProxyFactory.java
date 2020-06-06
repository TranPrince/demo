package com.prince.framework.v2.aop;


import com.prince.framework.v2.aop.support.AdviceSupport;

/**
 * @author Prince
 * @date 2020/5/25 18:07
 */
public class DefaultAopProxyFactory {
    public AopProxy createAopProxy(AdviceSupport config){
        Class targetClass = config.getTargetClass();
        if(targetClass.getInterfaces().length > 0){
            return new JdkDynamicAopProxy(config);
        }
        return new CglibDynamicAopProxy();
    }
}
