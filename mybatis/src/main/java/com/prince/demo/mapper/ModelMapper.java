package com.prince.demo.mapper;

import com.prince.demo.model.Model;
import com.prince.mybatis.v2.annotation.Entity;
import com.prince.mybatis.v2.annotation.Select;

/**
 * mapper接口
 *
 * @author Prince
 * @date 2020/6/7 16:55
 */
@Entity(Model.class)
public interface ModelMapper {
    @Select("select * from mybatis where id = ? ")
    Model selectModelById(Integer id);
}
