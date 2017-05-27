package com.ssm.common.core.mapper;

import com.ssm.common.model.Model;

/**
 * @author Gavin
 * @see org.apache.ibatis.binding.MapperProxy
 * @see org.apache.ibatis.binding.MapperProxyFactory
 */
public interface BaseMapper<T extends Model> {

    int insert(T record);

    int insertSelective(T record);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(T record);

    int updateByPrimaryKeySelective(T record);

    T selectByPrimaryKey(Long id);

}
