package com.prince.demo.controller;

import com.prince.demo.mapper.ModelMapper;
import com.prince.demo.model.Model;
import com.prince.mybatis.v1.Configuration;
import com.prince.mybatis.v1.Executor;
import com.prince.mybatis.v1.SqlSession;
import com.prince.mybatis.v2.session.DefaultSqlSession;
import com.prince.mybatis.v2.session.SqlSessionFactory;

/**
 * 测试类
 *
 * @author Prince
 * @date 2020/6/7 16:58
 */
public class ModelController {
    public static void main(String[] args) {
        // v1
//        SqlSession sqlSession = new SqlSession(new Configuration(),new Executor());
//        ModelMapper mapper = sqlSession.getMapper(ModelMapper.class);
//        mapper.selectModelById(1);

        // v2
        SqlSessionFactory factory = new SqlSessionFactory();
        DefaultSqlSession sqlSession = factory.build().openSqlSession();
        // 获取MapperProxy代理
        ModelMapper mapper = sqlSession.getMapper(ModelMapper.class);
        Model model = mapper.selectModelById(1);

        System.out.println("第一次查询: " + model);
        System.out.println();
        model = mapper.selectModelById(1);
        System.out.println("第二次查询: " + model);

    }
}
