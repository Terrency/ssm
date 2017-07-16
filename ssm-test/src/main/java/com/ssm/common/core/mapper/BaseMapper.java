package com.ssm.common.core.mapper;

import com.ssm.common.base.model.Model;

public interface BaseMapper<T extends Model> {

    int insert(T record);

    int insertSelective(T record);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(T record);

    int updateByPrimaryKeySelective(T record);

    T selectByPrimaryKey(Long id);

}
