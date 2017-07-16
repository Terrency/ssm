package com.ssm.common.web.base;

import com.ssm.common.base.model.Model;
import com.ssm.common.web.datatable.DataTableRequest;

public interface Wrapper<T extends Model> {

    T getModel();

    DataTableRequest getDtArgs();

}
