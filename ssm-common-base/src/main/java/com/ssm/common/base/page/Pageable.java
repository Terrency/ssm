package com.ssm.common.base.page;

public interface Pageable<T> {

    T getParam();

    int getCurrentPage();

    int getPageSize();

    int getOffset();

    boolean isCountable();

}
