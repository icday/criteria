package com.daiyc.criteria.core.model.operator;

import com.daiyc.criteria.core.generic.GenericCriterion;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.CriterionFactory;
import com.daiyc.criteria.core.model.OperandNum;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.core.schema.FieldInfo;

/**
 * @author daiyc
 */
public class SingleOperandOperator extends BaseOperator {
    public SingleOperandOperator(String symbol) {
        super(symbol);
    }

    @Override
    public OperandNum getOperandNum() {
        return OperandNum.SINGLE;
    }

    @Override
    public <T> Criterion<T> toCriterion(GenericCriterion genericCriterion, CriteriaSchema schema) {
        FieldInfo field = schema.getField(genericCriterion.getName());

        T value = getOperand(genericCriterion, field);

        return CriterionFactory.create(genericCriterion.getName(), this, value);
    }
}
