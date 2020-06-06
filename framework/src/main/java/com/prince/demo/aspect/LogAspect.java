package com.prince.demo.aspect;

import com.prince.framework.v2.aop.aspsct.JoinPoint;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Tom.
 */
@Slf4j
public class LogAspect {

    //在调用一个方法之前，执行before方法
    public void before(JoinPoint joinPoint){
        joinPoint.setUserAttribute("startTime_" + joinPoint.getMethod().getName(),System.currentTimeMillis());
        //这个方法中的逻辑，是由我们自己写的
        log.info("Invoker Before Method!!!");
        System.out.println("Invoker Before Method!!!");
    }

    //在调用一个方法之后，执行after方法
    public void after(JoinPoint joinPoint){
        long startTime = (Long)joinPoint.getUserAttribute("startTime_" + joinPoint.getMethod().getName());
        long endTime = System.currentTimeMillis();

        log.info("Invoker After Method!!!" + "use time " + (endTime - startTime));
        System.out.println("Invoker After Method!!!");
    }

    public void afterThrowing(JoinPoint joinPoint,Throwable ex){

        log.info("出现异常: " + ex.getMessage());
        System.out.println("出现异常");
    }
}
