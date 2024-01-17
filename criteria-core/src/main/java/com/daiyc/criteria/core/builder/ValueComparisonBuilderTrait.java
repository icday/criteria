package com.daiyc.criteria.core.builder;

import java.util.Arrays;
import java.util.List;

/**
 * @author daiyc
 */
public interface ValueComparisonBuilderTrait<T, B extends ValueComparisonBuilderTrait<T, B>> {
    /**
     * 大于
     *
     * @param value value
     * @return builder
     */
    B greaterThan(T value);

    /**
     * 小于
     *
     * @param value value
     * @return builder
     */
    B lessThan(T value);

    /**
     * 大于等于
     *
     * @param value value
     * @return builder
     */
    B greaterThanOrEqualsTo(T value);

    /**
     * 小于等于
     *
     * @param value value
     * @return builder
     */
    B lessThanOrEqualsTo(T value);

    /**
     * 等于
     *
     * @param value value
     * @return builder
     */
    B equalsTo(T value);

    /**
     * IN
     *
     * @param values values
     * @return builder
     */
    B in(List<T> values);

    /**
     * IN
     *
     * @param values values
     * @return builder
     */
    default B in(T... values) {
        return in(Arrays.asList(values));
    }

    /**
     * LIKE
     *
     * @param value value
     * @return builder
     */
    B like(T value);
}
