package com.daiyc.criteria.core.model.operator;

import com.daiyc.criteria.core.generic.GenericCriterion;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.CriterionFactory;
import com.daiyc.criteria.core.model.OperandNum;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.core.schema.FieldInfo;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * @author daiyc
 */
public class SingleOperandOperator extends BaseOperator {
    public SingleOperandOperator(String symbol) {
        super(symbol, OperandNum.SINGLE);
    }

    @Override
    public <T> Criterion<T> toCriterion(GenericCriterion genericCriterion, CriteriaSchema schema) {
        FieldInfo field = schema.getField(genericCriterion.getName());

        T value = getOperand(genericCriterion, field);

        return CriterionFactory.create(genericCriterion.getName(), this, value);
    }

    @Override
    public String stringify(Criterion<?> criterion) {
        Object singleValue = criterion.getSingleValue();
        if (singleValue instanceof Date) {
            singleValue = DateFormatUtils.format((Date) singleValue, "yyyy-MM-dd HH:mm:ss");
        }
        return String.format("%s %s %s", criterion.getFieldName(), getSymbol(), singleValue);
    }
}
