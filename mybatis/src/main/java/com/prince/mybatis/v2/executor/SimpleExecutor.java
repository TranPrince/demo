package com.prince.mybatis.v2.executor;

/**
 * @author Prince
 * @date 2020/6/7 18:07
 */
public class SimpleExecutor implements Executor {
    @Override
    public <T> T query(String statement, Object[] parameter, Class pojo) {
        StatementHandler statementHandler = new StatementHandler();
        return statementHandler.query(statement, parameter, pojo);
    }
}
