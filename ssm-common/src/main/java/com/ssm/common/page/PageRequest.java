package com.ssm.common.page;

import com.ssm.common.model.Model;

public class PageRequest implements Pageable {

    private final Model param;
    private final int offset;
    private final int limit;
    private final boolean countable;

    public PageRequest(Model param, int offset, int limit) {
        this(param, offset, limit, Boolean.TRUE);
    }

    public PageRequest(Model param, int offset, int limit, boolean countable) {
        this.param = param;
        this.offset = offset;
        this.limit = limit;
        this.countable = countable;
    }

    @Override
    public Model getParam() {
        return param;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public boolean isCountable() {
        return countable;
    }

    public static Pageable newInstance(Model param, int offset, int limit) {
        return new PageRequest(param, offset, limit);
    }

}
