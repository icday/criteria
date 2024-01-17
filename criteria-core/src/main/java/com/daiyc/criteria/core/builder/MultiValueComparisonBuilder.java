package com.daiyc.criteria.core.builder;

/**
 * @author daiyc
 */
public interface MultiValueComparisonBuilder<T> extends
        MultiValueComparisonBuilderTrait<T, MultiValueComparisonBuilder<T>>, Builder<T> {
    /**
     * OR
     *
     * @return builder
     */
    MultiValueComparisonBuilder<T> or();
}
