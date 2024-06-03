package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.enums.TimePrecision;
import com.daiyc.criteria.core.enums.TimeUnit;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * @author daiyc
 */
public class CriterionFactory {
    public static <T> Criterion<T> create(String name, Operator operator) {
        return new GeneralCriterion<>(name, operator);
    }

    public static <T> Criterion<T> create(String name, Operator operator, T value) {
        return new GeneralCriterion<>(name, operator, value);
    }

    public static <T> Criterion<T> create(String name, Operator operator, List<T> value) {
        return new GeneralCriterion<>(name, operator, value);
    }

    public static RelativeTimeCriterion relativeTime(String name, Operator operator, Integer value
            , TimeUnit timeUnit, TimePrecision timePrecision) {
        return relativeTime(name, operator, date -> {
            date = TimePrecision.apply(timePrecision, date);
            return TimeUnit.apply(timeUnit, date, value);
        });
    }

    public static RelativeTimeCriterion relativeTime(String name, Operator operator, Function<Date, Date> fn) {
        return new RelativeTimeCriterion(name, operator, fn, null);
    }
}
