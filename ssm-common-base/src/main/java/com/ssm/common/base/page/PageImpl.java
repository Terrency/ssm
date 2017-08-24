package com.ssm.common.base.page;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Basic {@code Page} implementation.
 *
 * @param <T> the type of which the page consists.
 * @author Gavin
 */
public class PageImpl<T> implements Page<T>, Serializable {

    /**
     * 传递的参数
     * currentPage: 当前页
     * pageSize: 每页显示的记录数
     */
    private final int currentPage;
    private final int pageSize;

    /**
     * 查询数据库
     * items: 当前页的数据列表
     * totalItems: 总记录数
     */
    private final List<T> items = new ArrayList<>();
    private final int totalItems;

    /**
     * 计算总页数
     * totalPages: 总页数
     */
    private final int totalPages;

    /**
     * {@code PageImpl}.
     *
     * @param currentPage the number of the current {@link Page}.
     * @param pageSize    the size of the {@link Page}.
     * @param items       the content of this page.
     * @param totalItems  the total amount of elements available.
     */
    public PageImpl(int currentPage, int pageSize, List<T> items, int totalItems) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.items.addAll(items);
        this.totalItems = totalItems;
        this.totalPages = (totalItems + pageSize - 1) / pageSize;
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public List<T> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public int getTotalItems() {
        return totalItems;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public int getOffset() {
        return currentPage * pageSize;
    }

    @Override
    public int getItemsSize() {
        return items.size();
    }

    @Override
    public boolean isFirst() {
        return !hasPrevious();
    }

    @Override
    public boolean isLast() {
        return !hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return getCurrentPage() > 0;
    }

    @Override
    public boolean hasNext() {
        return getCurrentPage() + 1 < getTotalPages();
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
