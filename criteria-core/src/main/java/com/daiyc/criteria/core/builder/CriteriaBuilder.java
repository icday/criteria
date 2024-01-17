package com.daiyc.criteria.core.builder;

import java.util.Arrays;
import java.util.List;

/**
 * @author daiyc
 */
public interface CriteriaBuilder<T> extends Builder<T> {
    default CriteriaBuilder<T> and(Builder<?>... builders) {
        return and(Arrays.asList(builders));
    }

    CriteriaBuilder<T> and(List<Builder<?>> builders);

    default CriteriaBuilder<T> or(Builder<?>... builders) {
        return or(Arrays.asList(builders));
    }

    CriteriaBuilder<T> or(List<Builder<?>> builders);
}
