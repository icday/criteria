package com.daiyc.criteria.core.model.operator;

import com.daiyc.criteria.core.generic.GenericCriterion;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.OperandNum;
import com.daiyc.criteria.core.model.Operator;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author daiyc
 */
@RequiredArgsConstructor
public class OperatorWrapper implements Operator {
    @Getter
    protected final Operator target;

    @Override
    public String getSymbol() {
        return target.getSymbol();
    }

    @Override
    public OperandNum getOperandNum() {
        return target.getOperandNum();
    }

    @Override
    public <T> Criterion<T> toCriterion(GenericCriterion genericCriterion, CriteriaSchema schema) {
        return target.toCriterion(genericCriterion, schema);
    }

    @Override
    public String stringify(Criterion<?> criterion) {
        return target.stringify(criterion);
    }

    @Override
    public Operator getTarget() {
        return target.getTarget();
    }
}
