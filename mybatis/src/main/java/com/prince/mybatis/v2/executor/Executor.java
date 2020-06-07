package com.prince.mybatis.v2.executor;

/**
 * @author Prince
 * @date 2020/6/7 18:01
 */
public interface Executor {
    <T> T query(String statement, Object[] parameter, Class pojo);
}
