package com.daiyc.criteria.core.matcher.op;

import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.Operator;

import java.util.List;

/**
 * @author daiyc
 */
public abstract class BaseEvaluator implements Evaluator {
    @Override
    public boolean evaluate(Object value, Criterion<?> criterion) {
        Operator operator = criterion.getOperator();
        switch (operator.getOperandNum()) {
            case NONE:
                return evaluate(value);
            case SINGLE:
                return evaluate(value, criterion.getSingleValue());
            case DOUBLE:
                return evaluate(value, criterion.getListValue().get(0), criterion.getListValue().get(1));
            case MORE:
                return evaluate(value, criterion.getListValue());
        }
        return false;
    }

    protected boolean evaluate(Object value) {
        throw new UnsupportedOperationException();
    }

    protected boolean evaluate(Object value, Object operand) {
        throw new UnsupportedOperationException();
    }
    protected boolean evaluate(Object value, Object operand1, Object operand2) {
        throw new UnsupportedOperationException();
    }

    protected boolean evaluate(Object value, List<Object> operands) {
        throw new UnsupportedOperationException();
    }
}
