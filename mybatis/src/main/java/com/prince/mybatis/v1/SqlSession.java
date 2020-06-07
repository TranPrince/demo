package com.prince.mybatis.v1;

/**
 * 对外api SqlSession
 *
 * @author Prince
 * @date 2020/6/7 16:59
 */
public class SqlSession {

    private Configuration configuration;

    private Executor executor;

    public SqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    public <T> T selectOne(String statementId, Object param){
        String sql = Configuration.sqlMappings.getString(statementId);
        return executor.query(sql,param);
    };

    public <T> T getMapper(Class clazz){
        return configuration.getMapper(clazz,this);
    }
}
