package com.daiyc.criteria.core.builder.impl;

import com.daiyc.criteria.core.builder.ValueComparisonBuilder;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.OperatorEnum;

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
    public ValueComparisonBuilder<T> empty() {
        return this;
    }

    @Override
    public ValueComparisonBuilderImpl<T> or() {
        ValueComparisonBuilderImpl<T> next = new ValueComparisonBuilderImpl<>(name, type);
        next.next = this;
        return next;
    }

    @Override
    public ValueComparisonBuilder<T> greaterThan(T value) {
        return addCriterion(new Criterion<>(name, OperatorEnum.GT, value));
    }

    @Override
    public ValueComparisonBuilder<T> lessThan(T value) {
        return addCriterion(new Criterion<>(name, OperatorEnum.LT, value));
    }

    @Override
    public ValueComparisonBuilder<T> greaterThanOrEqualsTo(T value) {
        return addCriterion(new Criterion<>(name, OperatorEnum.GTE, value));
    }

    @Override
    public ValueComparisonBuilder<T> lessThanOrEqualsTo(T value) {
        return addCriterion(new Criterion<>(name, OperatorEnum.LTE, value));
    }

    @Override
    public ValueComparisonBuilder<T> equalsTo(T value) {
        return addCriterion(new Criterion<>(name, OperatorEnum.EQ, value));
    }

    @Override
    public ValueComparisonBuilder<T> in(List<T> values) {
        return addCriterion(new Criterion<>(name, OperatorEnum.IN, values));
    }

    @Override
    public ValueComparisonBuilder<T> like(T value) {
        return addCriterion(new Criterion<>(name, OperatorEnum.LIKE, value));
    }

    @Override
    public ValueComparisonBuilder<T> notEqualsTo(T value) {
        return addCriterion(new Criterion<>(name, OperatorEnum.NEQ, value));
    }

    @Override
    public ValueComparisonBuilder<T> notIn(List<T> values) {
        return addCriterion(new Criterion<>(name, OperatorEnum.NOT_IN, values));
    }

    @Override
    public ValueComparisonBuilder<T> notLike(T value) {
        return addCriterion(new Criterion<>(name, OperatorEnum.NOT_LIKE, value));
    }
}
