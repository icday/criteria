package com.daiyc.criteria.core.matcher.op;

import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.OperandNum;

/**
 * @author daiyc
 */
@FunctionalInterface
public interface SingleOperandEvaluator extends Evaluator {
    @Override
    default boolean evaluate(Object value, Criterion<?> criterion) {
        assert criterion.getOperator().getOperandNum() == OperandNum.SINGLE;
        return evaluate(value, criterion.getSingleValue());
    }

    boolean evaluate(Object value, Object operand);
}
