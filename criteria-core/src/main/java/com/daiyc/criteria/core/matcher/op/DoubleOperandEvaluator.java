package com.daiyc.criteria.core.matcher.op;

import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.OperandNum;

import java.util.List;

/**
 * @author daiyc
 */
@FunctionalInterface
public interface DoubleOperandEvaluator extends Evaluator {
    @Override
    default boolean evaluate(Object value, Criterion<?> criterion) {
        assert criterion.getOperator().getOperandNum() == OperandNum.DOUBLE;

        List<?> listValue = criterion.getListValue();
        return evaluate(value, listValue.get(0), listValue.get(1));
    }

    boolean evaluate(Object value, Object op1, Object op2);
}
