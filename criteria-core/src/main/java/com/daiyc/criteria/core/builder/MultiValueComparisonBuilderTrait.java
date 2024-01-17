package com.daiyc.criteria.core.builder;

import java.util.Arrays;
import java.util.List;

/**
 * @author daiyc
 */
public interface MultiValueComparisonBuilderTrait<T, B extends MultiValueComparisonBuilderTrait<T, B>> {
    /**
     * 包含特定的值
     *
     * @param value value
     * @return builder
     */
    B contains(T value);

    /**
     * 包含所有值
     *
     * @param values values
     * @return builder
     */
    B containsAll(List<T> values);

    /**
     * 包含所有值
     *
     * @param values values
     * @return builder
     */
    default B containsAll(T... values) {
        return containsAll(Arrays.asList(values));
    }

    /**
     * 包含任一值
     *
     * @param values values
     * @return builder
     */
    default B containsAny(T... values) {
        return containsAny(Arrays.asList(values));
    }

    /**
     * 包含任一值
     *
     * @param values values
     * @return builder
     */
    B containsAny(List<T> values);
}
