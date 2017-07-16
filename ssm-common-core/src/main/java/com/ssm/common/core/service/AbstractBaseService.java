package com.ssm.common.core.service;

import com.ssm.common.base.model.Model;
import com.ssm.common.base.service.BaseService;
import com.ssm.common.core.mapper.BaseMapper;

import java.util.Collection;

public abstract class AbstractBaseService<T extends Model> implements BaseService<T> {

    protected abstract BaseMapper<T> getBaseMapper();

    @Override
    public int add(T model) {
        return getBaseMapper().insertSelective(model);
    }

    @Override
    public int add(Collection<T> collection) {
        int row = 0;
        for (T model : collection) {
            row += add(model);
        }
        return row;
    }

    @Override
    public int update(T model) {
        return getBaseMapper().updateByPrimaryKeySelective(model);
    }

    @Override
    public int delete(Long id) {
        return getBaseMapper().deleteByPrimaryKey(id);
    }

    @Override
    public int delete(Long[] ids) {
        int row = 0;
        for (Long id : ids) {
            row += delete(id);
        }
        return row;
    }

    @Override
    public T getById(Long id) {
        return getBaseMapper().selectByPrimaryKey(id);
    }

}
