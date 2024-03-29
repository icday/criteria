package com.daiyc.criteria.core.builder.impl;

import com.daiyc.criteria.core.builder.ValueComparisonBuilder;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.Operator;

import java.util.List;

/**
 * @author daiyc
 */
public class ValueComparisonBuilderImpl<T> extends BaseComparisonBuilderImpl<T, ValueComparisonBuilderImpl<T>>
        implements ValueComparisonBuilder<T> {

    public ValueComparisonBuilderImpl(String name, Class<T> type) {
        super(name, type);
    }

    @Override
    public ValueComparisonBuilderImpl<T> or() {
        ValueComparisonBuilderImpl<T> next = new ValueComparisonBuilderImpl<>(name, type);
        next.next = this;
        return next;
    }

    @Override
    public ValueComparisonBuilder<T> greaterThan(T value) {
        return addCriterion(new Criterion<>(name, Operator.GT, value));
    }

    @Override
    public ValueComparisonBuilder<T> lessThan(T value) {
        return addCriterion(new Criterion<>(name, Operator.LT, value));
    }

    @Override
    public ValueComparisonBuilder<T> greaterThanOrEqualsTo(T value) {
        return addCriterion(new Criterion<>(name, Operator.GTE, value));
    }

    @Override
    public ValueComparisonBuilder<T> lessThanOrEqualsTo(T value) {
        return addCriterion(new Criterion<>(name, Operator.LTE, value));
    }

    @Override
    public ValueComparisonBuilder<T> equalsTo(T value) {
        return addCriterion(new Criterion<>(name, Operator.EQ, value));
    }

    @Override
    public ValueComparisonBuilder<T> in(List<T> values) {
        return addCriterion(new Criterion<>(name, Operator.IN, values));
    }

    @Override
    public ValueComparisonBuilder<T> like(T value) {
        return addCriterion(new Criterion<>(name, Operator.LIKE, value));
    }
}
