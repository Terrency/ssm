package com.ssm.common.web.wrapper;

import com.ssm.common.model.Model;
import com.ssm.common.web.datatable.DataTableRequest;

public class BaseWrapper<T extends Model> implements Wrapper<T> {

    protected T model;

    protected DataTableRequest dtArgs;

    @Override
    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    @Override
    public DataTableRequest getDtArgs() {
        return dtArgs;
    }

    public void setDtArgs(DataTableRequest dtArgs) {
        this.dtArgs = dtArgs;
    }

}
