package com.ssm.common.base.page;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PageRequest<T> implements Pageable<T> {

    private T param;                            // 额外参数
    private int pageSize;                       // 每页显示的记录数
    private int currentPage;                    // 当前页
    private boolean countable = Boolean.TRUE;   // 是否进行count查询

    public PageRequest() {
    }

    public PageRequest(T param, int pageSize, int currentPage) {
        this(param, pageSize, currentPage, Boolean.TRUE);
    }

    public PageRequest(T param, int pageSize, int currentPage, boolean countable) {
        this.param = param;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.countable = countable;
    }

    @Override
    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public int getOffset() {
        return currentPage * pageSize;
    }

    @Override
    public boolean isCountable() {
        return countable;
    }

    public void setCountable(boolean countable) {
        this.countable = countable;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
