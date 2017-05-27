package com.ssm.common.web.wrapper;

import com.ssm.common.model.Model;
import com.ssm.common.web.datatable.DataTableRequest;

public interface Wrapper<T extends Model> {

    T getModel();

    DataTableRequest getDtArgs();

}
