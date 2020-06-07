package com.prince.mybatis.v2.binding;

import com.prince.mybatis.v2.session.DefaultSqlSession;

import java.lang.reflect.Proxy;

/**
 * MapperProxy代理类工厂
 *
 * @author Prince
 * @date 2020/6/7 17:52
 */
public class MapperProxyFactory<T> {
    private Class<T>  mapperInterface;
    private Class object;

    public MapperProxyFactory(Class<T> mapperInterface, Class object) {
        this.mapperInterface = mapperInterface;
        this.object = object;
    }

    public T newInstance(DefaultSqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface }, new MapperProxy(sqlSession, object));
    }
}
