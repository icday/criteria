package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.generic.GenericCondition;
import com.daiyc.criteria.core.generic.GenericCriteria;
import com.daiyc.criteria.core.generic.GenericCriterion;
import com.daiyc.criteria.core.model.*;

import java.util.List;
import java.util.Optional;

/**
 * @author daiyc
 */
public class GenericTransformer implements Transformer<GenericCondition> {
    @Override
    public GenericCondition transform(Criteria criteria, GenericCondition newValue, TransformContext ctx) {
        return newValue;
    }

    @Override
    public GenericCondition transform(Criterion<?> criterion, TransformContext ctx) {
        String name = criterion.getFieldName();
        Operator operator = criterion.getOperator();

        GenericCriterion gc = new GenericCriterion();
        gc.setName(name);
        gc.setOperator(operator.getSymbol());

        if (criterion instanceof RelativeTimeCriterion) {
            RelativeTimeCriterion relativeTimeCriterion = (RelativeTimeCriterion) criterion;
            gc.setTimeUnit(relativeTimeCriterion.getTimeUnit().name());
            gc.setTimePrecision(Optional.ofNullable(relativeTimeCriterion.getTimePrecision()).map(Enum::name).orElse(null));
            gc.setValue(relativeTimeCriterion.getDelta());
        } else {
            Object value = null;
            switch (operator.getOperandNum()) {
                case SINGLE:
                    value = criterion.getSingleValue();
                    break;
                case DOUBLE:
                case MORE:
                    value = criterion.getListValue();
                    break;
                case NONE:
                default:
                    break;
            }
            gc.setValue(value);
        }

        return gc;
    }

    @Override
    public GenericCondition combine(Combinator combinator, List<GenericCondition> list) {
        GenericCriteria criteria = new GenericCriteria();
        criteria.setType(combinator.name());
        criteria.setElements(list);
        return criteria;
    }
}
