package com.ssm.common.page;

import com.ssm.common.model.Model;

public interface Pageable {

    Model getParam();

    int getOffset();

    int getLimit();

    boolean isCountable();

}
