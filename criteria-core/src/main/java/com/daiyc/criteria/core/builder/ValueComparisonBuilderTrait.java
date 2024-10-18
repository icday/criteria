package com.daiyc.criteria.core.builder;

import com.daiyc.criteria.core.enums.TimePrecision;
import com.daiyc.criteria.core.enums.TimeUnit;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

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

    //region greaterThan
    default B greaterThan(Function<T, Boolean> fn, T value) {
        // 不能是 null, or(). NPE
        return fn.apply(value) ? greaterThan(value) : empty();
    }

    default B greaterThan(Boolean predicate, T value) {
        return predicate ? greaterThan(value) : empty();
    }

    default B greaterThan(Boolean predicate, Supplier<T> value) {
        return predicate ? greaterThan(value.get()) : empty();
    }
    //endregion

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

    default B lessThan(Boolean predicate, T value) {
        return predicate ? lessThan(value) : empty();
    }

    default B lessThan(Boolean predicate, Supplier<T> value) {
        return predicate ? lessThan(value.get()) : empty();
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

    default B greaterThanOrEqualsTo(Boolean predicate, T value) {
        return predicate ? greaterThanOrEqualsTo(value) : empty();
    }

    default B greaterThanOrEqualsTo(Boolean predicate, Supplier<T> value) {
        return predicate ? greaterThanOrEqualsTo(value.get()) : empty();
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

    default B lessThanOrEqualsTo(Boolean predicate, T value) {
        return predicate ? lessThanOrEqualsTo(value) : empty();
    }

    default B lessThanOrEqualsTo(Boolean predicate, Supplier<T> value) {
        return predicate ? lessThanOrEqualsTo(value.get()) : empty();
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

    default B equalsTo(Boolean predicate, T value) {
        return predicate ? equalsTo(value) : empty();
    }

    default B equalsTo(Boolean predicate, Supplier<T> value) {
        return predicate ? equalsTo(value.get()) : empty();
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

    default B notEqualsTo(Boolean predicate, T value) {
        return predicate ? notEqualsTo(value) : empty();
    }

    default B notEqualsTo(Boolean predicate, Supplier<T> value) {
        return predicate ? notEqualsTo(value.get()) : empty();
    }

    /**
     * 为空
     */
    B isNull();

    default B isNull(boolean condition) {
        return condition ? isNull() : empty();
    }

    default B isNull(Supplier<Boolean> fn) {
        return fn.get() ? isNull() : empty();
    }

    /**
     * 不为空
     */
    B isNotNull();

    default B isNotNull(Supplier<Boolean> fn) {
        return fn.get() ? isNotNull() : empty();
    }

    default B isNotNull(boolean condition) {
        return condition ? isNotNull() : empty();
    }

    /**
     * IN
     *
     * @param values values
     * @return builder
     */
    B in(List<T> values);

    default B in(T... values) {
        return in(Arrays.asList(values));
    }

    default B in(Function<List<T>, Boolean> fn, List<T> values) {
        return fn.apply(values) ? in(values) : empty();
    }

    default B in(boolean condition, T... values) {
        return condition ? in(values) : empty();
    }

    default B in(boolean condition, List<T> values) {
        return condition ? in(values) : empty();
    }

    default B in(boolean condition, Supplier<List<T>> supplier) {
        return condition ? in(supplier.get()) : empty();
    }

    /**
     * NOT IN
     *
     * @param values values
     * @return builder
     */
    B notIn(List<T> values);

    default B notIn(T... values) {
        return notIn(Arrays.asList(values));
    }

    default B notIn(Function<List<T>, Boolean> fn, List<T> values) {
        return fn.apply(values) ? notIn(values) : empty();
    }

    default B notIn(boolean condition, T... values) {
        return condition ? notIn(values) : empty();
    }

    default B notIn(boolean condition, List<T> values) {
        return condition ? notIn(values) : empty();
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

    default B like(boolean condition, T value) {
        return condition ? like(value) : empty();
    }

    default B like(boolean condition, Supplier<T> value) {
        return condition ? like(value.get()) : empty();
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

    default B notLike(boolean condition, T value) {
        return condition ? notLike(value) : empty();
    }

    default B notLike(boolean condition, Supplier<T> value) {
        return condition ? notLike(value.get()) : empty();
    }

    /**
     * 时间晚于[当前日期 + delta]
     *
     * @param delta 当前时间增量
     * @param unit 增量单位
     */
    B relativeAfterOrEqualsTo(Integer delta, TimeUnit unit, TimePrecision precision);

    default B relativeAfterOrEqualsTo(Integer delta, TimeUnit unit) {
        return relativeAfterOrEqualsTo(delta, unit, null);
    }

    /**
     * 时间晚于[当前日期 + delta]
     *
     * @param delta 当前时间增量
     * @param unit 增量单位
     */
    B relativeAfter(Integer delta, TimeUnit unit, TimePrecision precision);

    default B relativeAfter(Integer delta, TimeUnit unit) {
        return relativeAfter(delta, unit, null);
    }

    /**
     * 时间早于[当前日期 + delta]
     *
     * @param delta 当前时间增量
     * @param unit 增量单位
     */
    B relativeBefore(Integer delta, TimeUnit unit, TimePrecision precision);

    default B relativeBefore(Integer delta, TimeUnit unit) {
        return relativeBefore(delta, unit, null);
    }

    /**
     * 时间早于[当前日期 + delta]
     *
     * @param delta 当前时间增量
     * @param unit 增量单位
     */
    B relativeBeforeOrEqualsTo(Integer delta, TimeUnit unit, TimePrecision precision);

    default B relativeBeforeOrEqualsTo(Integer delta, TimeUnit unit) {
        return relativeBeforeOrEqualsTo(delta, unit, null);
    }
}
