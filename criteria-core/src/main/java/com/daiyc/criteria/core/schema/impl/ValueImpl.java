package com.daiyc.criteria.core.schema.impl;

import com.daiyc.criteria.core.builder.ValueComparisonBuilder;
import com.daiyc.criteria.core.builder.impl.ValueComparisonBuilderImpl;
import com.daiyc.criteria.core.enums.TimePrecision;
import com.daiyc.criteria.core.enums.TimeUnit;
import com.daiyc.criteria.core.schema.Value;

import java.util.List;

/**
 * @author daiyc
 */
public class ValueImpl<T> extends BaseValue<T> implements Value<T> {
    public ValueImpl(String fieldName, Class<T> type) {
        super(fieldName, type);
    }

    private ValueComparisonBuilder<T> builder() {
        return new ValueComparisonBuilderImpl<>(fieldName, type);
    }

    @Override
    public ValueComparisonBuilder<T> empty() {
        return builder().empty();
    }

    @Override
    public ValueComparisonBuilder<T> greaterThan(T value) {
        return builder().greaterThan(value);
    }

    @Override
    public ValueComparisonBuilder<T> lessThan(T value) {
        return builder().lessThan(value);
    }

    @Override
    public ValueComparisonBuilder<T> greaterThanOrEqualsTo(T value) {
        return builder().greaterThanOrEqualsTo(value);
    }

    @Override
    public ValueComparisonBuilder<T> lessThanOrEqualsTo(T value) {
        return builder().lessThanOrEqualsTo(value);
    }

    @Override
    public ValueComparisonBuilder<T> equalsTo(T value) {
        return builder().equalsTo(value);
    }

    @Override
    public ValueComparisonBuilder<T> in(List<T> values) {
        return builder().in(values);
    }

    @Override
    public ValueComparisonBuilder<T> like(T value) {
        return builder().like(value);
    }

    @Override
    public ValueComparisonBuilder<T> notEqualsTo(T value) {
        return builder().notEqualsTo(value);
    }

    @Override
    public ValueComparisonBuilder<T> isNull() {
        return builder().isNull();
    }

    @Override
    public ValueComparisonBuilder<T> isNotNull() {
        return builder().isNotNull();
    }

    @Override
    public ValueComparisonBuilder<T> notIn(List<T> values) {
        return builder().notIn(values);
    }

    @Override
    public ValueComparisonBuilder<T> notLike(T value) {
        return builder().notLike(value);
    }

    @Override
    public ValueComparisonBuilder<T> relativeAfterOrEqualsTo(Integer delta, TimeUnit unit, TimePrecision precision) {
        return builder().relativeAfterOrEqualsTo(delta, unit, precision);
    }

    @Override
    public ValueComparisonBuilder<T> relativeAfter(Integer delta, TimeUnit unit, TimePrecision precision) {
        return builder().relativeAfter(delta, unit, precision);
    }

    @Override
    public ValueComparisonBuilder<T> relativeBefore(Integer delta, TimeUnit unit, TimePrecision precision) {
        return builder().relativeBefore(delta, unit, precision);
    }

    @Override
    public ValueComparisonBuilder<T> relativeBeforeOrEqualsTo(Integer delta, TimeUnit unit, TimePrecision precision) {
        return builder().relativeBeforeOrEqualsTo(delta, unit, precision);
    }
}
