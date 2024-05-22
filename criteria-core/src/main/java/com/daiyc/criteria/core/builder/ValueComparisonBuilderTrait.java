package com.daiyc.criteria.core.builder;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author daiyc
 */
public interface ValueComparisonBuilderTrait<T, B extends ValueComparisonBuilderTrait<T, B>> {
    /**
     * 返回一个表示空条件的builder
     */
    B empty();

    /**
     * 大于
     *
     * @param value value
     * @return builder
     */
    B greaterThan(T value);

    default B greaterThan(Function<T, Boolean> fn, T value) {
        // 不能是 null, or(). NPE
        return fn.apply(value) ? greaterThan(value) : empty();
    }

    /**
     * 小于
     *
     * @param value value
     * @return builder
     */
    B lessThan(T value);

    default B lessThan(Function<T, Boolean> fn, T value) {
        return fn.apply(value) ? lessThan(value) : empty();
    }

    /**
     * 大于等于
     *
     * @param value value
     * @return builder
     */
    B greaterThanOrEqualsTo(T value);

    default B greaterThanOrEqualsTo(Function<T, Boolean> fn, T value) {
        return fn.apply(value) ? greaterThanOrEqualsTo(value) : empty();
    }

    /**
     * 小于等于
     *
     * @param value value
     * @return builder
     */
    B lessThanOrEqualsTo(T value);

    default B lessThanOrEqualsTo(Function<T, Boolean> fn, T value) {
        return fn.apply(value) ? lessThanOrEqualsTo(value) : empty();
    }

    /**
     * 等于
     *
     * @param value value
     * @return builder
     */
    B equalsTo(T value);

    default B equalsTo(Function<T, Boolean> fn, T value) {
        return fn.apply(value) ? equalsTo(value) : empty();
    }

    /**
     * 不等于
     *
     * @param value value
     * @return builder
     */
    B notEqualsTo(T value);

    default B notEqualsTo(Function<T, Boolean> fn, T value) {
        return fn.apply(value) ? notEqualsTo(value) : empty();
    }

    /**
     * IN
     *
     * @param values values
     * @return builder
     */
    B in(List<T> values);

    default B in(Function<List<T>, Boolean> fn, List<T> values) {
        return fn.apply(values) ? in(values) : empty();
    }

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
     * NOT IN
     *
     * @param values values
     * @return builder
     */
    B notIn(List<T> values);

    default B notIn(Function<List<T>, Boolean> fn, List<T> values) {
        return fn.apply(values) ? notIn(values) : empty();
    }

    /**
     * IN
     *
     * @param values values
     * @return builder
     */
    default B notIn(T... values) {
        return notIn(Arrays.asList(values));
    }

    /**
     * LIKE
     *
     * @param value value
     * @return builder
     */
    B like(T value);

    default B like(Function<T, Boolean> fn, T value) {
        return fn.apply(value) ? like(value) : empty();
    }

    /**
     * NOT LIKE
     *
     * @param value value
     * @return builder
     */
    B notLike(T value);

    default B notLike(Function<T, Boolean> fn, T value) {
        return fn.apply(value) ? notLike(value) : empty();
    }
}
