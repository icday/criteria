package com.daiyc.criteria.common;

import lombok.RequiredArgsConstructor;

/**
 * @author daiyc
 */
@RequiredArgsConstructor
public class Pagination {
    private final int page;

    private final int size;

    public Pagination(int size) {
        this(0, size);
    }

    public int getOffset() {
        return page * size;
    }

    public int getLimit() {
        return size;
    }

    public Pagination nextPage() {
        return new Pagination(page + 1, size);
    }
}
