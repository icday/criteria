package com.daiyc.criteria.core.builder;

import com.daiyc.criteria.core.builder.impl.DefaultCriteriaBuilder;
import com.daiyc.criteria.core.model.Combinator;

import java.util.Arrays;
import java.util.List;

/**
 * @author daiyc
 */
public abstract class CriteriaBuilders {
    public static <T> CriteriaBuilder<T> and(Builder<?>... builders) {
        return and(Arrays.asList(builders));
    }

    public static <T> CriteriaBuilder<T> and(List<Builder<?>> builders) {
        return new DefaultCriteriaBuilder<>(Combinator.AND, builders);
    }

    public static <T> CriteriaBuilder<T> or(Builder<?>... builders) {
        return or(Arrays.asList(builders));
    }

    public static <T> CriteriaBuilder<T> or(List<Builder<?>> builders) {
        return new DefaultCriteriaBuilder<>(Combinator.OR, builders);
    }
}
