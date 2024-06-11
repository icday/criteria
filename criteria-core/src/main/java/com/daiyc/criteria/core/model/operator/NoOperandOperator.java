package com.daiyc.criteria.core.model.operator;

import com.daiyc.criteria.core.generic.GenericCriterion;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.CriterionFactory;
import com.daiyc.criteria.core.model.OperandNum;
import com.daiyc.criteria.core.schema.CriteriaSchema;

/**
 * @author daiyc
 */
public class NoOperandOperator extends BaseOperator {
    public NoOperandOperator(String symbol) {
        super(symbol);
    }

    @Override
    public OperandNum getOperandNum() {
        return OperandNum.NONE;
    }

    @Override
    public <T> Criterion<T> toCriterion(GenericCriterion genericCriterion, CriteriaSchema schema) {
        return CriterionFactory.create(genericCriterion.getName(), this);
    }
}
