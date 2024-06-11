package com.daiyc.criteria.core.model.operator;

import com.daiyc.criteria.core.generic.GenericCriterion;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.CriterionFactory;
import com.daiyc.criteria.core.model.OperandNum;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.core.schema.FieldInfo;

import java.util.List;

/**
 * @author daiyc
 */
public class ListOperandOperator extends BaseOperator {
    public ListOperandOperator(String symbol) {
        super(symbol);
    }

    @Override
    public OperandNum getOperandNum() {
        return OperandNum.MORE;
    }

    @Override
    public <T> Criterion<T> toCriterion(GenericCriterion genericCriterion, CriteriaSchema schema) {
        FieldInfo fieldInfo = schema.getField(genericCriterion.getName());
        List<T> operands = getOperands(genericCriterion, fieldInfo);

        return CriterionFactory.create(genericCriterion.getName(), this, operands);
    }
}
