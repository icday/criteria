package com.daiyc.criteria.core.builder;

import java.util.Arrays;
import java.util.List;

/**
 * @author daiyc
 */
public interface CriteriaBuilder<T> extends Builder<T> {
    default CriteriaBuilder<T> andWith(Builder<?>... builders) {
        return andWith(Arrays.asList(builders));
    }

    CriteriaBuilder<T> andWith(List<Builder<?>> builders);

    default CriteriaBuilder<T> orWith(Builder<?>... builders) {
        return orWith(Arrays.asList(builders));
    }

    CriteriaBuilder<T> orWith(List<Builder<?>> builders);
}
