package com.ssm.common.base.page;

import com.ssm.common.base.model.Model;

public interface Pageable {

    Model getParam();

    int getOffset();

    int getLimit();

    boolean isCountable();

}
