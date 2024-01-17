package com.daiyc.criteria.core.builder;

/**
 * @author daiyc
 */
public interface ComparisonBuilder<T, B> extends Builder<T> {
    /**
     * OR
     *
     * @return builder
     */
    B or();
}
