package com.prince.mybatis.v1;

import com.prince.demo.model.Model;

import java.sql.*;

/**
 * @author Prince
 * @date 2020/6/7 17:01
 */
public class Executor {

    public <T> T query(String sql, Object param) {
        Connection conn = null;
        Statement stmt = null;
        Model model = new Model();

        try {
            // 注册 JDBC 驱动
//            Class.forName("com.mysql.jdbc.Driver");

            // 打开连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prince", "root", "123456");

            // 执行查询
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format(sql, param));

            // 获取结果集
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                Integer authorId = rs.getInt("author_id");
                model.setAuthorId(authorId);
                model.setId(id);
                model.setName(name);
            }
            System.out.println(model);

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return (T)model;
    }
}
