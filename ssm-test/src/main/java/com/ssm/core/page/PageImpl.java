package com.ssm.core.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Basic {@code Page} implementation.
 *
 * @param <E> the type of which the page consists.
 * @author Gavin
 */
public class PageImpl<E> implements Page<E>, Serializable {

    /**
     * 传递的参数
     * pageNumber: 第几页(默认从0开始)
     * pageSize: 每页显示的记录数
     */
    private int pageNumber;
    private int pageSize;

    /**
     * 查询数据库
     * content: 当前页的数据列表
     * total: 总记录数
     */
    private final List<E> content = new ArrayList<>();
    private int totalElements;

    /**
     * 计算总页数
     * totalPages: 总页数
     */
    private int totalPages;

    public PageImpl() {
    }

    /**
     * {@code PageImpl}.
     *
     * @param pageNumber    the number of the current {@link Page}.
     * @param pageSize      the size of the {@link Page}.
     * @param content       the content of this page.
     * @param totalElements the total amount of elements available.
     */
    public PageImpl(int pageNumber, int pageSize, List<E> content, int totalElements) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.content.addAll(content);
        this.totalElements = totalElements;
        this.totalPages = (totalElements + pageSize - 1) / pageSize;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public List<E> getContent() {
        return Collections.unmodifiableList(content);
    }

    @Override
    public int getTotalElements() {
        return totalElements;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public int getOffset() {
        return pageNumber * pageSize;
    }

    @Override
    public int getNumberOfElements() {
        return content.size();
    }

    @Override
    public boolean hasContent() {
        return !content.isEmpty();
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
        return getPageNumber() > 0;
    }

    @Override
    public boolean hasNext() {
        return getPageNumber() + 1 < getTotalPages();
    }

    @Override
    public Iterator<E> iterator() {
        return content.iterator();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PageImpl{");
        sb.append("pageNumber=").append(pageNumber);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", content=").append(content);
        sb.append(", totalElements=").append(totalElements);
        sb.append(", totalPages=").append(totalPages);
        sb.append('}');
        return sb.toString();
    }
}
