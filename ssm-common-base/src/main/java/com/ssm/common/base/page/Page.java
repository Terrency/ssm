package com.ssm.common.base.page;

import java.util.List;

/**
 * A page is a sublist of a list of objects. It allows gain information about the position of it in the containing
 * entire list.
 *
 * @param <T>
 * @author Gavin
 */

public interface Page<T> extends Iterable<T> {

    /**
     * Returns the number of the current {@link Page}. Is always non-negative.
     *
     * @return the number of the current {@link Page}.
     */
    int getCurrentPage();

    /**
     * Returns the size of the {@link Page}.
     *
     * @return the size of the {@link Page}.
     */
    int getPageSize();

    /**
     * @return the page content as {@link List}.
     */
    List<T> getItems();

    /**
     * Returns the total amount of elements.
     *
     * @return the total amount of elements
     */
    int getTotalItems();

    /**
     * Returns the number of total pages.
     *
     * @return the number of total pages
     */
    int getTotalPages();

    /**
     * Returns the offset to be taken according to the underlying page and page size.
     *
     * @return the offset to be taken
     */
    int getOffset();

    /**
     * Returns the number of elements currently on this {@link Page}.
     *
     * @return the number of elements currently on this {@link Page}.
     */
    int getItemsSize();

    /**
     * @return whether the current {@link Page} is the first one.
     */
    boolean isFirst();

    /**
     * @return whether the current {@link Page} is the last one.
     */
    boolean isLast();

    /**
     * Returns if there is a previous {@link Page}.
     *
     * @return if there is a previous {@link Page}.
     */
    boolean hasPrevious();

    /**
     * Returns if there is a next {@link Page}.
     *
     * @return if there is a next {@link Page}.
     */
    boolean hasNext();

}
