package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.enums.TimePrecision;
import com.daiyc.criteria.core.enums.TimeUnit;
import com.daiyc.criteria.core.model.operator.RelativeTimeComparator;

import java.util.List;

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
        operator = operator.getTarget();
        assert operator instanceof RelativeTimeComparator;
        return new RelativeTimeCriterion(name, (RelativeTimeComparator) operator, value, timeUnit, timePrecision);
    }
}
