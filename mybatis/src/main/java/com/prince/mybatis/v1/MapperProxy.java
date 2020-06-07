package com.prince.mybatis.v1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理类 触发管理类
 *
 * @author Prince
 * @date 2020/6/7 17:06
 */
public class MapperProxy implements InvocationHandler {

    private SqlSession session;

    public MapperProxy(SqlSession session) {
        this.session = session;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String statementId = method.getDeclaringClass().getName() + "." + method.getName();
        return session.selectOne(statementId,args[0]);
    }
}
