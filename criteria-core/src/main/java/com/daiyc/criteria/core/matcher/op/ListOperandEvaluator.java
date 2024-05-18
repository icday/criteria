package com.daiyc.criteria.core.matcher.op;

import com.daiyc.criteria.core.model.Criterion;

import java.util.Collection;
import java.util.List;

/**
 * @author daiyc
 */
@FunctionalInterface
public interface ListOperandEvaluator extends Evaluator {
    @Override
    default boolean evaluate(Object value, Criterion<?> criterion) {
        assert value instanceof Collection;
        return evaluate((Collection<?>) value, criterion.getListValue());
    }

    boolean evaluate(Collection<?> realValue, List<?> list);
}
