package com.daiyc.criteria.core.matcher.op;

import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.OperandNum;

/**
 * @author daiyc
 */
@FunctionalInterface
public interface NoOperandEvaluator extends Evaluator {
    default boolean evaluate(Object value, Criterion<?> criterion) {
        assert criterion.getOperator().getOperandNum() == OperandNum.NONE;
        return evaluate(value);
    }

    boolean evaluate(Object value);
}
