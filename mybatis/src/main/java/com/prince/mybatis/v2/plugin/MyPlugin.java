package com.prince.mybatis.v2.plugin;

import com.prince.mybatis.v2.annotation.Interceptors;
import com.prince.mybatis.v2.interceptor.Interceptor;
import com.prince.mybatis.v2.interceptor.Invocation;
import com.prince.mybatis.v2.interceptor.Plugin;

import java.util.Arrays;

/**
 * @author Prince
 * @date 2020/6/7 18:18
 */
@Interceptors("query")
public class MyPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String statement = (String) invocation.getArgs()[0];
        Object[] parameter = (Object[]) invocation.getArgs()[1];
        Class pojo = (Class) invocation.getArgs()[2];
        System.out.println("进入自定义插件：MyPlugin");
        System.out.println("SQL：["+statement+"]");
        System.out.println("Parameters："+ Arrays.toString(parameter));

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
